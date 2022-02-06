package com.itprom.jet.office.job;

import com.itprom.jet.common.bean.Airport;
import com.itprom.jet.common.bean.Board;
import com.itprom.jet.common.bean.Route;
import com.itprom.jet.common.bean.RoutePath;
import com.itprom.jet.common.messages.AirportStateMessage;
import com.itprom.jet.common.messages.OfficeRouteMessage;
import com.itprom.jet.common.processor.MessageConverter;
import com.itprom.jet.office.provider.AirportsProvider;
import com.itprom.jet.office.provider.BoardsProvider;
import com.itprom.jet.office.service.PathService;
import com.itprom.jet.office.service.WaitingRouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class RouteDistributeJob {

    private final PathService pathService;
    private final BoardsProvider boardsProvider;
    private final WaitingRouteService waitingRouteService;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final MessageConverter messageConverter;
    private final AirportsProvider airportsProvider;

    @Scheduled(initialDelay = 500, fixedDelay = 2500)
    private void distribute() {
        waitingRouteService.list()
                .stream()
                .filter(Route::notAssigned)
                .forEach(route ->
                {
                    String startLocation = route.getPath().get(0)
                            .getFrom().getNameLocation();
                    boardsProvider.getBoards().stream()
                            .filter(board ->
                                    startLocation.equals(board.getLocation()) && board.noBusy()
                            )
                            .findFirst()
                            .ifPresent(board -> sendBoardToRoute(route, board));
                    if (route.notAssigned()) {
                        boardsProvider.getBoards().stream()
                                .filter(Board::noBusy)
                                .findFirst()
                                .ifPresent(board ->
                                {
                                    String currentLocation = board.getLocation();
                                    if (!currentLocation.equals(startLocation)) {
                                        RoutePath routePath = pathService.makePath(currentLocation, startLocation);
                                        route.getPath().add(0, routePath);
                                    }
                                    sendBoardToRoute(route, board);
                                });
                    }
                });
    }

    private void sendBoardToRoute(Route route, Board board) {
        route.setBoardName(board.getName());
        Airport airport = airportsProvider.findAirportAndRemoveBoard(board.getName());
        board.setLocation(null);
        kafkaTemplate.sendDefault(messageConverter.toJson(new OfficeRouteMessage(route)));
        kafkaTemplate.sendDefault(messageConverter.toJson(new AirportStateMessage(airport)));
    }

}

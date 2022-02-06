package com.itprom.jet.office.listener.processors;

import com.itprom.jet.common.bean.Airport;
import com.itprom.jet.common.bean.Board;
import com.itprom.jet.common.bean.Route;
import com.itprom.jet.common.messages.AirportStateMessage;
import com.itprom.jet.common.messages.BoardStateMessage;
import com.itprom.jet.common.processor.MessageConverter;
import com.itprom.jet.common.processor.MessageProcessor;
import com.itprom.jet.office.provider.AirportsProvider;
import com.itprom.jet.office.provider.BoardsProvider;
import com.itprom.jet.office.service.WaitingRouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component("BOARD_STATE")
@RequiredArgsConstructor
public class BoardStateProcessor implements MessageProcessor<BoardStateMessage> {

    private final MessageConverter messageConverter;
    private final KafkaTemplate<String, String> kafkaTemplate;

    private final WaitingRouteService waitingRouteService;
    private final BoardsProvider boardsProvider;
    private final AirportsProvider airportsProvider;

    @Override
    public void process(String jsonMessage) {
        BoardStateMessage message = messageConverter.extractMessage(jsonMessage, BoardStateMessage.class);
        Board board = message.getBoard();
        Optional<Board> previousOpt = boardsProvider.getBoard(board.getName());
        Airport airport = airportsProvider.getAirport(board.getLocation());

        boardsProvider.addBoard(board);
        if (previousOpt.isPresent() && board.hasRoute() && previousOpt.get().hasRoute()) {
            Route route = board.getRoute();
            waitingRouteService.remove(route);

        }

        if (previousOpt.isEmpty() || !board.isBusy() && previousOpt.get().isBusy()) {
            airport.addBoard(board.getName());
            kafkaTemplate.sendDefault(messageConverter.toJson(new AirportStateMessage(airport)));
        }

    }

}

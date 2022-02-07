package com.itprom.jet.ship.listener.processor;

import com.itprom.jet.common.messages.BoardStateMessage;
import com.itprom.jet.common.messages.OfficeStateMessage;
import com.itprom.jet.common.processor.MessageConverter;
import com.itprom.jet.common.processor.MessageProcessor;
import com.itprom.jet.ship.provider.BoardProvder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component("OFFICE_STATE")
@RequiredArgsConstructor
public class OfficeStateProcessor implements MessageProcessor<OfficeStateMessage> {

    private final MessageConverter messageConverter;
    private final BoardProvder boardProvder;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void process(String jsonMessage) {
        boardProvder.getBoards().forEach(board ->
                kafkaTemplate.sendDefault(messageConverter.toJson(new BoardStateMessage(board)))
        );
    }
}

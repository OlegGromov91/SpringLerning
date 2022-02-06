package com.itprom.jet.office.listener.processors;

import com.itprom.jet.common.messages.AirportStateMessage;
import com.itprom.jet.common.messages.OfficeStateMessage;
import com.itprom.jet.common.processor.MessageConverter;
import com.itprom.jet.common.processor.MessageProcessor;
import com.itprom.jet.office.provider.AirportsProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Slf4j
@Component("OFFICE_STATE")
@RequiredArgsConstructor

public class OfficeStateProcessor implements MessageProcessor<OfficeStateMessage> {


    private final MessageConverter messageConverter;
    private final AirportsProvider airportsProvider;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void process(String jsonMessage) {
        airportsProvider.getPorts().forEach(airport ->
        {
            kafkaTemplate.sendDefault(messageConverter.toJson(new AirportStateMessage(airport)));
        });
    }


}

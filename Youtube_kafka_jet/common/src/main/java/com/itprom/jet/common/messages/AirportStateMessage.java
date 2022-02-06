package com.itprom.jet.common.messages;

import com.itprom.jet.common.bean.Airport;
import com.itprom.jet.common.bean.Source;
import com.itprom.jet.common.bean.Type;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AirportStateMessage extends Message {
    private Airport airport;

    public AirportStateMessage() {
        this.source = Source.AIRPORT;
        this.type = Type.STATE;
    }

    public AirportStateMessage(Airport airport) {
        this();
        this.airport = airport;
    }
}

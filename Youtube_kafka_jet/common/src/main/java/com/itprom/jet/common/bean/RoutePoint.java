package com.itprom.jet.common.bean;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoutePoint {

    private String nameLocation;
    private double xPoint;
    private double yPoint;

    public RoutePoint(Airport airport) {
        this.nameLocation = airport.getName();
        this.xPoint = airport.getXCoordinate();
        this.yPoint = airport.getYCoordinate();
    }
}

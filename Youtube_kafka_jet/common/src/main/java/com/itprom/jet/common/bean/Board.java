package com.itprom.jet.common.bean;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Board {
    private String name;
    private String location;
    private Route route;
    private boolean busy;
    private double speed;
    private double xCoordinate;
    private double yCoordinate;
    private double angle;

    public boolean noBusy() {
        return !busy;
    }

    public boolean hasRoute() {
        return route != null;
    }

    public void calculatePosition(RoutePath routeDirection) {
        double position = routeDirection.getProgress() / 100;

        double toXCoordinate = (1 - position) * routeDirection.getFrom().getXPoint() + position * routeDirection.getTo().getXPoint();
        double toYCoordinate = (1 - position) * routeDirection.getFrom().getYPoint() + position * routeDirection.getTo().getYPoint();

        double deltaX = this.xCoordinate - toXCoordinate;
        double deltaY = this.yCoordinate - toYCoordinate;

        this.angle = Math.toDegrees(Math.atan2(deltaY, deltaX));
        if (this.angle < 0) {
            this.angle = 360 + this.angle;
        }
        this.xCoordinate = toXCoordinate;
        this.yCoordinate = toYCoordinate;
    }


}

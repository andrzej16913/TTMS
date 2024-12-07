package org.example.TTMS.searchRoute;

import org.example.TTMS.leg.Leg;

import java.time.LocalDateTime;

public class LegEdge {
    private Leg leg;
    private StationVertex origin;
    private StationVertex destination;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    public LegEdge(Leg leg, StationVertex origin, StationVertex destination) {
        this.leg = leg;
        this.origin = origin;
        this.destination = destination;
        departureTime = leg.getDepartureTime();
        arrivalTime = leg.getArrivalTime();
    }

    public Leg getLeg() {
        return leg;
    }

    public StationVertex getOrigin() {
        return origin;
    }

    public StationVertex getDestination() {
        return destination;
    }

    public void reduceDestinationArrivalTime(LocalDateTime originArrivalTime) {
        if (originArrivalTime.isBefore(departureTime) && arrivalTime.isBefore(destination.getArrival())) {
            destination.updateArrival(arrivalTime, this);
        }
    }
}

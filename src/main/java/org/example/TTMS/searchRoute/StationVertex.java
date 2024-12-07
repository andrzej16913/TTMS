package org.example.TTMS.searchRoute;

import org.example.TTMS.leg.Leg;
import org.example.TTMS.station.Station;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StationVertex {
    private long stationId;
    private List<LegEdge> edges;
    private LocalDateTime arrival;
    private LegEdge legToPrevious;

    public StationVertex(Station station) {
        stationId = station.getId();
        edges = new ArrayList<>();
        arrival = LocalDateTime.MAX;
        legToPrevious = null;
    }

    public Long getStationId() {
        return stationId;
    }

    public LocalDateTime getArrival() {
        return arrival;
    }

    public StationVertex getPrevious() {
        return legToPrevious.getOrigin();
    }

    public LegEdge getLegToPrevious() {
        return legToPrevious;
    }

    public void addLegToStation(StationVertex destination, Leg leg) {
        LegEdge legEdge = new LegEdge(leg, this, destination);
        edges.add(legEdge);
    }

    public void removeLegToStation(LegEdge legEdge) {
        edges.remove(legEdge);
    }

    public void reduceNeighbours() {
        for (LegEdge edge: edges) {
            edge.reduceDestinationArrivalTime(arrival);
        }
    }

    public void updateArrival(LocalDateTime arrival, LegEdge previous) {
        this.arrival = arrival;
        legToPrevious = previous;
    }

    public void resetArrival() {
        updateArrival(LocalDateTime.MAX, null);
    }
}

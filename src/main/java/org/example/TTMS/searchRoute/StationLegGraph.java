package org.example.TTMS.searchRoute;

import org.example.TTMS.leg.Leg;
import org.example.TTMS.route.Route;
import org.example.TTMS.station.Station;

import java.time.LocalDateTime;
import java.util.*;

public class StationLegGraph {
    private List<StationVertex> stationVertices;
    private Map<Station, StationVertex> stationMap;

    private void resetGraph() {
        stationVertices.forEach(StationVertex::resetArrival);
    }

    public StationLegGraph(Iterable<Station> stations, Iterable<Leg> legs) {
        stationVertices = new ArrayList<>();
        stationMap = new HashMap<>();

        stations.forEach((Station s) -> {
            StationVertex stationVertex = new StationVertex(s);
            stationVertices.add(stationVertex);
            stationMap.put(s, stationVertex);
        });

        for (Leg l: legs) {
            StationVertex legOrigin = stationMap.get(l.getOrigin());
            StationVertex legDestination = stationMap.get(l.getDestination());
            legOrigin.addLegToStation(legDestination, l);
        }
    }

    private Route findRouteWithDepartureTime(Station origin, Station destination, LocalDateTime departureTime) {
        ArrayList<StationVertex> queue = new ArrayList<>(stationVertices);
        stationMap.get(origin).updateArrival(departureTime, null);

        while (!queue.isEmpty()) {
            queue.sort((s1, s2) -> -1 * s1.getArrival().compareTo(s2.getArrival()));
            StationVertex stationVertex = queue.remove(queue.size() - 1);
            stationVertex.reduceNeighbours();
        }

        Deque<Leg> route = new ArrayDeque<>();
        StationVertex sv = stationMap.get(destination);
        if (sv.getArrival() == LocalDateTime.MAX) {
            return null;
        }

        while (true) {
            LegEdge le = sv.getLegToPrevious();
            if (le == null)
                break;
            route.addFirst(le.getLeg());
            sv = le.getOrigin();
            sv.removeLegToStation(le);
        }

        resetGraph();

        return new Route(new ArrayList<>(route));
    }

    public List<Route> findRoutesWithDepartureTime(Station origin, Station destination, LocalDateTime departureTime, int resultCount) {
        List<Route> routes = new ArrayList<>();

        for (int i = 0; i < resultCount; i++) {
            Route route = findRouteWithDepartureTime(origin, destination, departureTime);
            if (route != null)
                routes.add(route);
        }

        return routes;
    }
}

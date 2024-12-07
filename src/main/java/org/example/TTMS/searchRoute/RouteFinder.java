package org.example.TTMS.searchRoute;

import org.example.TTMS.leg.Leg;
import org.example.TTMS.route.Route;
import org.example.TTMS.station.Station;
import org.example.TTMS.leg.LegRepository;
import org.example.TTMS.station.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service("RouteFinder")
public class RouteFinder {
    @Autowired
    private LegRepository legRepository;

    @Autowired
    private StationRepository stationRepository;

    public List<Route> findRoutesWithDepartureTime(Station origin, Station destination, LocalDateTime departureTime, int resultCount) {
        LocalDateTime limitTime = departureTime.plusDays(2);
        Iterable<Station> stationList = stationRepository.findAll();
        List<Leg> legs = legRepository.findByDepartureTimeBetween(departureTime, limitTime);

        StationLegGraph graph = new StationLegGraph(stationList, legs);

        return graph.findRoutesWithDepartureTime(origin, destination, departureTime, resultCount);
    }
}

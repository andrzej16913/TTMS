package org.example.TTMS.searchRoute;

import org.example.TTMS.leg.Leg;
import org.example.TTMS.route.Route;
import org.example.TTMS.station.Station;
import org.example.TTMS.leg.LegRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service("RouteFinder")
public class RouteFinder {
    @Autowired
    private LegRepository legRepository;

    public List<Route> findRoutesWithDepartureTime(Station origin, Station destination, LocalDateTime departureTime, int resultCount) {
        List<Route> routes = new ArrayList<>();
        LocalDateTime limitTime = departureTime.plusDays(2);
        List<Leg> legs = legRepository.findByDepartureTimeBetweenAndOriginAndDestination(departureTime, limitTime, origin, destination);

        for (Leg leg: legs) {
            List<Leg> legList = new ArrayList<>();
            legList.add(leg);
            routes.add(new Route(legList));
        }

        return routes;
    }
}

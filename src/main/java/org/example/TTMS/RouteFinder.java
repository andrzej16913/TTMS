package org.example.TTMS;

import org.example.TTMS.entities.Leg;
import org.example.TTMS.entities.Station;
import org.example.TTMS.repositories.LegRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service("RouteFinder")
public class RouteFinder {
    @Autowired
    private LegRepository legRepository;

    public List<List<Leg>> findRoutesWithDepartureTime(Station origin, Station destination, LocalDateTime departureTime, int resultCount) {
        List<List<Leg>> routes = new ArrayList<>();
        LocalDateTime limitTime = departureTime.plusDays(2);
        List<Leg> legs = legRepository.findByDepartureTimeBetweenAndOriginAndDestination(departureTime, limitTime, origin, destination);

        for (Leg leg: legs) {
            List<Leg> route = new ArrayList<>();
            route.add(leg);
            routes.add(route);
        }

        return routes;
    }
}

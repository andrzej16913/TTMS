package org.example.TTMS;

import org.example.TTMS.entities.Leg;
import org.example.TTMS.responses.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("RouteParser")
public class RouteParser {
    @Autowired
    private TripFactory tripFactory;

    public List<List<Trip>> parseLegsToTrips(List<List<Leg>> routes) {
        List<List<Trip>> resultRoutes = new ArrayList<>();
        for (List<Leg> route: routes) {
            List<Trip> resultRoute = new ArrayList<>();
            if (route.isEmpty()) {
                resultRoutes.add(resultRoute);
                continue;
            }
            Leg firstLeg = route.get(0);
            Leg previousLeg = firstLeg;

            for (Leg leg: route) {
                if (leg.getService().equals(firstLeg.getService())) {
                    previousLeg = leg;
                } else {
                    resultRoute.add(tripFactory.createTripFromLegs(firstLeg, previousLeg));
                    firstLeg = leg;
                    previousLeg = leg;
                }
            }

            resultRoute.add(tripFactory.createTripFromLegs(firstLeg, previousLeg));
            resultRoutes.add(resultRoute);
        }

        return resultRoutes;
    }
}

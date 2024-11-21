package org.example.TTMS.searchRoute;

import org.example.TTMS.leg.Leg;
import org.example.TTMS.route.Route;
import org.example.TTMS.station.Station;
import org.example.TTMS.station.StationRepository;
import org.example.TTMS.trip.Trip;
import org.example.TTMS.trip.TripFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SearchRouteController {
    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private RouteFinder routeFinder;

    @Autowired
    private TripFactory tripFactory;

    private List<Trip> routeAsListOfTrips(Route route) {
        List<Trip> resultRoute = new ArrayList<>();

        List<Leg> legs = route.getLegList();
        Leg firstLeg = legs.get(0);
        Leg previousLeg = firstLeg;

        for (Leg leg: legs) {
            if (leg.getItinerary().equals(firstLeg.getItinerary())) {
                previousLeg = leg;
            } else {
                resultRoute.add(tripFactory.createTripFromLegs(firstLeg, previousLeg));
                firstLeg = leg;
                previousLeg = leg;
            }
        }

        resultRoute.add(tripFactory.createTripFromLegs(firstLeg, previousLeg));

        return resultRoute;
    }

    private List<List<Trip>> parseRoutesToTrips(List<Route> routes) {
        return routes.stream()
                .map(this::routeAsListOfTrips)
                .collect(Collectors.toList());
    }

    @GetMapping("/searchRoute")
    public List<List<Trip>> searchRoute(@RequestParam long departureStationId,
                                        @RequestParam long arrivalStationId,
                                        @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) OffsetDateTime dateAndTime,
                                        @RequestParam(required = false, defaultValue = "false") Boolean timeIsArrival,
                                        @RequestParam(required = false, defaultValue = "5") int maxResultCount) {
        //throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Incorrect departure station ID");

        Station departureStation = stationRepository.findById(departureStationId).orElseThrow();
        Station arrivalStation = stationRepository.findById(arrivalStationId).orElseThrow();

        LocalDateTime dateTime = dateAndTime.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();

        List<Route> listOfLegs = routeFinder.findRoutesWithDepartureTime(departureStation, arrivalStation, dateTime, maxResultCount);

        return parseRoutesToTrips(listOfLegs);
    }
}

package org.example.TTMS;

import org.example.TTMS.entities.Leg;
import org.example.TTMS.entities.Station;
import org.example.TTMS.repositories.StationRepository;
import org.example.TTMS.responses.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;

@RestController
public class TTMSController {
    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private RouteFinder routeFinder;

    @Autowired
    private RouteParser routeParser;

    @GetMapping("/test")
    public TestResponse testEndpoint(@RequestParam(value = "question", defaultValue = "No question") String question) {
        return new TestResponse(question);
    }

    @GetMapping("/searchRoute")
    public List<List<Trip>> searchRoute(@RequestParam(value = "departureStationId") String departureStationId,
                                        @RequestParam(value = "arrivalStationId") String arrivalStationId,
                                        @RequestParam(value = "dateAndTime")@DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) OffsetDateTime dateAndTime,
                                        @RequestParam(value = "timeIsArrival", required = false, defaultValue = "false") Boolean timeIsArrival) {
        int maxResultCount = 5;
        long departureId = Long.decode(departureStationId);
        long arrivalId = Long.decode(arrivalStationId);
        Optional<Station> departureOptional = stationRepository.findById(departureId);
        Optional<Station> arrivalOptional = stationRepository.findById(arrivalId);

        if (departureOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Incorrect departure station ID");
        }

        if (arrivalOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Incorrect arrival station ID");
        }

        Station departureStation = departureOptional.get();
        Station arrivalStation = arrivalOptional.get();

        LocalDateTime dateTime = dateAndTime.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();

        List<List<Leg>> listOfLegs = routeFinder.findRoutesWithDepartureTime(departureStation, arrivalStation, dateTime, maxResultCount);

        return routeParser.parseLegsToTrips(listOfLegs);
    }
}


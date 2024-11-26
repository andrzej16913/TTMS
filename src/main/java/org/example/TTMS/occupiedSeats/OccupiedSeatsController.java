package org.example.TTMS.occupiedSeats;

import org.example.TTMS.itinerary.Itinerary;
import org.example.TTMS.itinerary.ItineraryRepository;
import org.example.TTMS.leg.Leg;
import org.example.TTMS.route.Route;
import org.example.TTMS.seat.ResponseSeat;
import org.example.TTMS.seat.Seat;
import org.example.TTMS.seatLeg.SeatLeg;
import org.example.TTMS.seatLeg.SeatLegRepository;
import org.example.TTMS.station.Station;
import org.example.TTMS.station.StationRepository;
import org.example.TTMS.vehicle.ResponseVehicle;
import org.example.TTMS.vehicle.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class OccupiedSeatsController {
    @Autowired
    private ItineraryRepository itineraryRepository;

    @Autowired
    private SeatLegRepository seatLegRepository;

    @Autowired
    private StationRepository stationRepository;

    private List<ResponseVehicle> parseMapOfVehicles(Map<Vehicle, List<Seat>> map) {
        List<ResponseVehicle> vehicleList = new ArrayList<>();

        for (Map.Entry<Vehicle, List<Seat>> entry: map.entrySet()) {
            Vehicle vehicle = entry.getKey();
            List<Seat> seatList = entry.getValue();
            List<ResponseSeat> responseSeatList = new ArrayList<>();

            for (Seat seat: seatList) {
                responseSeatList.add(new ResponseSeat(seat.getId(), seat.getName()));
            }

            vehicleList.add(new ResponseVehicle(vehicle.getId(), responseSeatList));
        }

        return vehicleList;
    }

    private Map<Vehicle, List<Seat>> seatToVehicleSort(Set<Vehicle> vehicles, Set<Seat> seats) {
        Map<Vehicle, List<Seat>> map = new HashMap<>();

        for (Vehicle vehicle: vehicles) {
            map.put(vehicle, new ArrayList<>());
        }

        for (Seat seat: seats) {
            Vehicle vehicle = seat.getVehicle();
            if (map.containsKey(vehicle)) {
                List<Seat> seatList = map.get(vehicle);
                seatList.add(seat);
                map.put(vehicle, seatList);
            }
        }

        return map;
    }

    private Set<Seat> getOccupiedSeatsOnRoute(Route route) {
        Set<Seat> occupiedSeats = new HashSet<>();

        for (Leg leg: route.getLegList()) {
            Set<Seat> legSeats = seatLegRepository.findByLeg(leg).stream()
                    .filter(SeatLeg::isOccupied)
                    .map(SeatLeg::getSeat)
                    .collect(Collectors.toSet());
            occupiedSeats.addAll(legSeats);
        }

        return occupiedSeats;
    }

    @GetMapping("/occupiedSeats")
    public List<ResponseVehicle> occupiedSeats(@RequestParam long itineraryId,
                                               @RequestParam long departureStationId,
                                               @RequestParam long arrivalStationId) {
        try {
            Itinerary itinerary = itineraryRepository.findById(itineraryId).orElseThrow();
            Station departureStation = stationRepository.findById(departureStationId).orElseThrow();
            Station arrivalStation = stationRepository.findById(arrivalStationId).orElseThrow();


            Route route = new Route(itinerary);
            route.cutOutsideOf(departureStation, arrivalStation);
            Set<Seat> seatSet = getOccupiedSeatsOnRoute(route);
            Map<Vehicle, List<Seat>> map = seatToVehicleSort(itinerary.getVehicles(), seatSet);
            return parseMapOfVehicles(map);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(404, "Incorrect resourde ID", e);
        }
    }
}

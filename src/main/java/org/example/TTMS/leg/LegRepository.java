package org.example.TTMS.leg;

import org.example.TTMS.station.Station;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface LegRepository extends CrudRepository<Leg, Long> {
    List<Leg> findByOriginAndDestination(Station origin, Station destination);
    List<Leg> findByDepartureTimeBetweenAndOriginAndDestination(LocalDateTime departureTime, LocalDateTime timeLimit, Station origin, Station destination);
    List<Leg> findByDepartureTimeBetween(LocalDateTime departureTime, LocalDateTime timeLimit);
}

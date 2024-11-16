package org.example.TTMS.repositories;

import org.example.TTMS.entities.Leg;
import org.example.TTMS.entities.Station;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface LegRepository extends CrudRepository<Leg, Long> {
    List<Leg> findByOriginAndDestination(Station origin, Station destination);
    List<Leg> findByDepartureTimeBetweenAndOriginAndDestination(LocalDateTime departureTime, LocalDateTime timeLimit, Station origin, Station destination);
}

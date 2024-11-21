package org.example.TTMS.seatLeg;

import org.example.TTMS.leg.Leg;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SeatLegRepository extends CrudRepository<SeatLeg, Long> {
    List<SeatLeg> findByLeg(Leg leg);
}

package org.example.TTMS.repositories;

import org.example.TTMS.entities.Price;
import org.example.TTMS.entities.Station;
import org.example.TTMS.entities.Tariff;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PriceRepository extends CrudRepository<Price, Long> {
    List<Price> findByTariffAndOriginAndDestination(Tariff tariff, Station origin, Station destination);
}

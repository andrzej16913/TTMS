package org.example.TTMS.price;

import org.example.TTMS.station.Station;
import org.example.TTMS.tariff.Tariff;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PriceRepository extends CrudRepository<Price, Long> {
    List<Price> findByTariffAndOriginAndDestination(Tariff tariff, Station origin, Station destination);
}

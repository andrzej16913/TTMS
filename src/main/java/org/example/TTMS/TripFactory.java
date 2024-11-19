package org.example.TTMS;

import org.example.TTMS.entities.Leg;
import org.example.TTMS.entities.Price;
import org.example.TTMS.entities.Service;
import org.example.TTMS.entities.Station;
import org.example.TTMS.repositories.PriceRepository;
import org.example.TTMS.responses.Trip;
import org.example.TTMS.util.VehicleType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service("TripFactory")
public class TripFactory {
    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private LowestPriceFinder lowestPriceFinder;

    public Trip createTripFromLegs(Leg firstLeg, Leg lastLeg) {
        Station origin = firstLeg.getOrigin();
        Station destination = lastLeg.getDestination();
        Service service = firstLeg.getService();
        VehicleType vehicleType = service.getVehicleType();
        List<Price> prices = priceRepository.findByTariffAndOriginAndDestination(service.getTariff(), origin, destination);
        Price price = lowestPriceFinder.find(prices);

        return new Trip(origin.getId(), origin.getName(), firstLeg.getDepartureTimeAsIsoString(),
                destination.getId(), destination.getName(), lastLeg.getArrivalTimeAsIsoString(),
                service.getId(), price.asMapForAPI(), service.getName(), vehicleType.toString(),
                service.isReservationAvailable(), service.isReservationRequired());
    }
}

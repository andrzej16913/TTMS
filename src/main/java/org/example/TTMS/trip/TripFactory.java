package org.example.TTMS.trip;

import org.example.TTMS.itinerary.Itinerary;
import org.example.TTMS.leg.Leg;
import org.example.TTMS.price.Price;
import org.example.TTMS.station.Station;
import org.example.TTMS.price.PriceRepository;
import org.example.TTMS.vehicleModel.VehicleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("TripFactory")
public class TripFactory {
    @Autowired
    private PriceRepository priceRepository;

    public Trip createTripFromLegs(Leg firstLeg, Leg lastLeg) {
        Station origin = firstLeg.getOrigin();
        Station destination = lastLeg.getDestination();
        Itinerary itinerary = firstLeg.getItinerary();
        VehicleType vehicleType = itinerary.getVehicleType();
        Price price = priceRepository.findByTariffAndOriginAndDestination(itinerary.getTariff(), origin, destination)
                .stream()
                .min(Price::compareTo)
                .orElse(new Price());

        return new Trip(origin.getId(), origin.getName(), firstLeg.getDepartureTimeAsIsoString(),
                destination.getId(), destination.getName(), lastLeg.getArrivalTimeAsIsoString(),
                itinerary.getId(), price.asMapForAPI(), itinerary.getName(), vehicleType.toString(),
                itinerary.isReservationAvailable(), itinerary.isReservationRequired());
    }
}

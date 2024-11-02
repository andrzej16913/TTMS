package org.example.TTMS.responses;

import java.util.Map;

public record Trip(int departureStationId, String departureStationName, String departureDateTime,
                   int arrivalStationId, String arrivalStationName, String arrivalDateTime,
                   Map<String, String> price, String serviceName, String vehicleType,
                   boolean reservationAvailble, boolean reservationRequired) {
}

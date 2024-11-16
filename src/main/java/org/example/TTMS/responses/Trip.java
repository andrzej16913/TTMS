package org.example.TTMS.responses;

import java.util.Map;

public record Trip(long departureStationId, String departureStationName, String departureDateTime,
                   long arrivalStationId, String arrivalStationName, String arrivalDateTime,
                   Map<String, String> price, String serviceName, String vehicleType,
                   boolean reservationAvailble, boolean reservationRequired) {
}

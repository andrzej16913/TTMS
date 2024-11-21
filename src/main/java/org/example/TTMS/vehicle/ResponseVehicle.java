package org.example.TTMS.vehicle;

import org.example.TTMS.seat.ResponseSeat;

import java.util.List;

public record ResponseVehicle(long vehicleModelId, List<ResponseSeat> occupiedSeats) {
}

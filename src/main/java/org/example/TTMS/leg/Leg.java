package org.example.TTMS.leg;

import jakarta.persistence.*;
import org.example.TTMS.itinerary.Itinerary;
import org.example.TTMS.station.Station;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Leg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    //@Column(nullable = false)
    private Station origin;

    @ManyToOne
    //@Column(nullable = false)
    private Station destination;

    @ManyToOne
    //@Column(nullable = false)
    private Itinerary itinerary;

    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Station getOrigin() {
        return origin;
    }

    public void setOrigin(Station origin) {
        this.origin = origin;
    }

    public Station getDestination() {
        return destination;
    }

    public void setDestination(Station destination) {
        this.destination = destination;
    }

    public Itinerary getItinerary() {
        return itinerary;
    }

    public void setService(Itinerary itinerary) {
        this.itinerary = itinerary;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDepartureTimeAsIsoString() {
        return getDepartureTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public String getArrivalTimeAsIsoString() {
        return getArrivalTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}

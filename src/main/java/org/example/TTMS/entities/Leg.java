package org.example.TTMS.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Leg {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToOne
    //@Column(nullable = false)
    private Station origin;

    @ManyToOne
    //@Column(nullable = false)
    private Station destination;

    @ManyToOne
    //@Column(nullable = false)
    private Service route;

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
}

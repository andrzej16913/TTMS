package org.example.TTMS.seatLeg;

import jakarta.persistence.*;
import org.example.TTMS.leg.Leg;
import org.example.TTMS.seat.Seat;

@Entity
public class SeatLeg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Seat seat;

    @ManyToOne
    private Leg leg;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Leg getLeg() {
        return leg;
    }

    public void setLeg(Leg leg) {
        this.leg = leg;
    }

    public boolean isOccupied() {
        return false;
    }
}
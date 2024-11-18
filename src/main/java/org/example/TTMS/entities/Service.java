package org.example.TTMS.entities;

import jakarta.persistence.*;
import org.example.TTMS.util.VehicleType;

import java.util.List;
import java.util.Set;

@Entity
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String callSign;
    private boolean reservationAvailable;
    private boolean reservationRequired;
    private VehicleType vehicleType;

    @OneToMany(mappedBy = "service")
    private List<Leg> legs;

    @ManyToOne
    private Tariff tariff;

    @ManyToMany
    Set<Vehicle> vehicles;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCallSign() {
        return callSign;
    }

    public void setCallSign(String callSign) {
        this.callSign = callSign;
    }

    public boolean isReservationAvailable() {
        return reservationAvailable;
    }

    public void setReservationAvailable(boolean reservationAvailable) {
        this.reservationAvailable = reservationAvailable;
    }

    public boolean isReservationRequired() {
        return reservationRequired;
    }

    public void setReservationRequired(boolean reservationRequired) {
        this.reservationRequired = reservationRequired;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public List<Leg> getLegs() {
        return legs;
    }

    public void setLegs(List<Leg> legs) {
        this.legs = legs;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    public Set<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Set<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }


}

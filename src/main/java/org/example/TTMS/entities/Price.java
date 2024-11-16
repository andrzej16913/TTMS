package org.example.TTMS.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Tariff tariff;

    @ManyToOne
    private Station origin;

    @ManyToOne
    private Station destination;

    @ManyToOne
    private TravelClass travelClass;

    private BigDecimal amount;
    private Currency currency;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    public Station getDestination() {
        return destination;
    }

    public void setDestination(Station destination) {
        this.destination = destination;
    }

    public Station getOrigin() {
        return origin;
    }

    public void setOrigin(Station origin) {
        this.origin = origin;
    }

    public TravelClass getTravelClass() {
        return travelClass;
    }

    public void setTravelClass(TravelClass travelClass) {
        this.travelClass = travelClass;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Map<String, String> asMapForAPI() {
        Map<String, String> map = new HashMap<>();
        if (amount == null) {
            map.put("amount", "");
        } else {
            map.put("amount", amount.toPlainString());
        }
        if (currency == null) {
            map.put("currency", "");
        } else {
            map.put("currency", currency.getCurrencyCode());
        }
        return map;
    }
}

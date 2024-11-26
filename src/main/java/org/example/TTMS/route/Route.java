package org.example.TTMS.route;

import org.example.TTMS.itinerary.Itinerary;
import org.example.TTMS.leg.Leg;
import org.example.TTMS.station.Station;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Route {
    private List<Leg> legs;

    public static boolean checkIfIsRoute(List<Leg> legs) {
        if (legs.isEmpty())
            return false;

        Iterator<Leg> it = legs.iterator();
        Leg previousLeg = it.next();

        while (it.hasNext()) {
            Leg currentLeg = it.next();
            if (!previousLeg.getDestination().equals(currentLeg.getOrigin()) ||
                previousLeg.getArrivalTime().isAfter(currentLeg.getDepartureTime())) {
                return false;
            }
            previousLeg = currentLeg;
        }

        return true;
    }

    public Route(List<Leg> legs) {
        if (checkIfIsRoute(legs)) {
            this.legs = legs;
        } else {
            throw new NotARouteException("Provided list of Leg objects is not a valid route");
        }

        //legs.sort(Comparator.nullsLast(Comparator.comparing(Leg::getDepartureTime)));
    }

    public Route(Itinerary itinerary) {
        legs = itinerary.getLegs();
    }

    public List<Leg> getLegList() {
        return legs;
    }

    public void cutLegsAfterFirstAppearance(Station station) {
        List<Leg> newList = new ArrayList<>();
        Iterator<Leg> it = legs.iterator();

        while (it.hasNext()) {
            Leg currentLeg = it.next();
            if (currentLeg.getOrigin().equals(station)) {
                break;
            }
            newList.add(currentLeg);
        }

        legs = newList;
    }

    public void cutLegsBeforeFirstAppearance(Station station) {
        List<Leg> newList = new ArrayList<>();
        Iterator<Leg> it = legs.iterator();

        while (it.hasNext()) {
            Leg currentLeg = it.next();
            if (currentLeg.getOrigin().equals(station)) {
                newList.add(currentLeg);
                break;
            }
        }

        while (it.hasNext()) {
            newList.add(it.next());
        }

        legs = newList;
    }

    public void cutOutsideOf(Station origin, Station destination) {
        this.cutLegsBeforeFirstAppearance(origin);
        this.cutLegsAfterFirstAppearance(destination);
    }
}

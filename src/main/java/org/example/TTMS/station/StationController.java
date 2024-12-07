package org.example.TTMS.station;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StationController {
    @Autowired
    private StationRepository stationRepository;

    @GetMapping("/station")
    public Iterable<Station> stations() {
        return stationRepository.findAll();
    }
}

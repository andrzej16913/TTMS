package org.example.TTMS;

import org.example.TTMS.responses.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TTMSController {
    @GetMapping("/test")
    public TestResponse testEndpoint(@RequestParam(value = "question", defaultValue = "No question") String question) {
        return new TestResponse(question);
    }

    @GetMapping("/searchRoute")
    public List<List<Trip>> searchRoute(@RequestParam(value = "departureStationId") String departureStationId,
                                        @RequestParam(value = "arrivalStationId") String arrivalStationId,
                                        @RequestParam(value = "dateAndTime")@DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) OffsetDateTime dateAndTime,
                                        @RequestParam(value = "timeIsArrival", required = false, defaultValue = "false") Boolean timeIsArrival) {
        List<List<Trip>> foundRoutes = new ArrayList<>();

        return foundRoutes;
    }
}


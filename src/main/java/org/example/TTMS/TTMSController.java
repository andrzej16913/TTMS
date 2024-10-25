package org.example.TTMS;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TTMSController {
    @GetMapping("/test")
    public TestResponse testEndpoint(@RequestParam(value = "question", defaultValue = "No question") String question) {
        return new TestResponse(question);
    }
}


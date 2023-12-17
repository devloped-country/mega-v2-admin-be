package com.app.mega.config.healthCheck;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
    @GetMapping("/health")
    public String getHealthCheck() {
        return "건강해!";
    }
}

package com.app.mega.config.healthCheck;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
    @GetMapping("/health")
    public String getHealthCheck() {
        System.out.println("건강해질거야!");
        return "건강해질거야!";
    }
}

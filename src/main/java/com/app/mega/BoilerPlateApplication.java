package com.app.mega;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BoilerPlateApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoilerPlateApplication.class, args);
	}

}

package com.greenbuilding.trainingbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Main entry point that boots the Spring application context.
@SpringBootApplication
public class TrainingBackendApplication {

	public static void main(String[] args) {
		// Starts the embedded server and wires all Spring-managed beans.
		SpringApplication.run(TrainingBackendApplication.class, args);
	}

}

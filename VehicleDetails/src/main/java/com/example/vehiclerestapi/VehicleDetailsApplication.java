package com.example.vehiclerestapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class VehicleDetailsApplication {

	public static void main(String[] args) {
		SpringApplication.run(VehicleDetailsApplication.class, args);
	}

}

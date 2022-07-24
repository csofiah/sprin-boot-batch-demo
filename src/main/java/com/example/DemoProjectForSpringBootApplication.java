package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DemoProjectForSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoProjectForSpringBootApplication.class, args);
	}
 
}

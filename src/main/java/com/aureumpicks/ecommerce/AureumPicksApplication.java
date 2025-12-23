package com.aureumpicks.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AureumPicksApplication {

	public static void main(String[] args) {
		SpringApplication.run(AureumPicksApplication.class, args);
	}

}

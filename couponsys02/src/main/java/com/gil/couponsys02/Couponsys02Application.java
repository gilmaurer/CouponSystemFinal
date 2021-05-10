package com.gil.couponsys02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Couponsys02Application {

	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
		SpringApplication.run(Couponsys02Application.class, args);
		System.out.println("started!!!!");
	}

}

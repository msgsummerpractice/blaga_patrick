package com.example.springbootapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootappApplication {

	private static final Logger logger = LoggerFactory.getLogger(SpringbootappApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(SpringbootappApplication.class, args);
		logger.info("Main is working fine");
	}

}

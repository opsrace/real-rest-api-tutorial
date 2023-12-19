package com.api.tutorials;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class ApiExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiExampleApplication.class, args);
	}

}

package com.example.sofkatransaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SofkaTransactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(SofkaTransactionApplication.class, args);
	}

}

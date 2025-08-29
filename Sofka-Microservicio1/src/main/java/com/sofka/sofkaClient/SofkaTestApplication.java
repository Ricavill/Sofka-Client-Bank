package com.sofka.sofkaClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SofkaTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SofkaTestApplication.class, args);
    }

}

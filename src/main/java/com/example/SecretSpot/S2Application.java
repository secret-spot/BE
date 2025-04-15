package com.example.SecretSpot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class S2Application {

    public static void main(String[] args) {
        SpringApplication.run(S2Application.class, args);
    }

}

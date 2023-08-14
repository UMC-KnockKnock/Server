package com.example.knockknock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@EnableJpaRepositories
@SpringBootApplication
public class KnockKnockApplication {

    public static void main(String[] args) {
        SpringApplication.run(KnockKnockApplication.class, args);
    }

}

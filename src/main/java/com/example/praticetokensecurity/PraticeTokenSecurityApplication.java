package com.example.praticetokensecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@EnableCaching
public class PraticeTokenSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(PraticeTokenSecurityApplication.class, args);
    }

}

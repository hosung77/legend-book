package com.example.praticetokensecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@EnableJpaAuditing
@SpringBootApplication
public class PraticeTokenSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(PraticeTokenSecurityApplication.class, args);
    }

}

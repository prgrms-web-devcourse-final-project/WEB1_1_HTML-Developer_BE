package com.backend.allreva;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AllrevaApplication {

    public static void main(String[] args) {
        SpringApplication.run(AllrevaApplication.class, args);
    }

}

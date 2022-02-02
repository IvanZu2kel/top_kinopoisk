package com.example.top_kinopoisk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class TopKinopoiskApplication {

    public static void main(String[] args) {
        SpringApplication.run(TopKinopoiskApplication.class, args);
    }

}

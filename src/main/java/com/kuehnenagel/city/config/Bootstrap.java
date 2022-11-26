package com.kuehnenagel.city.config;

import com.kuehnenagel.city.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Bootstrap {

    @Autowired
    private CityService cityService;

    @Bean
    CommandLineRunner loadCities() {
        return args -> cityService.loadCities();
    }
}

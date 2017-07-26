package com.cv.aircraft;

import com.fasterxml.jackson.contrib.jsonpath.DefaultJsonUnmarshaller;
import com.fasterxml.jackson.contrib.jsonpath.JsonUnmarshaller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class AircraftApplication {

    public static void main(String[] args) {
        SpringApplication.run(AircraftApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public JsonUnmarshaller jsonUnmarshaller() {
        return new DefaultJsonUnmarshaller();
    }
}

package com.cv.aircraft.dto;

import com.fasterxml.jackson.contrib.jsonpath.annotation.JsonPath;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AircraftInfo {
    @JsonPath("$.aircraft.model.text")
    private String model;
    @JsonPath("$.airline.short")
    private String airline;
    @JsonPath("$.airport.origin.position.country.name")
    private String departureCountry;
    @JsonPath("$.airport.origin.position.region.city")
    private String departureCity;
    @JsonPath("$.airport.destination.position.country.name")
    private String arrivalCountry;
    @JsonPath("$.airport.destination.position.region.city")
    private String arrivalCity;
    @JsonPath("$.trail[0].spd")
    private int speed;
    @JsonPath("$.trail[0].alt")
    private int height;
    @JsonPath("$.aircraft.images.thumbnails[0].src")
    private String ico;
    @JsonPath("$.aircraft.images.medium[0].src")
    private String img;
}
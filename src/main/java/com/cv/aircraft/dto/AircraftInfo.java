package com.cv.aircraft.dto;

import com.fasterxml.jackson.contrib.jsonpath.annotation.JsonPath;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AircraftInfo {
    private static final double KMH_IN_KTS = 1.852;
    private static final double METER_TO_FEET = 0.3048;

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
    @Getter(AccessLevel.NONE)
    private int speed;
    @JsonPath("$.trail[0].alt")
    @Getter(AccessLevel.NONE)
    private int height;
    @JsonPath("$.aircraft.images.medium[0].src")
    private String img;

    public int getSpeedInKmH() {
        return (int) Math.round(KMH_IN_KTS * speed);
    }

    public int getHeightInMeters() {
        return (int) Math.round(METER_TO_FEET * height);
    }
}
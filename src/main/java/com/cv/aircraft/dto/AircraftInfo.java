package com.cv.aircraft.dto;

import com.fasterxml.jackson.contrib.jsonpath.annotation.JsonPath;

public class AircraftInfo {
    @JsonPath("$.aircraft.model.text")
    private String model;
    @JsonPath("$.airline.short")
    private String airline;
    @JsonPath("$.airport.origin.position.country.name")
    private String countryDeparture;
    @JsonPath("$.airport.origin.position.region.city")
    private String cityDeparture;
    @JsonPath("$.airport.destination.position.country.name")
    private String countryArrival;
    @JsonPath("$.airport.destination.position.region.city")
    private String cityArrival;
    @JsonPath("$.trail[0].spd")
    private int speed;
    @JsonPath("$.trail[0].spd")
    private int hight;
}

//TODO remove it
//https://data-live.flightradar24.com/clickhandler/?version=1.5&flight=e3c275b
//
//        $.aircraft.model.text      -----  "Embraer ERJ-145LR"
//        $.airline.short            ------"Windrose Airlines"
//        $.airport.origin.position.country.name  --- FROM---- "Bulgaria"
//        $.airport.origin.position.region.city                "Sofia"
//
//        $.airport.destination.position.country.name   --- TO--- "Ukraine"
//        $.airport.destination.position.region.city     "Kiev"
//
//
//        $.trail[0].lat
//        $.trail[0].lng
//        $.trail[0].spd     spead in knots
//        $.trail[0].alt      hight in ft
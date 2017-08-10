package com.cv.aircraft.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Builder
@Getter
public class AirportEntity {
    private String iataCode;
    private Optional<String> cityRus;
    private String cityEng;
    private String countryRus;
    private String countryEng;
}

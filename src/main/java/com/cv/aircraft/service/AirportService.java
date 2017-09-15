package com.cv.aircraft.service;

import com.cv.aircraft.model.AirportEntity;
import com.cv.aircraft.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AirportService {
    @Autowired
    private AirportRepository airportRepository;

    public String getCityAndCountryNameByIataCode(String iataCode) {
        Optional<AirportEntity> maybeAirportEntity = airportRepository.findAirportEntityByIataCode(iataCode);
        String city = "NotFoundCity";
        String country = "NotFoundCountry";
        if (maybeAirportEntity.isPresent()) {
            city = maybeAirportEntity.get().getCityEng();
            country = maybeAirportEntity.get().getCountryEng();
        }
        return String.format("%s (%s)", city, country);
    }
}

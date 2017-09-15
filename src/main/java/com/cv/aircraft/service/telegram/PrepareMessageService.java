package com.cv.aircraft.service.telegram;

import com.cv.aircraft.dto.AircraftInfo;

import org.springframework.stereotype.Component;

@Component
public class PrepareMessageService {
    public String formatAirplaneInfo(AircraftInfo aircraftInfo) {
        String template = "Model: %s\nAirline: %s\nDepartureCountry: %s\nDepartureCity: %s\nArrivalCountry: %s\nArrivalCity: %s\nSpeed: %s km/h\nHeight: %s m\nimg: %s";

        return String.format(template,
                aircraftInfo.getModel(), aircraftInfo.getAirline(),
                aircraftInfo.getDepartureCountry(), aircraftInfo.getDepartureCity(),
                aircraftInfo.getArrivalCountry(), aircraftInfo.getArrivalCity(),
                aircraftInfo.getSpeedInKmH(), aircraftInfo.getHeightInMeters(),
                aircraftInfo.getImg());
    }
}

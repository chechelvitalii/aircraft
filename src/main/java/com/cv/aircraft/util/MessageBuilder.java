package com.cv.aircraft.util;

import com.cv.aircraft.dto.AircraftInfo;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MessageBuilder {
    public static String buildAirplaneInfoMessage(AircraftInfo airplane) {
        return "Airline: " + airplane.getAirline() +
                "\nModel: " + airplane.getModel() +
                "\nDepartureCountry: " + airplane.getDepartureCountry() +
                "\nDepartureCity: " + airplane.getDepartureCity() +
                "\nArrivalCountry: " + airplane.getArrivalCountry() +
                "\nArrivalCity: " + airplane.getArrivalCity() +
                "\nSpeed: " + airplane.getSpeedInKmH() +
                "\nHeight: " + airplane.getHeightInMeters() +
                "\nimg: " + airplane.getImg();

    }
}

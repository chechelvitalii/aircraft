package com.cv.aircraft.service;

import com.cv.aircraft.dto.Zone;
import org.springframework.beans.factory.annotation.Value;


public class AircaraftService {

    @Value("url.flight_in_zone")
    private String flightInZoneUrl;
    //50 lat 30 lon
    public void getAircraftsIn(Zone area) {

    }
}

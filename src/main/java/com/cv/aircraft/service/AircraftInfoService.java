package com.cv.aircraft.service;

import com.cv.aircraft.dto.AircraftInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class AircraftInfoService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${url.aircraft.info}")
    private String aircraftInfoUrl;

    public List<AircraftInfo> getAircraftInfos(List<String> aircraftIds) {
        List<AircraftInfo> aircraftInfos = new ArrayList<>();
//restTemplate.getForObject(aircraftIds)

        return aircraftInfos;
    }
}

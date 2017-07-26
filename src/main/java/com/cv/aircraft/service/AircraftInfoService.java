package com.cv.aircraft.service;

import com.cv.aircraft.dto.AircraftInfo;
import com.fasterxml.jackson.contrib.jsonpath.JsonUnmarshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class AircraftInfoService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private JsonUnmarshaller jsonUnmarshaller;
    @Value("${url.aircraft.info}")
    private String aircraftInfoUrl;

    public List<AircraftInfo> getAircraftInfos(List<String> aircraftIds) {
        List<AircraftInfo> aircraftInfos = new ArrayList<>();
        for (String aircraftId : aircraftIds) {
            String aircraftJsonInfo = restTemplate.getForObject(aircraftInfoUrl, String.class, aircraftId);
            AircraftInfo aircraftInfo = convertToAircraftInfo(aircraftJsonInfo);
            aircraftInfos.add(aircraftInfo);
        }
        return aircraftInfos;
    }

    private AircraftInfo convertToAircraftInfo(String json) {
        AircraftInfo aircraftInfo;
        try {
            aircraftInfo = jsonUnmarshaller.unmarshal(AircraftInfo.class, json);
        } catch (IOException e) {
            //TODO print in readable format
            throw new RuntimeException("Can't unmarshal json: "+json);
        }
        return aircraftInfo;
    }

}

package com.cv.aircraft.service;

import com.cv.aircraft.dto.AircraftInfo;
import com.fasterxml.jackson.contrib.jsonpath.JsonUnmarshaller;
import com.google.common.annotations.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class AircraftInfoIdService extends AircraftService {

    private RestTemplate restTemplate;
    private JsonUnmarshaller jsonUnmarshaller;

    @Autowired
    public AircraftInfoIdService(RestTemplate restTemplate, JsonUnmarshaller jsonUnmarshaller) {
        this.restTemplate = restTemplate;
        this.jsonUnmarshaller = jsonUnmarshaller;
    }

    @Value("${url.aircraft.info}")
    private String aircraftInfoUrl;

    public List<AircraftInfo> getAircraftInfos(Set<String> aircraftIds) {
        List<AircraftInfo> aircraftInfos = new ArrayList<>();
        for (String aircraftId : aircraftIds) {
            String preparedUrl = prepareUrl(aircraftId);
            ResponseEntity<String> response = restTemplate.exchange(preparedUrl, HttpMethod.GET, RequestUtils.defaultHeaders(), String.class);
            AircraftInfo aircraftInfo = convertToAircraftInfo(response.getBody());
            aircraftInfos.add(aircraftInfo);
        }
        return aircraftInfos;
    }

    @VisibleForTesting
    AircraftInfo convertToAircraftInfo(String json) {
        AircraftInfo aircraftInfo;
        try {
            aircraftInfo = jsonUnmarshaller.unmarshal(AircraftInfo.class, json);
        } catch (IOException e) {
            //TODO print in readable format
            throw new RuntimeException("Can't unmarshal json: " + json);
        }
        return aircraftInfo;
    }

    @Override
    String prepareUrl(Object... vars) {
        return String.format(aircraftInfoUrl,vars);
    }
}

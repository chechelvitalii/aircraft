package com.cv.aircraft.service.aircraft;

import com.cv.aircraft.dto.AircraftInfo;
import com.cv.aircraft.service.RequestUtils;
import com.fasterxml.jackson.contrib.jsonpath.JsonUnmarshaller;
import com.google.common.annotations.VisibleForTesting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Component
public class AircraftInfoService extends AircraftService {

    private RestTemplate restTemplate;
    private JsonUnmarshaller jsonUnmarshaller;

    @Autowired
    public AircraftInfoService(RestTemplate restTemplate, JsonUnmarshaller jsonUnmarshaller) {
        this.restTemplate = restTemplate;
        this.jsonUnmarshaller = jsonUnmarshaller;
    }

    @Value("${url.aircraft.info}")
    private String aircraftInfoUrl;

    public AircraftInfo getAircraftInfos(String aircraftId) {
            String preparedUrl = prepareUrl(aircraftId);
            ResponseEntity<String> response = restTemplate.exchange(preparedUrl, HttpMethod.GET, RequestUtils.defaultHeaders(), String.class);
            return convertToAircraftInfo(response.getBody());
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

package com.cv.aircraft.service;

import com.cv.aircraft.dto.AirplaneShortInfo;
import com.cv.aircraft.dto.TargetArea;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import static com.cv.aircraft.util.RequestUtils.defaultHeaders;
import static java.lang.String.format;
import static java.util.Arrays.asList;

@Slf4j
@Component
public class AircraftIdService extends AircraftService {

    public static final List<String> NOT_BUSINESS_KEYS = asList("full_count", "version");

    @Autowired
    private RestTemplate restTemplate;

    @Value("${url.aircraft.ids}")
    private String aircraftIdsUrl;

    public Set<AirplaneShortInfo> getAirplaneShortInfoInZone(TargetArea targetArea) {
        String preparedUrl = prepareUrl(targetArea.targetAreaToString());
        ResponseEntity<String> response = restTemplate
                .exchange(preparedUrl, HttpMethod.GET, defaultHeaders(), String.class);
        return convertToAirplaneShortInfo(response.getBody());
    }

    private Set<AirplaneShortInfo> convertToAirplaneShortInfo(String json) {
        JSONObject jsonObject = new JSONObject(json);
        Set<String> airplaneIds = jsonObject.keySet();
        airplaneIds.removeAll(NOT_BUSINESS_KEYS);
        Set<AirplaneShortInfo> airplaneShortInfos = new HashSet<>();
        for (String airplaneId : airplaneIds) {
            if (jsonObject.get(airplaneId) instanceof JSONArray) {
                String from = (String) ((JSONArray) jsonObject.get(airplaneId)).get(11);
                String to = (String) ((JSONArray) jsonObject.get(airplaneId)).get(12);
                airplaneShortInfos.add(new AirplaneShortInfo(airplaneId, from, to));
            }
        }
        return airplaneShortInfos;
    }

    @Override
    String prepareUrl(Object... vars) {
        return format(aircraftIdsUrl, vars);
    }
}

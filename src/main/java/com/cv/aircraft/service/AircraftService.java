package com.cv.aircraft.service;

import com.cv.aircraft.dto.Zone;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import static java.lang.String.format;
import static java.util.Arrays.asList;

@Slf4j
@Component
public class AircraftService {

    public static final String AIRCRAFT_ID_INFORMATION = "\\[.+]";
    public static final String CONSTANT_REPLACE_VALUE = "replaceValue";
    public static final String FIELD_SPLITTER = ",\"";

    @Autowired
    private RestTemplate restTemplate;

    @Value("${url.aircraft.ids}")
    private String aircraftIdsUrl;

    public Set<String> getAircraftIdsInZone(Zone zone) {
        Zone.TopLeft topLeft = zone.getTopLeft();
        Zone.BottomRight bottomRight = zone.getBottomRight();
        String preparedUrl = prepareUrl(topLeft, bottomRight);
        HttpEntity<String> headers = prepareHeaders();

        ResponseEntity<String> response = restTemplate
                .exchange(preparedUrl, HttpMethod.GET, headers, String.class);
        return parseAircraftIds(response.getBody());
    }

    private String prepareUrl(Zone.TopLeft topLeft, Zone.BottomRight bottomRight) {
        return format(aircraftIdsUrl,
                topLeft.getLatitude(), bottomRight.getLatitude(),
                topLeft.getLongitude(), bottomRight.getLongitude());
    }

    private HttpEntity<String> prepareHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0;… Gecko/20100101 Firefox/54.0");
        return new HttpEntity("parameters", headers);
    }

    private Set<String> parseAircraftIds(String json) {
        JSONObject jsonObject = new JSONObject(json.trim());
        Set<String> keys = jsonObject.keySet();
        keys.removeAll(asList("full_count", "version"));
        return keys;
//        List<String> aircraftIds = new ArrayList<>();
//        String[] rawAircraftInfo = value.replaceAll(AIRCRAFT_ID_INFORMATION, CONSTANT_REPLACE_VALUE).split(FIELD_SPLITTER);
//
//        if (rawAircraftInfo.length > 2) {
//            range(2, rawAircraftInfo.length)
//                    .forEach(index -> {
//                        String aircraftId = rawAircraftInfo[index].split("\":" + CONSTANT_REPLACE_VALUE)[0];
//                        aircraftIds.add(aircraftId);
//                    });
//        }
//        return aircraftIds;
    }


}

package com.cv.aircraft.service.aircraft;

import com.cv.aircraft.dto.Zone;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import static com.cv.aircraft.service.RequestUtils.defaultHeaders;
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

    public Set<String> getAircraftIdsInZone(Zone zone) {
        Zone.TopLeft topLeft = zone.getTopLeft();
        Zone.BottomRight bottomRight = zone.getBottomRight();
        String preparedUrl = prepareUrl(topLeft.getLatitude(), bottomRight.getLatitude(), topLeft.getLongitude(), bottomRight.getLongitude());
        ResponseEntity<String> response = restTemplate
                .exchange(preparedUrl, HttpMethod.GET, defaultHeaders(), String.class);
        return parseAircraftIds(response.getBody());
    }

    private Set<String> parseAircraftIds(String json) {
        JSONObject jsonObject = new JSONObject(json);
        Set<String> aircraftIds = jsonObject.keySet();
        aircraftIds.removeAll(NOT_BUSINESS_KEYS);
        return aircraftIds;
    }

    @Override
    String prepareUrl(Object... vars) {
        return format(aircraftIdsUrl, vars);
    }
}

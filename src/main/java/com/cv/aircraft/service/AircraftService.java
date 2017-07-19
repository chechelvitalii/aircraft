package com.cv.aircraft.service;

import com.cv.aircraft.dto.Zone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class AircraftService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${url.flight_in_zone}")
    private String flightInZoneUrl;

    public List<String> getAircraftsIn(Zone zone) {
        Zone.TopLeft topLeft = zone.getTopLeft();
        Zone.BottomRight bottomRight = zone.getBottomRight();
        String preparedUrl = prepareUrl(topLeft, bottomRight);
        HttpEntity<String> headers = prepareHeaders();

        ResponseEntity<String> exchange = restTemplate
                .exchange(preparedUrl, HttpMethod.GET, headers, String.class);
        return null;
    }

    private String prepareUrl(Zone.TopLeft topLeft, Zone.BottomRight bottomRight) {
        return flightInZoneUrl
                + topLeft.getLatitude() + ","
                + bottomRight.getLatitude() + ","
                + topLeft.getLongitude() + ","
                + bottomRight.getLongitude();
    }

    private HttpEntity<String> prepareHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0;… Gecko/20100101 Firefox/54.0");
        return new HttpEntity("parameters", headers);
    }

}

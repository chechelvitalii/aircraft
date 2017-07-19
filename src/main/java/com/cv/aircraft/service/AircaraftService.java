package com.cv.aircraft.service;

import com.cv.aircraft.dto.Zone;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


public class AircaraftService {

    @Value("url.flight_in_zone")
    private String flightInZoneUrl;

    public void getAircraftsIn(Zone zone) {
        Zone.TopLeft topLeft = zone.getTopLeft();
        Zone.BottomRight bottomRight = zone.getBottomRight();
        String preparedUrl = prepareUrl(topLeft, bottomRight);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("host","data-live.flightradar24.com");
        headers.add("User-Agent","Mozilla/5.0 (Windows NT 10.0;… Gecko/20100101 Firefox/54.0");
        headers.add("Accept-Language","ru-RU,ru;q=0.8,en-US;q=0.5,en;q=0.3");
        headers.add("Accept-Encoding","gzip, deflate, br");
        headers.add("Connection","keep-alive");
        headers.add("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<String> exchange = restTemplate.exchange("https://data-live.flightradar24.com/zones/fcgi/feed.js?bounds=50.52,50.16,30.70,31.06", HttpMethod.GET, entity, String.class);
        String forObject = restTemplate.getForObject(preparedUrl, String.class);

    }

    private String prepareUrl(Zone.TopLeft topLeft, Zone.BottomRight bottomRight) {
        return flightInZoneUrl
                + topLeft.getLatitude() + ","
                + bottomRight.getLatitude() + ","
                + topLeft.getLongitude() + ","
                + bottomRight.getLongitude();
    }

}

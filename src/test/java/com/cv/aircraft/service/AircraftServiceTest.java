package com.cv.aircraft.service;

import com.cv.aircraft.dto.Zone;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItems;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

@RunWith(SpringRunner.class)
public class AircraftServiceTest {

    @InjectMocks
    private AircraftService aircraftService;
    @Mock
    private RestTemplate restTemplate;

    @Test
    public void shouldParseAircrafts() throws Exception {
        //GIVEN
        String responseBody = "{\"full_count\":14066,\"version\":4,\"e2760b4\":[\"02A1AF\",50.2557,29.7976,49,34975,483,\"7353\",\"F-UKBB1\",\"A320\",\"TS-INP\",1500498166,\"MIR\",\"VKO\",\"BJ868\",0,0,\"LBT868\",0]\n" +
                ",\"e27759c\":[\"5083EF\",50.1453,30.1035,12,5675,255,\"6336\",\"F-UKKK8\",\"B735\",\"UR-CGY\",1500498167,\"AYT\",\"IEV\",\"\",0,-1216,\"BAY4306\",0]\n" +
                ",\"e2787d5\":[\"471F92\",50.4060,30.4538,312,0,34,\"6435\",\"F-UKKK8\",\"A320\",\"HA-LYW\",1500498167,\"HAJ\",\"IEV\",\"W66108\",1,0,\"WZZ6108\",0]\n" +
                ",\"e277c6e\":[\"50824A\",50.2164,30.8947,3,2325,158,\"6343\",\"T-UKKK4\",\"A320\",\"UR-WRM\",1500498164,\"AYT\",\"KBP\",\"7W7012\",0,-832,\"WRC7012\",0]\n" +
                ",\"e27a778\":[\"504E2E\",50.6169,31.1706,207,38000,423,\"4530\",\"F-UKKK2\",\"A319\",\"ER-AXL\",1500498167,\"DME\",\"KIV\",\"9U176\",0,0,\"MLD176\",0]\n" +
                "}";
        ResponseEntity<String> responseEntity = new ResponseEntity(responseBody, OK);
        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(String.class))).thenReturn(responseEntity);
        //WHEN
        Zone zone = createZone();
        List<String> aircrafts = aircraftService.getAircraftIdsInZone(zone);
        //THEN
        assertThat(aircrafts, containsInAnyOrder("e2760b4","e27759c", "e2787d5", "e277c6e", "e27a778"));
    }

    private Zone createZone() {
        Zone.TopLeft topLeft = new Zone.TopLeft(10.10, 10.10);
        Zone.BottomRight bottomRight = new Zone.BottomRight(10.10, 10.10);
        return new Zone(topLeft, bottomRight);
    }

}
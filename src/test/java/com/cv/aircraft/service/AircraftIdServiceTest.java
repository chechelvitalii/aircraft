package com.cv.aircraft.service;

import com.cv.aircraft.IOTestUtils;
import com.cv.aircraft.dto.AirplaneShortInfo;
import com.cv.aircraft.dto.TargetArea;

import com.cv.aircraft.service.AircraftIdService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

@RunWith(SpringRunner.class)
public class AircraftIdServiceTest {

    @InjectMocks
    private AircraftIdService aircraftIdService;
    @Mock
    private RestTemplate restTemplate;

    @Before
    public void setUp() throws Exception { //TODO fix this hardcode
        ReflectionTestUtils.setField(aircraftIdService, "aircraftIdsUrl", "http://test=%s");
    }

    @Test
    public void shouldParseAircrafts() throws Exception {
        //GIVEN
        String responseBody = IOTestUtils.readFileAsString("/aircraftIds.json");
        ResponseEntity<String> responseEntity = new ResponseEntity(responseBody, OK);
        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(String.class))).thenReturn(responseEntity);
        //WHEN
        TargetArea targetArea = createTargetArea();
        Set<AirplaneShortInfo> airplaneShortInfos = aircraftIdService.getAirplaneShortInfoInZone(targetArea);
        //THEN
        AirplaneShortInfo airplaneShortInfo1 = new AirplaneShortInfo("e2760b4", "MIR", "VKO");
        AirplaneShortInfo airplaneShortInfo2 = new AirplaneShortInfo("e27759c", "AYT", "IEV");
        AirplaneShortInfo airplaneShortInfo3 = new AirplaneShortInfo("e2787d5", "HAJ", "IEV");
        AirplaneShortInfo airplaneShortInfo4 = new AirplaneShortInfo("e277c6e", "AYT", "KBP");
        AirplaneShortInfo airplaneShortInfo5 = new AirplaneShortInfo("e27a778", "DME", "KIV");
        assertThat(airplaneShortInfos, containsInAnyOrder(airplaneShortInfo1, airplaneShortInfo2, airplaneShortInfo3, airplaneShortInfo4, airplaneShortInfo5));
    }

    private TargetArea createTargetArea() {
        TargetArea.Point northWest = new TargetArea.Point(10.10, 10.10);
        TargetArea.Point southEast = new TargetArea.Point(10.10, 10.10);
        return new TargetArea(northWest, southEast);
    }

}
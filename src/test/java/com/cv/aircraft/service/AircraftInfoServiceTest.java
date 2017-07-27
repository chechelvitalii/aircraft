package com.cv.aircraft.service;

import com.cv.aircraft.IOTestUtils;
import com.cv.aircraft.dto.AircraftInfo;
import com.fasterxml.jackson.contrib.jsonpath.DefaultJsonUnmarshaller;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

@RunWith(SpringRunner.class)
public class AircraftInfoServiceTest {

    @InjectMocks
    private AircraftInfoIdService aircraftInfoService;
    @Mock
    private RestTemplate restTemplate;

    @Before
    public void setUp() throws Exception {
        aircraftInfoService = new AircraftInfoIdService(restTemplate, new DefaultJsonUnmarshaller());
        ReflectionTestUtils.setField(aircraftInfoService, "aircraftInfoUrl", "http://test=%s");

    }

    @Test
    public void getAircraftInfos() {
        //GIVEN
        Set<String> aircraftIds = new HashSet<>(asList("id1", "id2"));
        String responseBody = IOTestUtils.readFileAsString("/aircraftInfo.json");
        ResponseEntity<String> responseEntity = new ResponseEntity(responseBody, OK);
        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(String.class))).thenReturn(responseEntity);
        //WHEN
        List<AircraftInfo> aircraftInfos = aircraftInfoService.getAircraftInfos(aircraftIds);

        //THEN
        AircraftInfo aircraftInfo1 = aircraftInfos.get(0);
        assertThat(aircraftInfo1.getAirline(), is("Windrose Airlines"));
        assertThat(aircraftInfo1.getModel(), is("Embraer ERJ-145LR"));
        assertThat(aircraftInfo1.getDepartureCountry(), is("Bulgaria"));
        assertThat(aircraftInfo1.getDepartureCity(), is("Sofia"));
        assertThat(aircraftInfo1.getArrivalCountry(), is("Ukraine"));
        assertThat(aircraftInfo1.getArrivalCity(), is("Kiev"));
        assertThat(aircraftInfo1.getSpeed(), is(180));
        assertThat(aircraftInfo1.getHight(), is(3816));
//        assertThat(aircraftInfo1.,is());
//        assertThat(aircraftInfo1.,is());
    }

    @Test
    public void shouldConvertJsonToAircraftInfo() throws Exception {
        //GIVEN
        String json = IOTestUtils.readFileAsString("/aircraftInfo.json");
        //WHEN
        AircraftInfoIdService aircraftInfoService = new AircraftInfoIdService(new RestTemplate(),new DefaultJsonUnmarshaller());
        AircraftInfo aircraftInfo = aircraftInfoService.convertToAircraftInfo(json);
        //THEN
        assertNotNull(aircraftInfo);
    }
}
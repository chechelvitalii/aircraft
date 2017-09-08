package com.cv.aircraft.service.aircraft;

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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

@RunWith(SpringRunner.class)
public class AircraftInfoServiceTest {

    @InjectMocks
    private AircraftInfoService aircraftInfoService;
    @Mock
    private RestTemplate restTemplate;

    @Before
    public void setUp() throws Exception {
        aircraftInfoService = new AircraftInfoService(restTemplate, new DefaultJsonUnmarshaller());
        ReflectionTestUtils.setField(aircraftInfoService, "aircraftInfoUrl", "http://test=%s");

    }

    @Test
    public void getAircraftInfos() {
        //GIVEN
        String aircraftId = "id1";
        String responseBody = IOTestUtils.readFileAsString("/aircraftInfo.json");
        ResponseEntity<String> responseEntity = new ResponseEntity(responseBody, OK);
        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(String.class))).thenReturn(responseEntity);
        //WHEN
        AircraftInfo aircraftInfo = aircraftInfoService.getAircraftInfos(aircraftId);

        //THEN
        assertThat(aircraftInfo.getAirline(), is("Windrose Airlines"));
        assertThat(aircraftInfo.getModel(), is("Embraer ERJ-145LR"));
        assertThat(aircraftInfo.getDepartureCountry(), is("Bulgaria"));
        assertThat(aircraftInfo.getDepartureCity(), is("Sofia"));
        assertThat(aircraftInfo.getArrivalCountry(), is("Ukraine"));
        assertThat(aircraftInfo.getArrivalCity(), is("Kiev"));
        assertThat(aircraftInfo.getSpeedInKmH(), is(333));
        assertThat(aircraftInfo.getHeightInMeters(), is(1163));
        assertThat(aircraftInfo.getImg(),is("https://cdn.jetphotos.com/400/6/26071_1499321981.jpg?v=0"));
    }

    @Test
    public void shouldConvertJsonToAircraftInfo() throws Exception {
        //GIVEN
        String json = IOTestUtils.readFileAsString("/aircraftInfo.json");
        //WHEN
        AircraftInfoService aircraftInfoService = new AircraftInfoService(new RestTemplate(),new DefaultJsonUnmarshaller());
        AircraftInfo aircraftInfo = aircraftInfoService.convertToAircraftInfo(json);
        //THEN
        assertNotNull(aircraftInfo);
    }
}
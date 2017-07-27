package com.cv.aircraft.service;

import com.cv.aircraft.dto.Zone;

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

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

import lombok.SneakyThrows;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
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

    @Before
    public void setUp() throws Exception {
        ReflectionTestUtils.setField(aircraftService,"aircraftIdsUrl","http://test=%s,%s,%s,%s");
    }

    @Test
    public void shouldParseAircrafts() throws Exception {
        //GIVEN
        String responseBody = readFileAsString(getClass(),"/aircraftIds.json");
        ResponseEntity<String> responseEntity = new ResponseEntity(responseBody, OK);
        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(String.class))).thenReturn(responseEntity);
        //WHEN
        Zone zone = createZone();
        Set<String> aircrafts = aircraftService.getAircraftIdsInZone(zone);
        //THEN
        assertThat(aircrafts, containsInAnyOrder("e2760b4", "e27759c", "e2787d5", "e277c6e", "e27a778"));
    }

    private Zone createZone() {
        Zone.TopLeft topLeft = new Zone.TopLeft(10.10, 10.10);
        Zone.BottomRight bottomRight = new Zone.BottomRight(10.10, 10.10);
        return new Zone(topLeft, bottomRight);
    }

    @SneakyThrows
    public static String readFileAsString(Class clazz,String filePath) {
        return new String(
                Files.readAllBytes(
                        Paths.get(clazz.getResource(filePath).toURI())));
    }

}
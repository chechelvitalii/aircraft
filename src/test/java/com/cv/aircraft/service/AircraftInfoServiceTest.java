package com.cv.aircraft.service;

import com.cv.aircraft.dto.AircraftInfo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
public class AircraftInfoServiceTest {

    @InjectMocks
    private AircraftInfoService aircraftInfoService;
    @Mock
    private RestTemplate restTemplate;

    @Test
    public void getAircraftInfos() {
        //GIVEN
        List<String> aircraftIds = asList("id1", "id2");
        //WHEN
        List<AircraftInfo> aircraftInfos = aircraftInfoService.getAircraftInfos(aircraftIds);
        //THEN
        AircraftInfo aircraftInfo1 = aircraftInfos.get(0);
        assertThat(aircraftInfo1.getAirline(),is("Windrose Airlines"));
        assertThat(aircraftInfo1.getModel(),is("Embraer ERJ-145LR"));
        assertThat(aircraftInfo1.getDepartureCountry(),is("Bulgaria"));
        assertThat(aircraftInfo1.getDepartureCity(),is("Sofia"));
        assertThat(aircraftInfo1.getArrivalCountry(),is("Ukraine"));
        assertThat(aircraftInfo1.getArrivalCity(),is("Kiev"));
        assertThat(aircraftInfo1.getSpeed(),is(180));
        assertThat(aircraftInfo1.getHight(),is(3816));
//        assertThat(aircraftInfo1.,is());
//        assertThat(aircraftInfo1.,is());
    }

}
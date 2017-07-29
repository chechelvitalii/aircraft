package com.cv.aircraft.service.aircraft;

import com.cv.aircraft.dto.AircraftInfo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AircraftInfoServiceIT {
    @Autowired
    private AircraftInfoService aircraftInfoService;

    @Test
    public void shouldGetAircraftInfos() throws Exception {
        //GIVEN
        Set<String> aircraftIds = new HashSet<>(asList("e40e3f0"));
        //WHEN
        List<AircraftInfo> aircraftInfos = aircraftInfoService.getAircraftInfos(aircraftIds);
        //THEN
        assertNotNull(aircraftInfos);
    }

}
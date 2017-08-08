package com.cv.aircraft.service.aircraft;

import com.cv.aircraft.dto.AircraftInfo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AircraftInfoServiceIT {
    @Autowired
    private AircraftInfoService aircraftInfoService;

    @Test
    public void shouldGetAircraftInfos() throws Exception {
        //GIVEN
        String aircraftId = "e40e3f0";
        //WHEN
        AircraftInfo aircraftInfo = aircraftInfoService.getAircraftInfos(aircraftId);
        //THEN
        assertNotNull(aircraftInfo);
    }

}
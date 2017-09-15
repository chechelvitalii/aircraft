package com.cv.aircraft.service;

import com.cv.aircraft.dto.AirplaneShortInfo;
import com.cv.aircraft.dto.TargetArea;

import com.cv.aircraft.service.AircraftIdService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.junit.Assert.assertNotNull;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AircraftIdServiceIT {
    @Autowired
    private AircraftIdService aircraftIdService;

    @Test
    public void shouldGetAircraftsInZone() {
        //GIVEN
        TargetArea.Point northWest = new TargetArea.Point(50.71, 29.52);
        TargetArea.Point southEast = new TargetArea.Point(50.14, 31.66);
        TargetArea targetArea = new TargetArea(northWest, southEast);
        //WHEN
        Set<AirplaneShortInfo> aircraftIdsInZone = aircraftIdService.getAirplaneShortInfoInZone(targetArea);
        //THEN
        assertNotNull(aircraftIdsInZone);
    }
}
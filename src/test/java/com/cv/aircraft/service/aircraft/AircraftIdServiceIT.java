package com.cv.aircraft.service.aircraft;

import com.cv.aircraft.dto.AirplaneShortInfo;
import com.cv.aircraft.dto.Zone;

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
        Zone.TopLeft topLeft = new Zone.TopLeft(50.71, 29.52);
        Zone.BottomRight bottomRight = new Zone.BottomRight(50.14, 31.66);
        Zone zone = new Zone(topLeft, bottomRight);
        //WHEN
        Set<AirplaneShortInfo> aircraftIdsInZone = aircraftIdService.getAirplaneShortInfoInZone(zone);
        //THEN
        assertNotNull(aircraftIdsInZone);
    }
}
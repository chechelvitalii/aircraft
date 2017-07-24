package com.cv.aircraft.service;

import com.cv.aircraft.dto.Zone;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AircraftServiceIT {
    @Autowired
    private AircraftService aircraftService;

    @Test
    public void shouldGetAircraftsInZone() {
        //GIVEN
        Zone.TopLeft topLeft = new Zone.TopLeft(50.71, 29.52);
        Zone.BottomRight bottomRight = new Zone.BottomRight(50.14, 31.66);
        Zone zone = new Zone(topLeft, bottomRight);
        //WHEN
        aircraftService.getOnlineAircraftIdsInZone(zone);
        //THEN
        //TODO mock restTemplate ?

    }

}
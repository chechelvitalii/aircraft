package com.cv.aircraft.service;

import com.cv.aircraft.dto.Zone;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AircraftServiceTest {
    @Autowired
    private AircraftService aircraftService;

    @Test
    public void shouldGetAircraftsInZone() {
        //GIVEN
        Zone.TopLeft topLeft = new Zone.TopLeft(50.52407003079133, 30.708689361810684);
        Zone.BottomRight bottomRight = new Zone.BottomRight(50.163643592255546, 31.068612426519394);
        Zone zone = new Zone(topLeft, bottomRight);
        //WHEN
        aircraftService.getAircraftsIn(zone);
        //THEN
        //TODO mock restTemplate ?
    }

}
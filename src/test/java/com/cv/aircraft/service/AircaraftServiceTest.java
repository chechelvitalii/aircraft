package com.cv.aircraft.service;

import com.cv.aircraft.dto.Zone;

import org.junit.Test;

public class AircaraftServiceTest {

    private AircaraftService aircaraftService = new AircaraftService();
    @Test
    public void shouldGetAircraftsInZone() {
        //GIVEN
        Zone.TopLeft topLeft = new Zone.TopLeft(50.52407003079133, 30.708689361810684);
        Zone.BottomRight bottomRight = new Zone.BottomRight(50.163643592255546, 31.068612426519394);
        Zone zone = new Zone(topLeft, bottomRight);
        //WHEN
        aircaraftService.getAircraftsIn(zone);
        //THEN
    }

}
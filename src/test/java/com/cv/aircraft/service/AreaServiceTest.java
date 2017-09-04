package com.cv.aircraft.service;

import com.cv.aircraft.dto.TargetArea;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class AreaServiceTest {

    private AreaService areaService = new AreaService();

    @Test
    public void shouldDeterminateAreaInIstanbul() throws Exception {
        //GIVEN
        float latitude = Float.valueOf("40.97");
        float longitude = Float.valueOf("28.81");

        //WHEN
        TargetArea computeTargetArea = areaService.determinateTargetArea(latitude, longitude);
        //THEN
        TargetArea.Point northWest = computeTargetArea.getNorthWest();
        assertThat(northWest.getLatitude(),is(Double.valueOf("41.15")));
        assertThat(northWest.getLongitude(),is(Double.valueOf("28.63")));
        TargetArea.Point southEast = computeTargetArea.getSouthEast();
        assertThat(southEast.getLatitude(),is(Double.valueOf("40.79")));
        assertThat(southEast.getLongitude(),is(Double.valueOf("28.99")));
    }

    @Test
    public void shouldDeterminateAreaInDubai() throws Exception {
        //GIVEN
        float latitude = Float.valueOf("25.25");
        float longitude = Float.valueOf("55.36");

        //WHEN
        TargetArea computeTargetArea = areaService.determinateTargetArea(latitude, longitude);
        //THEN
        TargetArea.Point northWest = computeTargetArea.getNorthWest();
        assertThat(northWest.getLatitude(),is(Double.valueOf("25.43")));
        assertThat(northWest.getLongitude(),is(Double.valueOf("55.18")));
        TargetArea.Point southEast = computeTargetArea.getSouthEast();
        assertThat(southEast.getLatitude(),is(Double.valueOf("25.07")));
        assertThat(southEast.getLongitude(),is(Double.valueOf("55.54")));
    }
}
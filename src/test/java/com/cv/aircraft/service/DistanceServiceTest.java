package com.cv.aircraft.service;

import com.cv.aircraft.dto.TargetArea;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DistanceServiceTest {

    private DistanceService distanceService = new DistanceService();

    @Test
    public void shouldComputeAllowedZone() throws Exception {
        //GIVEN
        float latitude = Float.valueOf("50.34385826");
        float longitude = Float.valueOf("30.88865161");
        //WHEN
        TargetArea computeTargetArea = distanceService.createTargetArea(latitude, longitude);
        //THEN
        TargetArea.Point northWest = computeTargetArea.getNorthWest();
        assertThat(northWest.getLatitude(),is(Double.valueOf("50.52")));
        assertThat(northWest.getLongitude(),is(Double.valueOf("30.71")));
        TargetArea.Point southEast = computeTargetArea.getSouthEast();
        assertThat(southEast.getLatitude(),is(Double.valueOf("50.16")));
        assertThat(southEast.getLongitude(),is(Double.valueOf("31.07")));
    }

}
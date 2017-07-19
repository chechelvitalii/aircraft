package com.cv.aircraft.service;

import com.cv.aircraft.dto.Zone;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ComputationServiceTest {

    private ComputationService computationService = new ComputationService();

    @Test
    public void shouldComputeAllowedZone() throws Exception {
        //GIVEN
        float latitude = Float.valueOf("50.34385826");
        float longitude = Float.valueOf("30.88865161");
        //WHEN
        Zone computeZone = computationService.computeZone(latitude, longitude);
        //THEN
        Zone.TopLeft topLeft = computeZone.getTopLeft();
        assertThat(topLeft.getLatitude(),is(Double.valueOf("50.52")));
        assertThat(topLeft.getLongitude(),is(Double.valueOf("30.71")));
        Zone.BottomRight bottomRight = computeZone.getBottomRight();
        assertThat(bottomRight.getLatitude(),is(Double.valueOf("50.16")));
        assertThat(bottomRight.getLongitude(),is(Double.valueOf("31.07")));
    }

}
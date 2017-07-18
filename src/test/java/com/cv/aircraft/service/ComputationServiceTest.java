package com.cv.aircraft.service;

import com.cv.aircraft.dto.Zone;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.*;

public class ComputationServiceTest {

    private ComputationService computationService = new ComputationService();

    @Test
    public void shouldComputeAllowedZone() throws Exception {
        //GIVEN
        float latitude = Float.valueOf("50.338819");
        float longitude = Float.valueOf("30.891283");
        //WHEN
        Zone computeZone = computationService.computeZone(latitude, longitude);
        //THEN
        Zone.TopLeft topLeft = computeZone.getTopLeft();
        assertThat(topLeft.getLatitude(),is(Double.valueOf("52.656969226829084")));
        assertThat(topLeft.getLongitude(),is(Double.valueOf("30.631437301635742")));
        Zone.BottomRight bottomRight = computeZone.getBottomRight();
        assertThat(bottomRight.getLatitude(),is(Double.valueOf("48.30904944992873")));
        assertThat(bottomRight.getLongitude(),is(Double.valueOf("30.271512985229492")));
    }

}
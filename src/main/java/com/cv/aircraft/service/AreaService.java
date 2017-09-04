package com.cv.aircraft.service;

import com.cv.aircraft.dto.TargetArea;
import org.springframework.stereotype.Component;

@Component
public class AreaService {

    private static final int DEFAULT_AREA = 20;
    private static final float LONGITUDE_KHM_PER_DEGREE = Float.parseFloat("40008.55") / 360;
    private static final float EQUATOR_LATITUDE_KHM_PER_DEGREE = Float.parseFloat("40075.696") / 360;

    public TargetArea determinateTargetArea(Float latitude, Float longitude) {
        double absLatitudeForDefaultArea = getAbsLatitudeForDefaultArea(latitude);
        double longitudeForDefaultArea = getLongitudeForDefaultArea();

        double north = latitude + absLatitudeForDefaultArea;
        double west = longitude - longitudeForDefaultArea;
        TargetArea.Point northWest = new TargetArea.Point(north, west);

        double south = latitude - absLatitudeForDefaultArea;
        double east = longitude + longitudeForDefaultArea;
        TargetArea.Point southEast = new TargetArea.Point(south, east);

        return new TargetArea(northWest, southEast);
    }

    private double getAbsLatitudeForDefaultArea(Float latitude) {
        double latitudeKhmPerDegree = Math.cos(latitude) * EQUATOR_LATITUDE_KHM_PER_DEGREE;
        return Math.abs(DEFAULT_AREA / latitudeKhmPerDegree);
    }

    private double getLongitudeForDefaultArea() {
        return DEFAULT_AREA / LONGITUDE_KHM_PER_DEGREE;
    }
}

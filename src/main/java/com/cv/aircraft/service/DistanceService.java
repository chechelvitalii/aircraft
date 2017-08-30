package com.cv.aircraft.service;

import com.cv.aircraft.dto.TargetArea;
import org.springframework.stereotype.Component;

@Component
public class DistanceService {

    private static final int DEFAULT_ZONE = 20;
    private static final float LONGITUDE_KHM_PER_DEGREE = Float.parseFloat("40008.55") / 360;
    private static final float EQUATOR_LATITUDE_KHM_PER_DEGREE = Float.parseFloat("40075.696") / 360;

    public TargetArea createTargetArea(Float latitude, Float longitude) {
        double north = latitude + getDefaultZoneLatitude(latitude);
        double west = longitude - getDefaultZoneLongitude();
        TargetArea.Point northWest = new TargetArea.Point(north, west);

        double south = latitude - getDefaultZoneLatitude(latitude);
        double east = longitude + getDefaultZoneLongitude();
        TargetArea.Point southEast = new TargetArea.Point(south, east);

        return new TargetArea(northWest, southEast);
    }

    private double getDefaultZoneLatitude(Float latitude) {
        double latitudeKhmPerDegree = Math.cos(latitude) * EQUATOR_LATITUDE_KHM_PER_DEGREE;
        return DEFAULT_ZONE / latitudeKhmPerDegree;
    }

    private double getDefaultZoneLongitude() {
        return DEFAULT_ZONE / LONGITUDE_KHM_PER_DEGREE;
    }
}

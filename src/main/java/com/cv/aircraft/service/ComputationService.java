package com.cv.aircraft.service;

import com.cv.aircraft.dto.Zone;

public class ComputationService {

    private static final int DEFAULT_ZONE = 20;
    private static final float LONGITUDE_KHM_PER_DEGREE = Float.parseFloat("40008.55") / 360;
    private static final float EQUATOR_LATITUDE_KHM_PER_DEGREE = Float.parseFloat("40075.696") / 360;

    public Zone computeZone(Float latitude, Float longitude) {
        double topLatitude = latitude + getDefaultZoneLatitude(latitude);
        double topLongitude = longitude - getDefaultZoneLongitude();
        Zone.TopLeft topLeft = new Zone.TopLeft(topLatitude, topLongitude);
        double bottomLatitude = latitude - getDefaultZoneLatitude(latitude);
        double bottomLongitude = longitude + getDefaultZoneLongitude();
        Zone.BottomRight bottomRight = new Zone.BottomRight(bottomLatitude, bottomLongitude);

        return new Zone(topLeft, bottomRight);
    }

    private double getDefaultZoneLatitude(Float latitude) {
        double latitudeKhmPerDegree = Math.cos(latitude) * EQUATOR_LATITUDE_KHM_PER_DEGREE;
        return DEFAULT_ZONE / latitudeKhmPerDegree;
    }

    private double getDefaultZoneLongitude() {
        return DEFAULT_ZONE / LONGITUDE_KHM_PER_DEGREE;
    }
}

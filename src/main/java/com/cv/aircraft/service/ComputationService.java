package com.cv.aircraft.service;

import com.cv.aircraft.dto.Zone;

public class ComputationService {

    private static final int DEFAULT_AREA = 20;
    private static final float LONGITUDE_PER_METER = 1 / (Float.parseFloat("40008.55") / 360);
    private static final float EQUATOR_LATITUDE_PER_METER = (Float.parseFloat("40075.696") / 360) / 1000;

    public Zone computeZone(Float latitude, Float longitude) {
        double latitudePerMeter = Math.cos(latitude) * EQUATOR_LATITUDE_PER_METER;

        double topLatitude = latitude + (latitudePerMeter * DEFAULT_AREA);
        double topLongitude = longitude + (LONGITUDE_PER_METER * DEFAULT_AREA);
        Zone.TopLeft topLeft = new Zone.TopLeft(topLatitude, topLongitude);
        double bottomLatitude = latitude - (latitudePerMeter * DEFAULT_AREA);
        double bottomLongitude = longitude - (LONGITUDE_PER_METER * DEFAULT_AREA);
        Zone.BottomRight bottomRight = new Zone.BottomRight(bottomLatitude, bottomLongitude);

        return new Zone(topLeft, bottomRight);
    }
}

package com.cv.aircraft.dto;


import lombok.Value;
import org.apache.commons.math3.util.Precision;

import static java.lang.String.format;

@Value
public class TargetArea {
    private Point northWest;
    private Point southEast;

    @Value
    public static class Point {
        private double latitude;
        private double longitude;

        public Point(double latitude, double longitude) {
            this.latitude = round(latitude);
            this.longitude = round(longitude);
        }
    }

    public String targetAreaToString() {
        return format("%s,%s,%s,%s", northWest.latitude, southEast.latitude, northWest.longitude, southEast.longitude);
    }

    private static double round(double value) {
        return Precision.round(value, 2);
    }
}

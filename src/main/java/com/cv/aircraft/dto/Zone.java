package com.cv.aircraft.dto;


import lombok.Value;
import org.apache.commons.math3.util.Precision;

@Value
public class Zone {
    private TopLeft topLeft;
    private BottomRight bottomRight;

    @Value
    public static class TopLeft {
        private double latitude;
        private double longitude;

        public TopLeft(double latitude, double longitude) {
            this.latitude = round(latitude);
            this.longitude = round(longitude);
        }
    }

    @Value
    public static class BottomRight {
        private double latitude;
        private double longitude;

        public BottomRight(double latitude, double longitude) {
            this.latitude = round(latitude);
            this.longitude = round(longitude);
        }
    }

    private static double round(double value) {
        return Precision.round(value, 2);
    }
}

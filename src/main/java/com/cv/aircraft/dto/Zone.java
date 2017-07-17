package com.cv.aircraft.dto;


import lombok.Value;

@Value
public class Zone {
    private TopLeft topLeft;
    private BottomRight bottomRight;

    @Value
    public static class TopLeft {
        private double latitude;
        private double longitude;
    }

    @Value
    public static class BottomRight {
        private double latitude;
        private double longitude;
    }
}

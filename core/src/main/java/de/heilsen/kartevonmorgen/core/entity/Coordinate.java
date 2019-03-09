package de.heilsen.kartevonmorgen.core.entity;

import lombok.Value;

@Value
public class Coordinate {
    double latitude;
    double longitude;

    public Coordinate(double latitude, double longitude) {
        this.latitude = validLatitude(latitude);
        this.longitude = validLongitude(longitude);
    }

    private double validLatitude(double latitude) {
        return Math.max(-90.0D, Math.min(90.0D, latitude));
    }

    private double validLongitude(double value) {
        if (-180.0D <= value && value < 180.0D) {
            return value;
        } else {
            return  ((value - 180.0D) % 360.0D + 360.0D) % 360.0D - 180.0D;
        }
    }
}

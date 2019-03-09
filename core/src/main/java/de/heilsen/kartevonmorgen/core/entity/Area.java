package de.heilsen.kartevonmorgen.core.entity;

import lombok.Value;

@Value
public class Area {
    Coordinate southWest;
    Coordinate northEast;

    public static Area rect(double minLat, double minLong, double maxLat, double maxLong) {
        return new Area(
                new Coordinate(minLat, minLong),
                new Coordinate(maxLat, maxLong));
    }
}

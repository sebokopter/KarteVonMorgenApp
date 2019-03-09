package de.heilsen.kartevonmorgen.api.entity;

import de.heilsen.kartevonmorgen.core.entity.Area;
import de.heilsen.kartevonmorgen.core.entity.Coordinate;
import lombok.Value;

@Value
public class Bbox {
    double minLat;
    double minLng;
    double maxLat;
    double maxLng;

    public static Bbox of(Area area) {
        Coordinate southWest = area.getSouthWest();
        Coordinate northEast = area.getNorthEast();
        double minLat = southWest.getLatitude();
        double minLng = southWest.getLongitude();
        double maxLat = northEast.getLatitude();
        double maxLng = northEast.getLongitude();
        return new Bbox(minLat, minLng, maxLat, maxLng);
    }
}

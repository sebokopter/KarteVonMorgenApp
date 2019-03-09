package de.heilsen.kartevonmorgen.app.poi.map.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.VisibleRegion;

import de.heilsen.kartevonmorgen.core.entity.Area;
import de.heilsen.kartevonmorgen.core.entity.Coordinate;

public class VisibleRegionUtil {
    public static Area toArea(VisibleRegion visibleRegion) {
        LatLng farRight = visibleRegion.farRight;
        LatLng nearLeft = visibleRegion.nearLeft;
        Coordinate southWest = new Coordinate(nearLeft.latitude, nearLeft.longitude);
        Coordinate northEast = new Coordinate(farRight.latitude, farRight.longitude);
        return new Area(southWest, northEast);
    }
}

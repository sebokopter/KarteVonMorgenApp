package de.heilsen.kartevonmorgen.app.poi.map.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import de.heilsen.kartevonmorgen.core.entity.Poi;

public class MarkerOptionsUtil {
    public static MarkerOptions fromPoi(Poi poi) {
        return new MarkerOptions().
                position(new LatLng(poi.getCoordinate().getLatitude(), poi.getCoordinate()
                        .getLongitude())).
                title(poi.getTitle()).
                snippet(poi.getDescription());
    }
}

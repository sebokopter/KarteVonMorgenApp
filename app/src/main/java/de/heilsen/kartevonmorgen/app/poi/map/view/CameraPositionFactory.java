package de.heilsen.kartevonmorgen.app.poi.map.view;

import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public final class CameraPositionFactory {
    public static final CameraPosition MOEHRINGEN = new CameraPosition.Builder()
            .target(new LatLng(48.7244278, 9.14492801713147))
            .zoom(14)
            .build();
}
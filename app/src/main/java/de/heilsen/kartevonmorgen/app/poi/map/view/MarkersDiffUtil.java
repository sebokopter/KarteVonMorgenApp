package de.heilsen.kartevonmorgen.app.poi.map.view;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Iterator;
import java.util.List;

public class MarkersDiffUtil {

    //TODO: test to clear it all when markerOptions is null
    //TODO: replace sync by Position with sync by ID
    public static void sync(@NonNull List<Marker> markerList, @NonNull List<MarkerOptions> markerOptionsList,
                            @NonNull OnMarkerAddedCallback onMarkerAddedCallback) {
        removeMarker(markerList, markerOptionsList);
        addNewMarker(markerList, markerOptionsList, onMarkerAddedCallback);
    }

    @VisibleForTesting
    static void removeMarker(@NonNull List<Marker> markerList, @NonNull List<MarkerOptions> markerOptionsList) {
        //noinspection ForLoopReplaceableByForEach => List#remove() throws ConcurrentModificationException during iteration
        for (Iterator<Marker> it = markerList.iterator(); it.hasNext();) {
            Marker marker = it.next();
            if (!containsMarker(markerOptionsList, marker)) {
                marker.remove();
                it.remove();
            }
        }
    }

    @VisibleForTesting
    static void addNewMarker(@NonNull List<Marker> markerList, @NonNull List<MarkerOptions> markerOptionsList,
                             @NonNull OnMarkerAddedCallback onMarkerAddedCallback) {
        for (MarkerOptions markerOptions : markerOptionsList) {
            if (!containsMarkerOptions(markerList, markerOptions)) {
                Marker marker = onMarkerAddedCallback.add(markerOptions);
                markerList.add(marker);
            }
        }
    }

    private static boolean containsMarker(List<MarkerOptions> markerOptionsList, Marker marker) {
        LatLng position = marker.getPosition();
        for (MarkerOptions markerOptions : markerOptionsList) {
            if (markerOptions.getPosition().equals(position)) {
                return true;
            }
        }
        return false;
    }

    private static boolean containsMarkerOptions(List<Marker> markerList, MarkerOptions markerOptions) {
        LatLng position = markerOptions.getPosition();
        for (Marker marker : markerList) {
            if (marker.getPosition().equals(position)) {
                return true;
            }
        }
        return false;
    }

    public interface OnMarkerAddedCallback {
        Marker add(MarkerOptions markerOptions);
    }
}

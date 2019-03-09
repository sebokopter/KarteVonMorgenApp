package de.heilsen.kartevonmorgen.app.poi.map.viewmodel.livedata;

import android.arch.lifecycle.LiveData;

import com.google.android.gms.maps.CameraUpdate;

import static com.google.android.gms.maps.CameraUpdateFactory.newCameraPosition;
import static de.heilsen.kartevonmorgen.app.poi.map.view.CameraPositionFactory.MOEHRINGEN;

public class CameraUpdateLiveData extends LiveData<CameraUpdate> {

    public CameraUpdateLiveData() {
        init();
    }

    private void init() {
        setValue(newCameraPosition(MOEHRINGEN));
    }
}

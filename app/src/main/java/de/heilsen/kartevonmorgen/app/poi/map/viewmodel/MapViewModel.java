package de.heilsen.kartevonmorgen.app.poi.map.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;

import java.util.List;

import de.heilsen.kartevonmorgen.app.poi.map.model.ErrorState;
import de.heilsen.kartevonmorgen.app.poi.map.model.repo.MarkerOptionsRxRepo;
import de.heilsen.kartevonmorgen.app.poi.map.viewmodel.livedata.CameraUpdateLiveData;
import de.heilsen.kartevonmorgen.app.poi.map.viewmodel.livedata.ErrorStateRxLiveData;
import de.heilsen.kartevonmorgen.app.poi.map.viewmodel.livedata.MarkerOptionsRxLiveData;
import de.heilsen.kartevonmorgen.app.poi.map.viewmodel.livedata.VisibleRegionLiveData;

//TODO: init markerOptionsRxRepo with non-empty visibleRegion
public class MapViewModel extends ViewModel {

    private VisibleRegionLiveData visibleRegion;
    private ErrorStateRxLiveData errorState;
    private CameraUpdateLiveData cameraUpdate;
    private MarkerOptionsRxLiveData markerOptions;

    public MapViewModel(MarkerOptionsRxRepo markerOptionsRxRepo) {
        cameraUpdate = new CameraUpdateLiveData();
        visibleRegion = new VisibleRegionLiveData();
        errorState = new ErrorStateRxLiveData(visibleRegion, markerOptionsRxRepo);
        markerOptions = new MarkerOptionsRxLiveData(visibleRegion, markerOptionsRxRepo);
    }

    public LiveData<List<MarkerOptions>> markerOptions() {
        return markerOptions;
    }

    //TODO: is cameraUpdate needed?
    public LiveData<CameraUpdate> cameraUpdate() {
        return cameraUpdate;
    }

    public MutableLiveData<VisibleRegion> visibleRegion() {
        return visibleRegion;
    }

    public LiveData<ErrorState> errorState() {
        return errorState;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        markerOptions.dispose();
        errorState.dispose();
    }
}
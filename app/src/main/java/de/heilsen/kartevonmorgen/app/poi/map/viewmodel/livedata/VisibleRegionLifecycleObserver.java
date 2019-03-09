package de.heilsen.kartevonmorgen.app.poi.map.viewmodel.livedata;

import android.support.annotation.Nullable;

import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;

import java.util.List;

import de.heilsen.kartevonmorgen.app.poi.map.model.repo.MarkerOptionsRxRepo;
import io.reactivex.Observer;

class VisibleRegionLifecycleObserver implements android.arch.lifecycle.Observer<VisibleRegion> {

    private MarkerOptionsRxRepo markerOptionsRxRepo;
    private Observer<List<MarkerOptions>> observer;

    public VisibleRegionLifecycleObserver(MarkerOptionsRxRepo markerOptionsRxRepo, Observer<List<MarkerOptions>> observer) {
        this.markerOptionsRxRepo = markerOptionsRxRepo;
        this.observer = observer;
    }

    @Override
    public void onChanged(@Nullable VisibleRegion visibleRegion) {
        markerOptionsRxRepo.markerOptions(visibleRegion).subscribe(observer);
    }
}

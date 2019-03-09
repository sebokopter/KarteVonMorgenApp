package de.heilsen.kartevonmorgen.app.poi.map.viewmodel.livedata;

import android.arch.lifecycle.LiveData;

import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Collections;
import java.util.List;

import de.heilsen.kartevonmorgen.app.poi.map.model.repo.MarkerOptionsRxRepo;
import io.reactivex.disposables.Disposable;

public class MarkerOptionsRxLiveData extends LiveData<List<MarkerOptions>> implements Disposable {

    private final VisibleRegionLifecycleObserver visibleRegionObserver;
    private final VisibleRegionLiveData visibleRegion;
    private boolean isDisposed;
    private Disposable subscription;

    public MarkerOptionsRxLiveData(VisibleRegionLiveData visibleRegion, MarkerOptionsRxRepo markerOptionsRxRepo) {
        this.visibleRegion = visibleRegion;
        this.visibleRegionObserver =
                new VisibleRegionLifecycleObserver(markerOptionsRxRepo, new MarkerOptionsObserver());
        this.visibleRegion.observeForever(visibleRegionObserver);
    }

    @Override
    public void dispose() {
        subscription.dispose();
        visibleRegion.removeObserver(visibleRegionObserver);
        isDisposed = true;
    }

    @Override
    public boolean isDisposed() {
        return isDisposed;
    }

    private class MarkerOptionsObserver implements io.reactivex.Observer<List<MarkerOptions>> {

        @Override
        public void onSubscribe(Disposable d) {
            subscription = d;
        }

        @Override
        public void onNext(List<MarkerOptions> markerOptions) {
            postValue(markerOptions);
        }

        @Override
        public void onError(Throwable e) {
            postValue(Collections.emptyList());
        }

        @Override
        public void onComplete() {

        }

    }
}

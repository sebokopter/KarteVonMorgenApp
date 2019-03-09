package de.heilsen.kartevonmorgen.app.poi.map.viewmodel.livedata;

import android.arch.lifecycle.MutableLiveData;

import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import de.heilsen.kartevonmorgen.app.poi.map.model.ErrorState;
import de.heilsen.kartevonmorgen.app.poi.map.model.repo.MarkerOptionsRxRepo;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static de.heilsen.kartevonmorgen.app.poi.map.model.ErrorState.NO_ERROR;


public class ErrorStateRxLiveData extends MutableLiveData<ErrorState> implements Disposable {
    private final VisibleRegionLiveData visibleRegion;
    private final VisibleRegionLifecycleObserver visibleRegionObserver;
    private boolean isDisposed;
    private Disposable subscription;

    public ErrorStateRxLiveData(VisibleRegionLiveData visibleRegion, MarkerOptionsRxRepo markerOptionsRxRepo) {
        this.visibleRegion = visibleRegion;
        this.visibleRegionObserver = new VisibleRegionLifecycleObserver(markerOptionsRxRepo, new ErrorStateObserver());
        this.visibleRegion.observeForever(visibleRegionObserver);
        this.setValue(NO_ERROR);
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

    private class ErrorStateObserver implements Observer<List<MarkerOptions>> {
        @Override
        public void onSubscribe(Disposable d) {
            subscription = d;
        }

        @Override
        public void onNext(List<MarkerOptions> markerOptions) {
            setValue(NO_ERROR);
        }

        @Override
        public void onError(Throwable e) {
            setValue(new ErrorState(e.getMessage()));
        }

        @Override
        public void onComplete() {

        }
    }
}

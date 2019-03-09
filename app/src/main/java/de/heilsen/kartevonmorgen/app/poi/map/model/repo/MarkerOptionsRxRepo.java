package de.heilsen.kartevonmorgen.app.poi.map.model.repo;

import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;

import java.util.List;

import de.heilsen.kartevonmorgen.app.poi.map.model.MarkerOptionsUtil;
import de.heilsen.kartevonmorgen.app.poi.map.model.VisibleRegionUtil;
import de.heilsen.kartevonmorgen.core.repository.PoiRepository;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static de.heilsen.kartevonmorgen.app.poi.map.model.VisibleRegionUtil.toArea;

public class MarkerOptionsRxRepo {
    private PoiRepository poiRepository;

    public MarkerOptionsRxRepo(PoiRepository poiRepository) {
        this.poiRepository = poiRepository;
    }

    public Observable<List<MarkerOptions>> markerOptions(VisibleRegion visibleRegion) {
        return poiRepository.getEntries(toArea(visibleRegion))
                .subscribeOn(Schedulers.io())
                .toFlowable(BackpressureStrategy.DROP)
                .observeOn(AndroidSchedulers.mainThread()) //TODO: Describe why running on main thread is necessary
                .map(MarkerOptionsUtil::fromPoi)
                .toList()
                .toObservable();
    }

    public Observable<List<MarkerOptions>> markerOptions(Flowable<VisibleRegion> visibleRegion) {
        return visibleRegion
                .subscribeOn(Schedulers.io())
                .onBackpressureLatest()
                .toObservable()
                .map(VisibleRegionUtil::toArea)
                .flatMap(poiRepository::getEntries)
                .observeOn(AndroidSchedulers.mainThread()) //TODO: Describe why running on main thread is necessary
                .map(MarkerOptionsUtil::fromPoi)
                .toList()
                .toObservable();
    }

}
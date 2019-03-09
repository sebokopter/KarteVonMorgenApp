package de.heilsen.kartevonmorgen.app.poi.map.model.repo;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.VisibleRegion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.heilsen.kartevonmorgen.core.entity.Poi;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;

import static de.heilsen.kartevonmorgen.app.poi.map.model.VisibleRegionUtil.toArea;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;

class MarkerOptionsRxRepoTest {

    private static final LatLng LAT_LNG = new LatLng(1, 2);
    private static final VisibleRegion VISIBLE_REGION =
            new VisibleRegion(LAT_LNG, LAT_LNG, LAT_LNG, LAT_LNG, new LatLngBounds(LAT_LNG, LAT_LNG));
    private MarkerOptionsRxRepo markerOptionsRxRepo;

    @BeforeEach
    void setUp() {
        markerOptionsRxRepo = new MarkerOptionsRxRepo(new TestPoiRepository());
    }

    @Test
    void returnMarkerOptions() {
        Observable<Poi> poiObservableOnIoScheduler =
                markerOptionsRxRepo.poiRepository.getEntries(toArea(VISIBLE_REGION))
                        .subscribeOn(Schedulers.io());
        TestObserver<Poi> testObserver = poiObservableOnIoScheduler.test();

        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();

        assertThat(testObserver.lastThread().getName(), startsWith("RxCachedThreadScheduler"));
    }
}
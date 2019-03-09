package de.heilsen.kartevonmorgen.app.poi.map.model.repo;

import de.heilsen.kartevonmorgen.core.entity.Area;
import de.heilsen.kartevonmorgen.core.entity.Poi;
import de.heilsen.kartevonmorgen.core.repository.PoiRepository;
import io.reactivex.Observable;

class TestPoiRepository implements PoiRepository {
    @Override
    public Observable<Poi> getEntries(Area area) {
        return Observable.just(Poi.builder().build());
    }

    @Override
    public Observable<Poi> getEntries(Area area, String searchTerm) {
        return Observable.just(Poi.builder().build());
    }
}

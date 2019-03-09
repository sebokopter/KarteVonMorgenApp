package de.heilsen.kartevonmorgen.core.repository;

import de.heilsen.kartevonmorgen.core.entity.Area;
import de.heilsen.kartevonmorgen.core.entity.Poi;
import io.reactivex.Observable;

public interface PoiRepository {
    Observable<Poi> getEntries(Area area);

    Observable<Poi> getEntries(Area area, String searchTerm);
}

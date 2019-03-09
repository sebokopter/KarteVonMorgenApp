package de.heilsen.kartevonmorgen.core.repository;

import de.heilsen.kartevonmorgen.core.entity.Area;
import io.reactivex.Single;

public interface AreaRepository {
    Single<Area> getCurrent();
    void setCurrent(Area area);
}

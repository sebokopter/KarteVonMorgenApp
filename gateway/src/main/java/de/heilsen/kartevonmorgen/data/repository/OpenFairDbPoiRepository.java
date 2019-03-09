package de.heilsen.kartevonmorgen.data.repository;

import java.util.List;

import javax.annotation.Nonnull;

import de.heilsen.kartevonmorgen.api.OpenFairDbApi;
import de.heilsen.kartevonmorgen.api.entity.Bbox;
import de.heilsen.kartevonmorgen.api.entity.Entry;
import de.heilsen.kartevonmorgen.api.entity.EntryLocation;
import de.heilsen.kartevonmorgen.api.entity.SearchResult;
import de.heilsen.kartevonmorgen.core.entity.Area;
import de.heilsen.kartevonmorgen.core.entity.Poi;
import de.heilsen.kartevonmorgen.core.repository.PoiRepository;
import de.heilsen.kartevonmorgen.data.retrofit.ResultUtils;
import io.reactivex.Observable;

import static de.heilsen.kartevonmorgen.data.rx.ObservableUtil.mapEntries;

public class OpenFairDbPoiRepository implements PoiRepository {

    private static final int GET_API_ENTRIES_MAX_QUERY_SIZE = 100;
    private OpenFairDbApi openFairDbApi;

    public OpenFairDbPoiRepository(OpenFairDbApi openFairDbApi) {
        this.openFairDbApi = openFairDbApi;
    }

    @SuppressWarnings("WeakerAccess")
    @Override
    public Observable<Poi> getEntries(Area area) {
        return getEntries(area, null);
    }

    @SuppressWarnings("WeakerAccess")
    @Override
    public Observable<Poi> getEntries(Area area, String searchTerm) {
        Observable<List<EntryLocation>> visibleEntries =
                ResultUtils.bodyOf(openFairDbApi.search(searchTerm, Bbox.of(area)))
                        .map(SearchResult::getVisible);

        Observable<String> visibleIds = visibleEntries
                .flatMap(list -> mapEntries(list, EntryLocation::getId));

        Observable<List<Entry>> allVisibleEntries = visibleIds
                .buffer(GET_API_ENTRIES_MAX_QUERY_SIZE)
                .flatMap(this::getApiEntries);

        return allVisibleEntries
                .flatMapIterable(list -> list)
                .map(Entry::toPoi);
    }


    private Observable<List<Entry>> getApiEntries(@Nonnull List<String> ids) {
        return ResultUtils.bodyOf(openFairDbApi.getEntries(ids));
    }

}

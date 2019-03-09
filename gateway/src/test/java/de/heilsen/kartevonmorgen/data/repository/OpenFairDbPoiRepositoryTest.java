package de.heilsen.kartevonmorgen.data.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.heilsen.kartevonmorgen.api.OpenFairDbApi;
import de.heilsen.kartevonmorgen.api.entity.ApiException;
import de.heilsen.kartevonmorgen.core.entity.Area;
import de.heilsen.kartevonmorgen.core.entity.Coordinate;
import de.heilsen.kartevonmorgen.core.entity.Poi;
import de.heilsen.kartevonmorgen.core.repository.PoiRepository;
import io.reactivex.Observable;

import static de.heilsen.kartevonmorgen.api.entity.Entry.toPoi;
import static de.heilsen.kartevonmorgen.data.repository.OpenFairDbApiFake.SOLAWIS;
import static de.heilsen.kartevonmorgen.data.repository.OpenFairDbApiFake.UNVERPACKT_KIEL;

class OpenFairDbPoiRepositoryTest {

    private static final ApiException API_EXCEPTION = new ApiException("", ApiException.Kind.HTTP, new Exception());

    private static OpenFairDbApi openFairDbApiWithReturnedItems;
    private static ThrowingOpenFairDbApiStub openFairDbApiWithException;

    @BeforeAll
    static void setUp() {
        openFairDbApiWithReturnedItems = new OpenFairDbApiFake();
        openFairDbApiWithException = new ThrowingOpenFairDbApiStub();
        openFairDbApiWithException.queueThrowable(API_EXCEPTION);
    }

    @Test
    void getEntries() {
        de.heilsen.kartevonmorgen.data.repository.OpenFairDbPoiRepository concatSearchAndEntryRepository =
                initGateway(openFairDbApiWithReturnedItems);

        Observable<Poi> entries = runGetEntries(concatSearchAndEntryRepository);

        entries.test().assertValues(toPoi(SOLAWIS), toPoi(UNVERPACKT_KIEL));
    }

    private OpenFairDbPoiRepository initGateway(OpenFairDbApi openFairDbApi) {
        return new OpenFairDbPoiRepository(openFairDbApi);
    }

    private Observable<Poi> runGetEntries(PoiRepository poiRepository) {
        return poiRepository.getEntries(
                new Area(new Coordinate(1, 2), new Coordinate(3, 4)));
    }

    @Test
    void getEntriesRethrowsRetrofitException() {
        PoiRepository openFairDbPoiRepository = initGateway(openFairDbApiWithException);

        Observable<Poi> entries = runGetEntries(openFairDbPoiRepository);

        entries.test().assertError(ApiException.class);
    }
}
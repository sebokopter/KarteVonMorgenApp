package de.heilsen.kartevonmorgen.api.retrofit.mockretrofit;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import de.heilsen.kartevonmorgen.api.OpenFairDbApi;
import de.heilsen.kartevonmorgen.api.entity.Bbox;
import de.heilsen.kartevonmorgen.api.entity.Category;
import de.heilsen.kartevonmorgen.api.entity.Entry;
import de.heilsen.kartevonmorgen.api.entity.EntryLocation;
import de.heilsen.kartevonmorgen.api.entity.SearchResult;
import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.adapter.rxjava2.Result;
import retrofit2.mock.BehaviorDelegate;

class MockOpenFairDbApi implements OpenFairDbApi {
    private static final EntryLocation VISIBLE_LOCATION =
            new EntryLocation("ff20f44776c0486682bc2e926d6d2bb3", 48.7244278, 9.14492801713147);
    static final List<EntryLocation> VISIBLE_LOCATIONS = Collections.singletonList(VISIBLE_LOCATION);
    private static final EntryLocation INVISIBLE_LOCATION =
            new EntryLocation("7efb0bd2773246b8a27d67bf258bc59a", 54.32077859967188, 10.125741362571716);
    static final List<EntryLocation> INVISIBLE_LOCATIONS = Collections.singletonList(INVISIBLE_LOCATION);
    private BehaviorDelegate<OpenFairDbApi> delegate;

    MockOpenFairDbApi(BehaviorDelegate<OpenFairDbApi> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Observable<Result<List<Category>>> getCategories() {
        return Observable.just(Result.response(Response.success(Arrays.asList(
                new Category("2cd00bebec0c48ba9db761da48678134", "Initiative", 1, 1448792571276L),
                new Category("c2dc278a2d6a4b9b8a50cb606fc017ed", "Event", 1, 1448792571276L),
                new Category("77b3c33a92554bcf8e8c2c86cedd6f6f", "Unternehmen", 1, 1448792571276L)
        ))));
    }

    @Override
    public Observable<Result<Category>> getCategory(long categoryId) {
        return null;
    }

    @Override
    public Observable<Result<List<String>>> getTags() {
        return null;
    }

    @Override
    public Observable<Result<SearchResult>> search(String text, Bbox bbox) {
        SearchResult searchResult = new SearchResult(VISIBLE_LOCATIONS, INVISIBLE_LOCATIONS);
        return Observable.just(Result.response(Response.success(searchResult)));
    }

    @Override
    public Observable<Result<List<Entry>>> getEntries(List<String> ids) {
        return null;
    }
}

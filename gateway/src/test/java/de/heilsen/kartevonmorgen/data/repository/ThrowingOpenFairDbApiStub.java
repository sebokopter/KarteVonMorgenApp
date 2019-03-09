package de.heilsen.kartevonmorgen.data.repository;

import java.util.List;

import de.heilsen.kartevonmorgen.api.OpenFairDbApi;
import de.heilsen.kartevonmorgen.api.entity.Bbox;
import de.heilsen.kartevonmorgen.api.entity.Category;
import de.heilsen.kartevonmorgen.api.entity.Entry;
import de.heilsen.kartevonmorgen.api.entity.SearchResult;
import io.reactivex.Observable;
import retrofit2.adapter.rxjava2.Result;

public class ThrowingOpenFairDbApiStub implements OpenFairDbApi {

    private Throwable throwable = new RuntimeException();

    public ThrowingOpenFairDbApiStub queueThrowable(Throwable throwable) {
        this.throwable = throwable;
        return this;
    }

    @Override
    public Observable<Result<List<Category>>> getCategories() {
        return Observable.error(throwable);
    }

    @Override
    public Observable<Result<SearchResult>> search(String text, Bbox bbox) {
        return Observable.error(throwable);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Observable<Result<List<Entry>>> getEntries(List<String> ids) {
        return Observable.error(throwable);
    }

    @Override
    public Observable<Result<Category>> getCategory(long categoryId) {
        return Observable.error(throwable);
    }

    @Override
    public Observable<Result<List<String>>> getTags() {
        return Observable.error(throwable);
    }

}

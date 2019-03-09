package de.heilsen.kartevonmorgen.api;

import java.util.List;

import de.heilsen.kartevonmorgen.api.entity.Bbox;
import de.heilsen.kartevonmorgen.api.entity.Category;
import de.heilsen.kartevonmorgen.api.entity.Entry;
import de.heilsen.kartevonmorgen.api.entity.SearchResult;
import io.reactivex.Observable;
import retrofit2.adapter.rxjava2.Result;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OpenFairDbApi {

    @GET("search")
    Observable<Result<SearchResult>> search(@Query(value = "text") String text,
                                        @Query(value = "bbox") Bbox bbox);

    @GET("entries/{entryIds}")
    Observable<Result<List<Entry>>> getEntries(@Path("entryIds") List<String> ids);

    @GET("categories")
    Observable<Result<List<Category>>> getCategories();

    @GET("categories/{categoryId}")
    Observable<Result<Category>> getCategory(@Path("categoryId") long categoryId);

    @GET("tags")
    Observable<Result<List<String>>> getTags();
}

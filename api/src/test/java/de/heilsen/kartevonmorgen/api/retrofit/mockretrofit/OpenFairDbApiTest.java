package de.heilsen.kartevonmorgen.api.retrofit.mockretrofit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.heilsen.kartevonmorgen.api.entity.Bbox;
import de.heilsen.kartevonmorgen.api.entity.SearchResult;
import de.heilsen.kartevonmorgen.api.OpenFairDbApi;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.Result;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;

import static de.heilsen.kartevonmorgen.api.retrofit.mockretrofit.MockOpenFairDbApi.INVISIBLE_LOCATIONS;
import static de.heilsen.kartevonmorgen.api.retrofit.mockretrofit.MockOpenFairDbApi.VISIBLE_LOCATIONS;

class OpenFairDbApiTest {

    private MockOpenFairDbApi mockOpenFairDbService;

    @BeforeEach
    void setUp() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://localhost:8080/").build();
        MockRetrofit mockRetrofit = new MockRetrofit.Builder(retrofit).build();
        BehaviorDelegate<OpenFairDbApi> delegate = mockRetrofit.create(OpenFairDbApi.class);
        mockOpenFairDbService = new MockOpenFairDbApi(delegate);
    }

    @Test
    void deserializeSearchResult() {
        Observable<Result<SearchResult>> searchResultObservable =
                mockOpenFairDbService.search("test", new Bbox(1, 2, 3, 4));

        TestObserver<SearchResult> resultTestObserver = searchResultObservable
                .map(searchResultResult -> {
                    boolean noError = !searchResultResult.isError();
                    Response<SearchResult> response = searchResultResult.response();
                    if (noError && response != null) {
                        return response.body();
                    }
                    return null;
                })
                .test();
        resultTestObserver.assertValue(new SearchResult(VISIBLE_LOCATIONS, INVISIBLE_LOCATIONS));
    }
}
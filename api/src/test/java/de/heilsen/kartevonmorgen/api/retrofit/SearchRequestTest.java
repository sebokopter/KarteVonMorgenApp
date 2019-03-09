package de.heilsen.kartevonmorgen.api.retrofit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.Collections;

import de.heilsen.kartevonmorgen.api.OpenFairDb;
import de.heilsen.kartevonmorgen.api.OpenFairDbApi;
import de.heilsen.kartevonmorgen.api.entity.Bbox;
import de.heilsen.kartevonmorgen.api.entity.EntryLocation;
import de.heilsen.kartevonmorgen.api.entity.SearchResult;
import de.heilsen.kartevonmorgen.api.retrofit.junitextensions.WireMockExtension;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import retrofit2.Response;
import retrofit2.adapter.rxjava2.Result;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;

class SearchRequestTest {
    private static final String COORDINATE_POINT = "\\d+(\\.\\d+)";
    private static final String ENCODED_COMMA = "%2C";

    @SuppressWarnings("WeakerAccess")
    @RegisterExtension
    static WireMockExtension wireMockExtension = new WireMockExtension();

    private static OpenFairDbApi openFairDbApi;
    private static String relativeBaseUrl;

    @BeforeAll
    static void setUp() {
        relativeBaseUrl = "/v1/";
        String baseUrl = wireMockExtension.url(relativeBaseUrl);
        setupSuccessfulSearchResponse();
        openFairDbApi = OpenFairDb.service(baseUrl);
    }

    private static void setupSuccessfulSearchResponse() {
        stubFor(get(urlMatching(
                relativeBaseUrl + "search\\?(text=.*&)?bbox=(" + COORDINATE_POINT + ENCODED_COMMA + "){3}"
                + COORDINATE_POINT))
                .willReturn(aResponse()
                        .withStatus(HttpURLConnection.HTTP_OK)
                        .withBodyFile("search-200.json")
                ));
    }

    @Test
    void validateSearchRequestAgainstRelativeBaseUrl() {
        Observable<Result<SearchResult>> searchResultObservable = openFairDbApi
                .search("searchTerm", new Bbox(1, 2, 3, 4));
        searchResultObservable.subscribe();

        verify(getRequestedFor(urlPathEqualTo(relativeBaseUrl + "search"))
                .withQueryParam("text", containing("searchTerm"))
                .withQueryParam("bbox", containing("1.0,2.0,3.0,4.0"))
        );
    }

    @Test
    void serializeSearchResponse() {
        Observable<Result<SearchResult>> searchResultObservable = openFairDbApi
                .search("searchTerm", new Bbox(1, 2, 3, 4));
        //TODO: don't repeat all those map calls
        TestObserver<SearchResult> testObserver = searchResultObservable
                .map(result -> {
                    boolean noError = !result.isError();
                    Response<SearchResult> response = result.response();
                    if (noError && response != null) {
                        return response.body();
                    }
                    return null;
                })
                .test();
        testObserver.assertValue(expectedSearchResult());
    }

    private SearchResult expectedSearchResult() {
        EntryLocation visible1 =
                new EntryLocation("ff20f44776c0486682bc2e926d6d2bb3", 48.7244278, 9.14492801713147);
        EntryLocation visible2 =
                new EntryLocation("cd1ac0d81679479fb85acdf59ce69a01", 48.1789742, 11.4842233);
        EntryLocation invisible =
                new EntryLocation("7efb0bd2773246b8a27d67bf258bc59a", 54.32077859967188, 10.125741362571716);
        return new SearchResult(Arrays.asList(visible1, visible2), Collections.singletonList(invisible));
    }
}

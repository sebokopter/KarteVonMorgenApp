package de.heilsen.kartevonmorgen.api.retrofit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.net.HttpURLConnection;

import de.heilsen.kartevonmorgen.api.OpenFairDb;
import de.heilsen.kartevonmorgen.api.OpenFairDbMonitorApi;
import de.heilsen.kartevonmorgen.api.retrofit.junitextensions.WireMockExtension;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import retrofit2.Response;
import retrofit2.adapter.rxjava2.Result;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.any;
import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;

class ServerVersionRequestTest {
    private static final String SERVER_VERSION = "1.2.3";
    @SuppressWarnings("WeakerAccess")
    @RegisterExtension
    static WireMockExtension wireMockExtension = new WireMockExtension();

    private static OpenFairDbMonitorApi openFairDbMonitorApi;

    @BeforeAll
    static void setUp() {
        openFairDbMonitorApi = OpenFairDb.monitor(wireMockExtension.url());
    }

    @Test
    void readVersionResponseString() {
        setupSuccessResponse();

        Observable<Result<String>> serverVersion = openFairDbMonitorApi.getServerVersion();
        TestObserver<String> testObserver = serverVersion
                .map(result -> {
                    boolean noError = !result.isError();
                    Response<String> response = result.response();
                    if (noError && response != null) {
                        return response.body();
                    }
                    return null;
                })
                .test();


        testObserver.assertValue(SERVER_VERSION);
    }

    private void setupSuccessResponse() {
        stubFor(any(anyUrl())
                .willReturn(aResponse()
                        .withStatus(HttpURLConnection.HTTP_OK)
                        .withBody("1.2.3")
                ));
    }
}

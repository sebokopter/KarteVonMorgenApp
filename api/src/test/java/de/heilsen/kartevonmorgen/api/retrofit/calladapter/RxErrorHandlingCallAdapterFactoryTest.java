package de.heilsen.kartevonmorgen.api.retrofit.calladapter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.net.HttpURLConnection;

import de.heilsen.kartevonmorgen.api.entity.ApiException;
import de.heilsen.kartevonmorgen.api.retrofit.junitextensions.WireMockExtension;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.any;
import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;

class RxErrorHandlingCallAdapterFactoryTest {
    @SuppressWarnings("WeakerAccess")
    @RegisterExtension
    static WireMockExtension wireMockExtension = new WireMockExtension();

    @BeforeAll
    static void setUp() {
        setupServerResponse();
    }

    private static void setupServerResponse() {
        stubFor(any(anyUrl())
                .willReturn(aResponse()
                        .withStatus(HttpURLConnection.HTTP_FORBIDDEN)
                        .withBody("")));
    }

    @Test
    void correctlyOrderedCallAdapterFactories_returnsApiException() {
        Retrofit retrofit = createRetrofit().newBuilder()
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        AService aService = createService(retrofit);

        Observable<String> voidObservable = aService.anEndpoint();

        TestObserver<String> test = voidObservable.test();
        test.assertError(ApiException.class);
    }

    private Retrofit createRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(wireMockExtension.url())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    private AService createService(Retrofit retrofit) {
        retrofit.newBuilder()
                .baseUrl(wireMockExtension.url())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        return retrofit.create(AService.class);
    }

    @Test
    void wrongOrderedCallAdapterFactories_returnsDefaultRetrofitException() {
        Retrofit retrofit = createRetrofit().newBuilder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .build();
        AService aService = createService(retrofit);

        Observable<String> voidObservable = aService.anEndpoint();

        TestObserver<String> testObserver = voidObservable.test();
        testObserver.assertError(HttpException.class);
    }

    interface AService {
        @GET("/")
        Observable<String> anEndpoint();
    }
}

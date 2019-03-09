package de.heilsen.kartevonmorgen.api.retrofit.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import de.heilsen.kartevonmorgen.api.entity.Bbox;
import de.heilsen.kartevonmorgen.api.retrofit.junitextensions.WireMockExtension;
import okhttp3.HttpUrl;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.any;
import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class BboxConverterFactoryTest {
    @SuppressWarnings("WeakerAccess")
    @RegisterExtension
    static WireMockExtension wireMockExtension = new WireMockExtension();

    private SimpleService simpleService;

    @BeforeEach
    void setUp() {
        initService();
    }

    private void initService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(wireMockExtension.url())
                .addConverterFactory(BboxConverterFactory.create())
                .build();
        simpleService = retrofit.create(SimpleService.class);
    }

    @Test
    void convertToString() {
        HttpUrl expectedRequestUrl =
                HttpUrl.get(wireMockExtension.url()).newBuilder().query("bbox=1.0,2.0,3.0,4.0").build();

        Call<ResponseBody> call = simpleService.anEndpoint(new Bbox(1, 2, 3, 4));

        HttpUrl actualRequestUrl = call.request().url();
        assertThat(actualRequestUrl, is(equalTo(expectedRequestUrl)));
    }

    interface SimpleService {
        @GET("/")
        Call<ResponseBody> anEndpoint(@Query(value = "bbox", encoded = true) Bbox bbox);
    }
}
package de.heilsen.kartevonmorgen.api.retrofit.converter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.util.Arrays;
import java.util.List;

import de.heilsen.kartevonmorgen.api.retrofit.junitextensions.WireMockExtension;
import okhttp3.HttpUrl;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.any;
import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class StringListConverterFactoryTest {
    @SuppressWarnings("WeakerAccess")
    @RegisterExtension
    static WireMockExtension wireMockExtension = new WireMockExtension();

    private SimpleService simpleService;

    @BeforeAll
    static void setUpClass() {
        setupServerResponse();
    }

    private static void setupServerResponse() {
        stubFor(any(anyUrl())
                .willReturn(aResponse()
                        .withStatus(java.net.HttpURLConnection.HTTP_OK)
                        .withBody("")));
    }

    @BeforeEach
    void setUp() {
        initService();
    }

    private void initService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(wireMockExtension.url())
                .addConverterFactory(StringListConverterFactory.create())
                .build();
        simpleService = retrofit.create(SimpleService.class);
    }

    @Test
    void convertToString() {
        HttpUrl expectedRequestUrl =
                HttpUrl.get(wireMockExtension.url()).newBuilder().encodedPath("/entries/1,b,3.0").build();

        Call<ResponseBody> call = simpleService.anEndpoint(Arrays.asList("1", "b", "3.0"));

        HttpUrl actualRequestUrl = call.request().url();
        assertThat(actualRequestUrl, is(equalTo(expectedRequestUrl)));
    }

    interface SimpleService {
        @GET("/entries/{entryIds}")
        Call<ResponseBody> anEndpoint(@Path(value = "entryIds") List<String> ids);
    }
}
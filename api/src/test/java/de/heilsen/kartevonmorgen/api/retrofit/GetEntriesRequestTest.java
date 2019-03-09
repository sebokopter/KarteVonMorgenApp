package de.heilsen.kartevonmorgen.api.retrofit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import de.heilsen.kartevonmorgen.api.OpenFairDb;
import de.heilsen.kartevonmorgen.api.OpenFairDbApi;
import de.heilsen.kartevonmorgen.api.entity.Entry;
import de.heilsen.kartevonmorgen.api.retrofit.junitextensions.WireMockExtension;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import retrofit2.Response;
import retrofit2.adapter.rxjava2.Result;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.any;
import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;

class GetEntriesRequestTest {
    @SuppressWarnings("WeakerAccess")
    @RegisterExtension
    static WireMockExtension wireMockExtension = new WireMockExtension();

    @Test
    void validateGetEntriesRequest() throws Exception {
        stubFor(any(anyUrl())
                .willReturn(aResponse()
                        .withStatus(HttpURLConnection.HTTP_OK)
                        .withBodyFile("getEntries-200.json")
                ));
        OpenFairDbApi openFairDbApi = OpenFairDb.service(wireMockExtension.url());
        List<String> ids = new ArrayList<String>() {{
            add("ff20f44776c0486682bc2e926d6d2bb3");
            add("7efb0bd2773246b8a27d67bf258bc59a");
        }};

        Observable<Result<List<Entry>>> entries = openFairDbApi.getEntries(ids);

        TestObserver<List<Entry>> testObserver = entries
                .map(result -> {
                    boolean noError = !result.isError();
                    Response<List<Entry>> response = result.response();
                    if (noError && response != null) {
                        return response.body();
                    }
                    return null;
                })
                .test();
        testObserver.assertValue(Arrays.asList(solawis, unverpacktKiel));
    }

    private static final Entry solawis = Entry.builder()
            .id("ff20f44776c0486682bc2e926d6d2bb3")
            .created(1524646051)
            .version(10)
            .title("SoLaWiS - Solidarische Landwirtschaft Stuttgart")
            .description("Gemeinsam mit dem Reyerhof hat die Initiative \"SoLaWiS\" die Idee des Solidarischen Landwirtschaftens umgesetzt und trägt zu nachhaltiger, ökologischer, regionaler und saisonaler Lebensmittelversorgung bei. Mach mit! :)")
            .lat(48.7244278)
            .lng(9.14492801713147)
            .street("Unteraicher Straße 8")
            .zip("70567")
            .city("Stuttgart")
            .country(null)
            .email("mail@solawis.de")
            .telephone(null)
            .homepage("http://www.solawis.de")
            .categories(Collections.singletonList("2cd00bebec0c48ba9db761da48678134"))
            .tags(Arrays.asList("csa",
                    "honig",
                    "demeter",
                    "solawi",
                    "teikei",
                    "socent"))
            .ratings(Arrays.asList(
                    "60ab3f52c21a444d8fcb90417b5e98a7",
                    "0263aa5485b74e8283e9f1784416b838",
                    "7f0809130cc6454dba395d144fa7c5fd",
                    "3aab025d29a946f7ae9a50640097d15c",
                    "225505c885ca473b88767e14289a92f5"))
            .license("CC0-1.0")
            .image_link_url(null)
            .image_url(null)
            .build();

    private static final Entry unverpacktKiel = Entry.builder()
            .id("7efb0bd2773246b8a27d67bf258bc59a")
            .created(1543259563)
            .version(7)
            .title("Unverpackt Kiel")
            .description("Lebensmittel, Kosmetikprodukte und mehr\nSie waren die Ersten in Deutschland. Das Beratungsangebot richtet sich an Existenzgründer oder existierende Lebensmittelläden, die einen Teil ihres Sortiments auf unverpackte Waren umstellen möchten.\n")
            .lat(54.32077859967188)
            .lng(10.125741362571716)
            .street("Adelheidstraße 28")
            .zip("24103")
            .city("Kiel")
            .country(null)
            .email(null)
            .telephone(null)
            .homepage("http://www.unverpackt-kiel.de/")
            .categories(Collections.singletonList("77b3c33a92554bcf8e8c2c86cedd6f6f"))
            .tags(Arrays.asList(
                    "fairnetzt",
                    "kiel",
                    "saisonal",
                    "regional",
                    "ökologisch",
                    "biologisch",
                    "unverpackt",
                    "bio",
                    "foodsharing",
                    "ernährungsdemokratie",
                    "ernährungsrat",
                    "ernährungswende",
                    "agrarwende"
            ))

            .ratings(Arrays.asList(
                    "15838fdaff4b4375b17ee9e87438c831",
                    "fc7d58fa4d204431907cb646926126bb",
                    "f8166260ab744879b3ccfabcfe8e305a",
                    "84256490c24c4192955811363d27403d"
            ))
            .license("CC0-1.0")
            .image_url("http://fairnetz-kiel.de/wp-content/uploads/2017/11/fure2_2-02-300x300.png")
            .image_link_url("http://fairnetz-kiel.de/project/unverpackt/")
            .build();

}
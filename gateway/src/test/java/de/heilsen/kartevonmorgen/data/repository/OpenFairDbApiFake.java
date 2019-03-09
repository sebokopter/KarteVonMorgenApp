package de.heilsen.kartevonmorgen.data.repository;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.heilsen.kartevonmorgen.api.OpenFairDbApi;
import de.heilsen.kartevonmorgen.api.entity.Bbox;
import de.heilsen.kartevonmorgen.api.entity.Category;
import de.heilsen.kartevonmorgen.api.entity.Entry;
import de.heilsen.kartevonmorgen.api.entity.EntryLocation;
import de.heilsen.kartevonmorgen.api.entity.SearchResult;
import io.reactivex.Observable;
import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.adapter.rxjava2.Result;

public class OpenFairDbApiFake implements OpenFairDbApi {
    public static final EntryLocation VISIBLE_LOCATION1 =
            new EntryLocation("ff20f44776c0486682bc2e926d6d2bb3", 48.7244278, 9.14492801713147);
    public static final Entry SOLAWIS = Entry.builder()
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
    public static final Entry UNVERPACKT_KIEL = Entry.builder()
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
    public static final EntryLocation VISIBLE_LOCATION2 =
            new EntryLocation("7efb0bd2773246b8a27d67bf258bc59a", 54.32077859967188, 10.125741362571716);
    public static final List<EntryLocation> VISIBLE_LOCATIONS = Arrays.asList(VISIBLE_LOCATION1, VISIBLE_LOCATION2);
    private static final EntryLocation INVISIBLE_LOCATION =
            new EntryLocation("7efb0bd2773246b8a27d67bf258bc59a", 54.32077859967188, 10.125741362571716);
    private static final List<EntryLocation> INVISIBLE_LOCATIONS = Collections.singletonList(INVISIBLE_LOCATION);
    public static final SearchResult SEARCH_RESULT = new SearchResult(VISIBLE_LOCATIONS, INVISIBLE_LOCATIONS);
    public static final Observable<Result<SearchResult>> RESULT_SEARCH_RESULT =
            Observable.just(Result.response(Response.success(SEARCH_RESULT)));
    private static final Map<String, Entry> ENTRY_MAP = new HashMap<String, Entry>() {{
        put(SOLAWIS.getId(), SOLAWIS);
        put(UNVERPACKT_KIEL.getId(), UNVERPACKT_KIEL);
    }};
    private static final Observable<Result<List<Category>>> CATEGORIES =
            Observable.just(Result.response(Response.success(Arrays.asList(
                    new Category("2cd00bebec0c48ba9db761da48678134", "Initiative", 1, 1448792571276L),
                    new Category("c2dc278a2d6a4b9b8a50cb606fc017ed", "Event", 1, 1448792571276L),
                    new Category("77b3c33a92554bcf8e8c2c86cedd6f6f", "Unternehmen", 1, 1448792571276L)
            ))));

    @Override
    public Observable<Result<List<Category>>> getCategories() {
        return CATEGORIES;
    }

    @Override
    public Observable<Result<SearchResult>> search(String text, Bbox bbox) {
        return RESULT_SEARCH_RESULT;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Observable<Result<List<Entry>>> getEntries(List<String> ids) {
        return entriesByIds(ids);
    }

    private Observable<Result<List<Entry>>> entriesByIds(List<String> ids) {
        return Observable.fromIterable(ids)
                .map(ENTRY_MAP::get)
                .toList()
                .map(entryList -> Result.response(Response.success(entryList)))
                .toObservable();
    }

    @Override
    public Observable<Result<Category>> getCategory(long categoryId) {
        return null;
    }

    @Override
    public Observable<Result<List<String>>> getTags() {
        return null;
    }

}

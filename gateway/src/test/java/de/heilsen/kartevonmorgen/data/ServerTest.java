package de.heilsen.kartevonmorgen.data;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import de.heilsen.kartevonmorgen.core.entity.Area;
import de.heilsen.kartevonmorgen.core.entity.Category;
import de.heilsen.kartevonmorgen.core.entity.Coordinate;
import de.heilsen.kartevonmorgen.core.entity.Id;
import de.heilsen.kartevonmorgen.core.entity.Poi;
import de.heilsen.kartevonmorgen.core.entity.Rating;
import de.heilsen.kartevonmorgen.core.repository.PoiRepository;
import io.reactivex.Observable;

@Tag("network")
class ServerTest {

    private static Poi solawis = Poi.builder()
            .id(new Id("ff20f44776c0486682bc2e926d6d2bb3"))
            .created(1524646051)
            .title("SoLaWiS - Solidarische Landwirtschaft Stuttgart")
            .description("Gemeinsam mit dem Reyerhof hat die Initiative \"SoLaWiS\" die Idee des Solidarischen Landwirtschaftens umgesetzt und trägt zu nachhaltiger, ökologischer, regionaler und saisonaler Lebensmittelversorgung bei. Mach mit! :)")
            .coordinate(new Coordinate(48.7244278, 9.14492801713147))
            .categories(Collections.singletonList(new Category("2cd00bebec0c48ba9db761da48678134", "2cd00bebec0c48ba9db761da48678134")))
            .tags(Arrays.asList("csa", "honig", "demeter", "solawi", "teikei", "socent"))
            .ratings(Arrays.asList(
                    new Rating("60ab3f52c21a444d8fcb90417b5e98a7"),
                    new Rating("0263aa5485b74e8283e9f1784416b838"),
                    new Rating("7f0809130cc6454dba395d144fa7c5fd"),
                    new Rating("3aab025d29a946f7ae9a50640097d15c"),
                    new Rating("225505c885ca473b88767e14289a92f5")))
            .build();

    private static Poi teikeiCoffee = Poi.builder()
            .id(new Id("043a57a47a344c638088cca5ac1708bc"))
            .created(1549828254)
            .title("Teikei Gemeinschaft Reyerhof Solawi Stuttgart")
            .description("Solidargemeinschaft für den mexikanischen biodynamischen Kaffee aus Agroforstwirtschaft (Permakultur) gehandelt in einem Netzwerk des assoziativen Wirtschaftens.")
            .coordinate(new Coordinate(48.7244278, 9.14492801713147))
            .categories(Collections.singletonList(new Category("2cd00bebec0c48ba9db761da48678134", "2cd00bebec0c48ba9db761da48678134")))
            .tags(Arrays.asList("teikei", "teikei-gemeinschaft"))
            .ratings(Collections.singletonList(new Rating("47abc8cebc904c5e8094e991ff07f0eb")))
            .build();

    private final PoiRepository openFairDbEntryGateway = PoiGateway.ofdbPoiRepository("https://api.ofdb.io/v0/");

    @Test
    void getEntriesSolawisAndTeikei() {
        Observable<Poi> entries =
                openFairDbEntryGateway.getEntries(
                        new Area(
                                new Coordinate(48.7244278, 9.14492801713147),
                                new Coordinate(48.7244278, 9.14492801713147)));

        entries.test().assertValueSet(Arrays.asList(solawis, teikeiCoffee));
    }
}

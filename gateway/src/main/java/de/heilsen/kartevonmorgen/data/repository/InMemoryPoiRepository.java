package de.heilsen.kartevonmorgen.data.repository;

import de.heilsen.kartevonmorgen.core.entity.Area;
import de.heilsen.kartevonmorgen.core.entity.Coordinate;
import de.heilsen.kartevonmorgen.core.entity.Poi;
import de.heilsen.kartevonmorgen.core.repository.PoiRepository;
import io.reactivex.Observable;

public class InMemoryPoiRepository implements PoiRepository {

    @Override
    public Observable<Poi> getEntries(Area area, String searchTerm) {
        return getEntries(area);
    }

    public Observable<Poi> getEntries(Area visibleRegion) {

        Poi solawis = Poi.builder()
                .title("Solawis")
                .description(
                        "Gemeinsam mit dem Reyerhof hat die Initiative \"SoLaWiS\" "
                        + "die Idee des Solidarischen Landwirtschaftens umgesetzt und trägt "
                        + "zu nachhaltiger, ökologischer, regionaler und saisonaler "
                        + "Lebensmittelversorgung bei. Mach mit! :)")
                .coordinate(new Coordinate(48.7244278, 9.14492801713147))
                .build();
        Poi solawis2 = Poi.builder()
                .title("Solawis")
                .description("Gemeinsam mit dem Reyerhof hat die Initiative \"SoLaWiS\" "
                             + "die Idee des Solidarischen Landwirtschaftens umgesetzt und trägt "
                             + "zu nachhaltiger, ökologischer, regionaler und saisonaler "
                             + "Lebensmittelversorgung bei. Mach mit! :)")
                .coordinate(new Coordinate(48.73, 9.15))
                .build();
        Poi unverpacktKiel = Poi.builder()
                .title("Unverpackt Kiel")
                .description("Lebensmittel, Kosmetikprodukte und mehr\n"
                             + "Sie waren die Ersten in Deutschland. Das Beratungsangebot richtet "
                             + "sich an Existenzgründer oder existierende Lebensmittelläden, "
                             + "die einen Teil ihres Sortiments auf unverpackte Waren "
                             + "umstellen möchten.")
                .coordinate(new Coordinate(54.32077859967188, 10.125741362571716))
                .build();
        return Observable.just(solawis, solawis2, unverpacktKiel);
    }
}

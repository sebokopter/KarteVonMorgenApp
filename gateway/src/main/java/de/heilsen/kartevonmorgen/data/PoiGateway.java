package de.heilsen.kartevonmorgen.data;

import de.heilsen.kartevonmorgen.api.OpenFairDb;
import de.heilsen.kartevonmorgen.api.OpenFairDbApi;
import de.heilsen.kartevonmorgen.core.repository.PoiRepository;
import de.heilsen.kartevonmorgen.data.repository.InMemoryPoiRepository;
import de.heilsen.kartevonmorgen.data.repository.OpenFairDbPoiRepository;

public class PoiGateway {
    public static PoiRepository ofdbPoiRepository(String url) {
        OpenFairDbApi openFairDbApi = OpenFairDb.service(url);
        return new OpenFairDbPoiRepository(openFairDbApi);
    }

    public static PoiRepository inMemoryRepository() {
        return new InMemoryPoiRepository();
    }
}

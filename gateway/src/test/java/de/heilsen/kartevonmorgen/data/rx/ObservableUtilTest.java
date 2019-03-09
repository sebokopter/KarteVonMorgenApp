package de.heilsen.kartevonmorgen.data.rx;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static java.util.Collections.emptyList;

class ObservableUtilTest {
    @Test
    void mapEntries_emptyList() {
        ObservableUtil.mapEntries(emptyList(), o -> o)
                .test().assertNoValues();
    }

    @Test
    void mapEntries_intListToString() {
        ObservableUtil.mapEntries(Arrays.asList(1, 2, 3), String::valueOf)
                .test().assertValues("1", "2", "3");
    }
}
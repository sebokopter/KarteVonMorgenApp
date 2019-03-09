package de.heilsen.kartevonmorgen.api.util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class CsvTest {
    @Test
    void of_doubles() {
        assertThat(Csv.of(1d, 2d, 3d), is("1.0,2.0,3.0"));
    }

    @Test
    void of_strings() {
        assertThat(Csv.of("a", "b", "c"), is("a,b,c"));
    }

    @Test
    void of_stringList() {
        List<String> stringList = Arrays.asList("a", "b", "c");

        assertThat(Csv.of(stringList), is("a,b,c"));
    }
}
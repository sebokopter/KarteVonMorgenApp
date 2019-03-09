package de.heilsen.kartevonmorgen.api.util;

import com.google.common.base.Joiner;

import java.util.Arrays;
import java.util.List;

public class Csv {

    public static String of(Double... doubles) {
        return Joiner.on(',').join(Arrays.asList(doubles));
    }

    public static String of(String... strings) {
        return of(Arrays.asList(strings));
    }

    public static String of(List<String> strings) {
        return Joiner.on(',').join(strings);
    }
}

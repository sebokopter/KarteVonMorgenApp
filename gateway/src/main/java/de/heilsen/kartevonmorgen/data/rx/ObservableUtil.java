package de.heilsen.kartevonmorgen.data.rx;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class ObservableUtil {
    public static <F, T> Observable<T> mapEntries(Iterable<F> iterable,
                                                  Function<F, T> mapFunction) {
        return Observable.fromIterable(iterable)
                .map(mapFunction);
    }
}
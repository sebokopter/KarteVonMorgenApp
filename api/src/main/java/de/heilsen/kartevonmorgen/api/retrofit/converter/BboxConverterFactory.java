package de.heilsen.kartevonmorgen.api.retrofit.converter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import de.heilsen.kartevonmorgen.api.entity.Bbox;
import de.heilsen.kartevonmorgen.api.util.Csv;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.http.Query;

public class BboxConverterFactory extends Converter.Factory {

    public static BboxConverterFactory create() {
        return new BboxConverterFactory();
    }
    private BboxConverterFactory() {}

    @Nullable
    @Override
    public Converter<?, String> stringConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        for (Annotation annotation : annotations) {
            if (annotation.annotationType() == Query.class && type == Bbox.class) {
                return new StringConverter();
            }
        }
        return null;
    }

    public static class StringConverter implements Converter<Bbox, String> {
        @Override
        public String convert(@Nonnull Bbox bbox) {
            return Csv.of(bbox.getMinLat(), bbox.getMinLng(), bbox.getMaxLat(), bbox.getMaxLng());
        }
    }
}

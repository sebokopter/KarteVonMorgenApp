package de.heilsen.kartevonmorgen.api.retrofit.converter;

import com.squareup.moshi.Types;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import de.heilsen.kartevonmorgen.api.util.Csv;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.http.Path;

public class StringListConverterFactory extends Converter.Factory {

    public static StringListConverterFactory create() {
        return new StringListConverterFactory();
    }
    private StringListConverterFactory() {}

    @Nullable
    @Override
    public Converter<?, String> stringConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        Type stringList = Types.newParameterizedType(List.class, String.class);
        for (Annotation annotation : annotations) {
            if (annotation.annotationType() == Path.class && type.equals(stringList)) {
                return new StringListConverterFactory.StringConverter();
            }
        }
        return null;
    }

    private class StringConverter implements Converter<List<String>, String> {
        @Nullable
        @Override
        public String convert(@Nonnull List<String> value) {
            return Csv.of(value);
        }
    }
}

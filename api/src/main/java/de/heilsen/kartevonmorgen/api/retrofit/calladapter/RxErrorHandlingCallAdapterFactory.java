package de.heilsen.kartevonmorgen.api.retrofit.calladapter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import de.heilsen.kartevonmorgen.api.entity.ApiException;
import io.reactivex.Observable;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;

/**
 * {@link CallAdapter.Factory} for converting errors returned by Observable Retrofit calls to {@link ApiException}.
 * Must be called before {@link retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory}.
 * <p>
 * TODO: currently only works for Observable types
 *
 * @param <T> Parameter of Observable Retrofit call
 */
public class RxErrorHandlingCallAdapterFactory<T> extends CallAdapter.Factory {

    private RxErrorHandlingCallAdapterFactory() {}

    public static RxErrorHandlingCallAdapterFactory create() {
        return new RxErrorHandlingCallAdapterFactory();
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public CallAdapter<?, Observable<T>> get(@Nonnull Type returnType, @Nonnull Annotation[] annotations,
                                             @Nonnull Retrofit retrofit) {

        boolean isObservable = getRawType(returnType) == Observable.class;

        if (!isObservable) return null;

        final CallAdapter<Object, Observable<?>> delegate =
                (CallAdapter<Object, Observable<?>>) retrofit.nextCallAdapter(this, returnType, annotations);

        return new RxErrorHandlingCallAdapter<>(delegate);
    }
}

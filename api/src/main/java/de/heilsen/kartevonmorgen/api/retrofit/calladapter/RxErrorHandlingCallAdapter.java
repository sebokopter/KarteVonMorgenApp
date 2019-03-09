package de.heilsen.kartevonmorgen.api.retrofit.calladapter;

import java.lang.reflect.Type;

import javax.annotation.Nonnull;

import de.heilsen.kartevonmorgen.api.entity.ApiException;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.CallAdapter;

/**
 * {@link CallAdapter} which converts errors returned by Observable Retrofit calls to {@link ApiException}.
 */
class RxErrorHandlingCallAdapter<T> implements CallAdapter<Object, Observable<T>> {
    private final CallAdapter<Object, Observable<?>> delegate;

    RxErrorHandlingCallAdapter(CallAdapter<Object, Observable<?>> delegate) {
        this.delegate = delegate;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Nonnull
    public Observable<T> adapt(@Nonnull Call<Object> call) {
        Observable<T> observable = (Observable<T>) delegate.adapt(call);

        return observable.onErrorResumeNext(throwable -> {
            return Observable.error(ApiException.from(throwable));
        });
    }


    @Override
    @Nonnull
    public Type responseType() {
        return delegate.responseType();
    }
}

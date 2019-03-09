package de.heilsen.kartevonmorgen.data.retrofit;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.adapter.rxjava2.Result;

public class ResultUtils {

    public static <T> Observable<T> bodyOf(Observable<Result<T>> resultMaybe) {
        return resultMaybe.compose(extractBody());
    }

    static <T> ObservableTransformer<Result<T>, T> extractBody() {
        return upstream -> {
            if (upstream == null) return Observable.empty();
            return upstream.map(result -> {
                if (result == null) throw new NullPointerException("result is null");
                if (result.isError()) {
                    throw handleResultError(result.error());
                }
                return handleResultSuccess(result);
            });
        };
    }

    //TODO: implement me
    private static Exception handleResultError(Throwable throwable) {
        return new UnsupportedOperationException(throwable);
    }

    private static <T> T handleResultSuccess(Result<T> result) throws Exception {
        Response<T> response = result.response();
        if (response == null) throw new NullPointerException("response is null");
        if (!response.isSuccessful()) {
            throw handleResponseError(response);
        }
        return handleResponseSuccess(response);
    }

    private static <T> Exception handleResponseError(Response<T> response) {
        return new HttpException(response); //TODO: return HTTP error
    }

    private static <T> T handleResponseSuccess(Response<T> response) {
        T body = response.body();
        if (body == null)
            throw new NullPointerException("body is null"); //TODO: define better what kind of error is this?!
        return body;
    }
}

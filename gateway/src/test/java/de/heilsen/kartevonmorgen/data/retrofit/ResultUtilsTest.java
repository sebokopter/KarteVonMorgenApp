package de.heilsen.kartevonmorgen.data.retrofit;

import org.junit.jupiter.api.Test;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.adapter.rxjava2.Result;

import static retrofit2.Response.success;
import static retrofit2.adapter.rxjava2.Result.error;
import static retrofit2.adapter.rxjava2.Result.response;

class ResultUtilsTest {
    @Test
    void extractBodyOfSuccessResult() {
        String string = "success";
        Result<String> result = successResponseResult(string);

        TestObserver<String> testObserver =
                Observable.just(result).compose(ResultUtils.extractBody()).test();

        testObserver
                .assertValue(string)
                .assertComplete();
    }

    private Result<String> successResponseResult(String string) {
        return response(success(string));
    }

    //TODO: implement me
    @Test
    void extractBodyOfErrorResult() {
        Exception testException = new Exception("An error occurred");
        Result<String> result = error(testException);

        TestObserver<String> testObserver = Observable.just(result).compose(ResultUtils.extractBody()).test();

        testObserver.assertError(UnsupportedOperationException.class);  //TODO: Throws UOException until implemented
    }

    @Test
    void throwNPEOnNullResponse() {
        Result<String> result = response(success(null));

        TestObserver<String> testObserver = Observable.just(result).compose(ResultUtils.extractBody()).test();

        testObserver.assertError(NullPointerException.class);
    }

    @Test
    void throwHttpExceptionOn500ErrorResponse() {
        Result<String> result =
                response(Response.error(500, ResponseBody.create(MediaType.parse("text/text"), "")));

        TestObserver<String> testObserver = Observable.just(result).compose(ResultUtils.extractBody()).test();

        testObserver.assertError(HttpException.class);
    }

    @Test
    void returnBodyOfRetrofitObservableResultSuccessResponse() {
        Observable<Result<String>> result = Observable.just(Result.response(Response.success("string")));

        Observable<String> body = ResultUtils.bodyOf(result);

        body.test().assertValue("string");
    }


}
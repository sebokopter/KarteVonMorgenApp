package de.heilsen.kartevonmorgen.api;

import io.reactivex.Observable;
import retrofit2.adapter.rxjava2.Result;
import retrofit2.http.GET;

public interface OpenFairDbMonitorApi {

    @GET("/count/tags")
    Observable<Result<Long>> getCountTags();

    @GET("/count/entries")
    Observable<Result<Long>> getCountEntries();

    @GET("/server/version")
    Observable<Result<String>> getServerVersion();
}

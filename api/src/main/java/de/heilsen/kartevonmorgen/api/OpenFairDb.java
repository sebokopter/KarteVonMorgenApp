package de.heilsen.kartevonmorgen.api;

import de.heilsen.kartevonmorgen.api.retrofit.converter.BboxConverterFactory;
import de.heilsen.kartevonmorgen.api.retrofit.converter.StringListConverterFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

//TODO: logging should be set from outside
public class OpenFairDb {
    public static OpenFairDbApi service(String url) {
        return createMoshiRetrofit(url).create(OpenFairDbApi.class);
    }

    private static Retrofit createMoshiRetrofit(String url) {
        return createBaseRetrofit(url).newBuilder()
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
    }

    private static Retrofit createBaseRetrofit(String url) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(BboxConverterFactory.create())
                .addConverterFactory(StringListConverterFactory.create())
                .build();
    }

    public static OpenFairDbMonitorApi monitor(String url) {
        return createScalarsRetrofit(url).create(OpenFairDbMonitorApi.class);
    }

    private static Retrofit createScalarsRetrofit(String url) {
        return createBaseRetrofit(url).newBuilder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }
}


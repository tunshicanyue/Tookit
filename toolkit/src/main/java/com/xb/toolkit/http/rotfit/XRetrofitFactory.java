package com.xb.toolkit.http.rotfit;

import com.xb.toolkit.http.gson.GsonCustomConverterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


public class XRetrofitFactory {
    private static OkHttpClient.Builder builder;
    static {
        int OUT_TIME = 20;//网络连接超时
        builder = new OkHttpClient.Builder()
                .connectTimeout(OUT_TIME, TimeUnit.SECONDS)
                .writeTimeout(OUT_TIME, TimeUnit.SECONDS);
    }

    /**
     * @param url
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T create(String url, Class<T> clazz) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(builder.build())
                .addConverterFactory(GsonCustomConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(clazz);
    }
}

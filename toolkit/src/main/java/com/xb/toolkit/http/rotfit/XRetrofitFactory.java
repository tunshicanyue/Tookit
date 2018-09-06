package com.xb.toolkit.http.rotfit;

import com.xb.toolkit.Toolkit;
import com.xb.toolkit.http.gson.GsonCustomConverterFactory;
import com.xb.toolkit.utils.log.LogUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


public class XRetrofitFactory {
    private static OkHttpClient.Builder builder;

    static {
        int OUT_TIME = 20;//网络连接超时
        builder = new OkHttpClient.Builder()
                .connectTimeout(OUT_TIME, TimeUnit.SECONDS)
                .writeTimeout(OUT_TIME, TimeUnit.SECONDS);

        //是否打印日志
        if (Toolkit.getToolkit().isDebug()) {
            builder.addInterceptor(chain -> {
                Request request = chain.request();
                Response response = chain.proceed(request);
                String param = getRequestParam(request);
                LogUtils.e("[ url =" + chain.request().url() +
                        " method: " + chain.request().method() +
                        " param: " + param + " ]");
                LogUtils.e("header:" + request.headers().toString());
                return response;
            });
        }
    }


    /*请求参数*/
    private static String getRequestParam(Request request) {
        String result = "";
        if (!request.method().contains("GET")) {
            if (request.body() instanceof FormBody) {
                FormBody formBody = (FormBody) request.body();
                if (formBody != null && formBody.size() > 0) {
                    for (int i = 0, len = formBody.size(); i < len; i++) {
                        result = result + (formBody.encodedName(i) + "=" + formBody.encodedValue(i));
                        if (i < len - 1) result += "&";
                    }
                }
            }
        }
        return result;
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

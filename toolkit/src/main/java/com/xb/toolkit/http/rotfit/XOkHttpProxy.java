package com.xb.toolkit.http.rotfit;

import android.util.Log;

import com.xb.toolkit.utils.LogUtils;

import java.io.IOException;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by admin on 2018/7/17.
 */

public class XOkHttpProxy {
    private XOkHttpProxy() {
    }


    public static void sendHttp(String url) {
        //创建网络处理的对象
        OkHttpClient client = new OkHttpClient.Builder()
                //设置读取数据的时间
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                //对象的创建
                .build();
        //在请求对象里面传入链接的URL地址
        Request request = new Request.Builder().url(url).build();
        Call newCall = client.newCall(request);
        newCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                if (body != null) {
                    byte[] bytes = body.bytes();
                    String gb2312 = new String(bytes, "gb2312");
                    LogUtils.e("tread =" + Thread.currentThread());
                    LogUtils.e(gb2312);
                } else {
                    onFailure(call, new IOException(""));
                }
            }
        });

    }


}

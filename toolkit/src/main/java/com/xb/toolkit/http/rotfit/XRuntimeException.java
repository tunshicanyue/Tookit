package com.xb.toolkit.http.rotfit;


import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * 自定义网络异常
 */
public class XRuntimeException extends RuntimeException {
    private String message;


    public static final String LINK_NOTWORK = "请连接网络";

    public XRuntimeException(Throwable throwable) {
        message = throwable(throwable);
    }

    public XRuntimeException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String throwable(Throwable throwable) {
        String result = throwable.getMessage();
        if (throwable instanceof SocketTimeoutException) {
            result = "网络连接超时";
        } else if (throwable instanceof SocketException) {
            result = "网络连接异常";
        } else if (throwable instanceof JSONException
                || throwable instanceof JsonParseException) {
            result = "json数据解析异常";
        } else if (throwable instanceof UnknownHostException) {
            result = "网络连接地址异常";
        }
        return result;
    }
}

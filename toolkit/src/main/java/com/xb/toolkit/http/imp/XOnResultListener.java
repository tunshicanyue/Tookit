package com.xb.toolkit.http.imp;


import com.xb.toolkit.http.XHttpProxy;

/**
 * 数据返回的监听器
 */
public interface XOnResultListener<T> {

    /**
     * 请求成功
     *
     * @param t
     */
    void onRequestSuccess(T t);


    /**
     * 请求失败
     *
     * @param failedMsg   错误的msg
     * @param requestType 失败的类型
     */
    void onRequestFailed(String failedMsg, XHttpProxy.RequestType requestType);
}

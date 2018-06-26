package com.xb.toolkit;

import android.app.Application;
import android.text.TextUtils;

import com.xb.toolkit.http.XHttpProxy;


public final class Toolkit {

    private Application application;

    private XHttpProxy mHttpProxy;

    private boolean isDebug = false;
    private String mHttpUrl;
    static Toolkit mToolkit;

    Toolkit(Application application) {
       this.application = application;
    }

    public static Toolkit create(Application application) {
        synchronized (Toolkit.class) {
            if (mToolkit == null) mToolkit = new Toolkit(application);
        }
        return mToolkit;
    }

    /*设置是否请求的API地址*/
    public Toolkit setHttpUrl(String httpUrl) {
        mHttpUrl = httpUrl;
        return this;
    }


    /*是否打开调试*/
    public Toolkit setDebug(boolean isDebug) {
        this.isDebug = isDebug;
        return this;
    }

    /*初始化所有的接口数据*/
    public void init() {
        /*初始化接口的地址*/
        if (!TextUtils.isEmpty(mHttpUrl)) {
            mHttpProxy = XHttpProxy.init(mHttpUrl);
        }
    }


    public static Toolkit getToolkit() {
        if (mToolkit == null) {// 初始化該類
            throw new NullPointerException("Toolkit no init");
        }
        return mToolkit;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public String getHttpUrl() {
        if (TextUtils.isEmpty(mHttpUrl)) {
            throw new NullPointerException("HttpUrl no init");
        }
        return mHttpUrl;
    }

    public Application getApplication() {
        return application;
    }
}

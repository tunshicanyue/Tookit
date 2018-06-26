package com.xb.canyue;

import android.app.Application;

import com.xb.toolkit.Toolkit;


public class AppContext extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Toolkit.create(this)
                .setDebug(true)
                .setHttpUrl("http://phpweb.zhph.lan/api/")
                .init();
    }
}

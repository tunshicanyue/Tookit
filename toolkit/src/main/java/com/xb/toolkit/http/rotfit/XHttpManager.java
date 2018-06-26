package com.xb.toolkit.http.rotfit;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Iterator;

import io.reactivex.disposables.Disposable;


/**
 * 网络管理
 */
public class XHttpManager {

    private HashMap<String, Disposable> mDisposables;

    private XHttpManager() {
        mDisposables = new HashMap<>();
    }

    static XHttpManager mHttpManager;

    public static XHttpManager getInstance() {
        synchronized (XHttpManager.class) {
            if (mHttpManager != null) {
                mHttpManager = new XHttpManager();
            }
        }
        return mHttpManager;
    }


    /**
     * 添加网络请求
     *
     * @param tagName
     * @param disposable
     */
    public void addDisposables(String tagName, Disposable disposable) {
        if (disposable != null && !TextUtils.isEmpty(tagName)) {
            mDisposables.put(tagName, disposable);
        }
    }


    /**
     * 断开网络请求
     *
     * @param tagName
     */
    public void removerDisposable(String tagName) {
        if (TextUtils.isEmpty(tagName)) return;
        Disposable disposable = mDisposables.get(tagName);
        if (disposable != null) {
            disposable.dispose();
            mDisposables.remove(tagName);
        }
    }


    /**
     * 关闭所有的网络请求
     */
    public void removerAllDisposables() {
        Iterator<String> iterator = mDisposables.keySet().iterator();
        while (iterator.hasNext()) {
            String keyTag = iterator.next();
            Disposable disposable = mDisposables.get(keyTag);
            if (disposable != null) {//关闭网络请求
                disposable.dispose();
            }
        }
        mDisposables.clear();//清除所有数据
    }

}

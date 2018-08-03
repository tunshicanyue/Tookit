package com.xb.toolkit.http;


import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;

import com.xb.toolkit.Toolkit;
import com.xb.toolkit.bean.XBean;
import com.xb.toolkit.http.imp.XIOnDownloadResultListener;
import com.xb.toolkit.http.imp.XOnResultListener;
import com.xb.toolkit.http.rotfit.DownloadUtil;
import com.xb.toolkit.http.rotfit.XHttpManager;
import com.xb.toolkit.http.rotfit.XObserver;
import com.xb.toolkit.http.rotfit.XRetrofitFactory;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * 网络访问
 */
public class XHttpProxy {

    private static XHttpProxy mXHttpProxy;
    private static String mURL;

    private XHttpProxy(String URL) {
        mURL = URL;
    }

    public static XHttpProxy init() {
        return init(Toolkit.getToolkit().getHttpUrl());
    }

    public static XHttpProxy init(String URL) {
        synchronized (XHttpProxy.class) {
            if (mXHttpProxy == null) mXHttpProxy = new XHttpProxy(URL);
        }
        return mXHttpProxy;
    }

    /*初始化接口*/
    public static <T> T create(String URL, Class<T> clazz) {
        return XRetrofitFactory.create(URL, clazz);
    }

    /*初始化接口*/
    public static <T> T create(Class<T> clazz) {
        return XRetrofitFactory.create(mURL, clazz);
    }


    private void sendHttp(Builder builder) {
        if (builder == null) throw new RuntimeException("http request no builder null");
        Observable observable = builder.apiService
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new XObserver(builder));
//        if (builder.getActivity() != null || builder.rxFragment != null) {
//            if (builder.getActivity() instanceof RxActivity) {
//                observable.compose(((RxActivity) builder.getActivity()).bindUntilEvent(ActivityEvent.DESTROY));
//            } else if (builder.rxFragment.get() != null) {
//                observable.compose(builder.rxFragment.get().bindUntilEvent(FragmentEvent.DESTROY));
//            }
//        }

    }


    public static class Builder {
        private WeakReference<Activity> mContext;
        private Observable apiService;
        private boolean isShowDialog;
        private boolean isCache;//网络请求是否缓存
        private boolean isPage;//是否进行分页处理
        private boolean page;//页数
        private Class<? extends XBean> clazz;//实体bean
        private XOnResultListener mXOnResultListener;


        Builder setCache(boolean cache) {
            isCache = cache;
            return this;
        }

        public Builder setContext(Activity context) {
            mContext = new WeakReference<Activity>(context);
            return this;
        }

        public Builder setApiService(Observable apiService) {
            this.apiService = apiService;
            return this;
        }

        public Builder setShowDialog(boolean showDialog) {
            isShowDialog = showDialog;
            return this;
        }


        public Builder setClazz(Class<? extends XBean> clazz) {
            this.clazz = clazz;
            return this;
        }

        public Builder setXOnResultListener(XOnResultListener xOnResultListener) {
            mXOnResultListener = xOnResultListener;
            return this;
        }

        public WeakReference<Activity> getContext() {
            return mContext;
        }

        public Observable getApiService() {
            return apiService;
        }

        public boolean isShowDialog() {
            return isShowDialog;
        }

        public boolean isCache() {
            return isCache;
        }

        public Class<? extends XBean> getClazz() {
            return clazz;
        }

        public XOnResultListener getXOnResultListener() {
            return mXOnResultListener;
        }


        public void request() {
            if (mXHttpProxy == null) throw new NullPointerException("mXHttpProxy no init");
            mXHttpProxy.sendHttp(this);
        }

    }

    public enum RequestType {
        REQUEST_ERROR
    }


    public static void downloadFile(String url,
                                    String destFileDir,
                                    String destFileName,
                                    XIOnDownloadResultListener listener) {
        DownloadUtil.getInstance().download(url, destFileDir, destFileName, listener);
    }

    public XHttpManager xhttpManager() {
        return XHttpManager.getInstance();
    }

}

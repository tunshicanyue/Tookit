package com.xb.toolkit.imp.mvp;

import android.app.Activity;

import com.xb.toolkit.http.XHttpProxy;
import com.xb.toolkit.http.imp.XOnResultListener;
import com.xb.toolkit.ui.activity.XDefaultActivity;
import com.xb.toolkit.utils.messenger.AppMessenger;

public abstract class XBasePresenter<V extends XIBaseView, M extends XIBaseModel> implements XOnResultListener {
    protected V mView;
    protected M mIMode;
    protected Activity mActivity;

    /*绑定实体类*/
    public void onAttach(M IMode, V view, Activity activity) {
        mView = view;
        mIMode = IMode;
        mActivity = activity;
        this.start();
    }


    /*需要初始化一些数据的时候重写*/
    public void start() {
    }

    /*解除绑定*/
    public void onDetach() {
        mIMode = null;
        mView = null;
        mActivity = null;
        AppMessenger.getDefault().unregister(this);
    }

    /*返回具体的数据Model*/
    public abstract M getModel();

    @Override
    public void onRequestFailed(String failedMsg, XHttpProxy.RequestType requestType) {
        ((XDefaultActivity) mActivity).showToast(failedMsg);
    }
}

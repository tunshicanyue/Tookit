package com.xb.toolkit.http.rotfit;


import com.xb.toolkit.Toolkit;
import com.xb.toolkit.bean.XBean;
import com.xb.toolkit.http.XHttpProxy;
import com.xb.toolkit.http.dialog.XHttpDialogManager;
import com.xb.toolkit.http.imp.XOnResultListener;
import com.xb.toolkit.utils.NetWorkUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class XObserver<T extends XBean> implements Observer<T> {

    private XHttpProxy.Builder mBuilder;


    public XObserver(XHttpProxy.Builder builder) {
        if (builder == null) throw new RuntimeException("http request no builder null");
        mBuilder = builder;
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (mBuilder == null) {
            d.dispose();
            return;//结束 不执行
        }
        if (mBuilder.isShowDialog()){
            XHttpDialogManager.getInstance().showDialog(mBuilder.getContext().get());
        }


        //如果没有网络
        if (!NetWorkUtils.isNetworkConnected(Toolkit.getToolkit().getApplication())) {
            // 判断是否有缓存
            if (mBuilder.isCache()) {
                // TODO: 2018/6/27 暂时没找到处理的办法
            } else {//沒有缓存
                mBuilder.getXOnResultListener().onRequestFailed(
                        new XRuntimeException(XRuntimeException.LINK_NOTWORK).getMessage(),
                        XHttpProxy.RequestType.REQUEST_ERROR);
            }
        }
        XHttpManager.getInstance().addDisposables(mBuilder.getClazz().getName(), d);
    }

    @Override
    public void onNext(T t) {
        try {
            XOnResultListener xOnResultListener = mBuilder.getXOnResultListener();
            xOnResultListener.onRequestSuccess(t);
        } catch (Exception e) {
            onError(e);
        }
    }

    @Override
    public void onError(Throwable e) {
        mBuilder.getXOnResultListener().onRequestFailed(new XRuntimeException(e).getMessage(), XHttpProxy.RequestType.REQUEST_ERROR);
    }

    @Override
    public void onComplete() {
//        XHttpDialogManager.getInstance().dismissDialog();
        XHttpManager.getInstance().removerDisposable(mBuilder.getClazz().getName());
        if (mBuilder.getContext() != null) {
            mBuilder.getContext().clear();
        }
    }
}

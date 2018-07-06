package com.xb.toolkit.ui.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.xb.toolkit.imp.mvp.XBasePresenter;
import com.xb.toolkit.imp.mvp.XIBaseModel;
import com.xb.toolkit.imp.mvp.XIBaseView;

/**
 * Mvp的设计模式
 */
public abstract class XDefaultMVPActivity<P extends XBasePresenter, M extends XIBaseModel> extends XDefaultActivity implements XIBaseView {
    protected P mPresenter;
    protected M mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initMvpPresenter();
        super.onCreate(savedInstanceState);
    }

    /*初始化 Presenter与ViewModel*/
    private void initMvpPresenter() {
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mViewModel = (M) mPresenter.getModel();
            if (mViewModel != null) {
                mPresenter.onAttach(mViewModel, this, this);
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDetach();
    }

    protected abstract P createPresenter();
}

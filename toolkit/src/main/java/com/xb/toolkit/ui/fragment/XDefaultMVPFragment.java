package com.xb.toolkit.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.xb.toolkit.imp.mvp.XBasePresenter;
import com.xb.toolkit.imp.mvp.XIBaseModel;
import com.xb.toolkit.imp.mvp.XIBaseView;
import com.xb.toolkit.ui.activity.XDefaultActivity;

/**
 * MVP的Activity
 */
public abstract class XDefaultMVPFragment<P extends XBasePresenter, M extends XIBaseModel> extends XDefaultFragment implements XIBaseView {
    protected P mPresenter;
    protected M mViewModel;
    private XDefaultActivity mActivity;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        initMvpPresenter();
        super.onActivityCreated(savedInstanceState);
        mActivity = (XDefaultActivity) getActivity();
        mActivity.isFullScreen();

    }

    /*初始化 Presenter与ViewModel*/
    private void initMvpPresenter() {
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mViewModel = (M) mPresenter.getModel();
            if (mViewModel != null) {
                mPresenter.onAttach(mViewModel, this, getActivity());
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDetach();
        }
    }


    @Override
    public int layoutId() {
        return layoutID();
    }

    /**
     * 布局文件
     *
     * @return
     */
    protected abstract int layoutID();

    /**
     * @return
     */
    protected abstract P createPresenter();
}

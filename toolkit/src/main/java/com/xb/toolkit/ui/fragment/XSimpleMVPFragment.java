package com.xb.toolkit.ui.fragment;

import android.os.Bundle;

import com.xb.toolkit.imp.mvp.XBasePresenter;
import com.xb.toolkit.imp.mvp.XIBaseModel;
import com.xb.toolkit.imp.mvp.XIBaseView;
import com.xb.toolkit.ui.activity.XDefaultMVPActivity;


/**
 * 简单的MVPFragment
         */
public abstract class XSimpleMVPFragment extends XDefaultMVPActivity<XBasePresenter, XIBaseModel> implements XIBaseView {
    @Override
    protected XBasePresenter createPresenter() {
        return new XBasePresenter() {
            @Override
            public XIBaseModel getModel() {
                return new XIBaseModel() {};
            }
        };
    }
}

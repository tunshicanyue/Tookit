package com.xb.toolkit.ui.activity;


import com.xb.toolkit.imp.mvp.XBasePresenter;
import com.xb.toolkit.imp.mvp.XIBaseModel;
import com.xb.toolkit.imp.mvp.XIBaseView;

/**
 * 简单的MVP页面
 */
public abstract class XSimpleMVPActivity extends XDefaultMVPActivity<XBasePresenter, XIBaseModel> implements XIBaseView {
    @Override
    protected XBasePresenter createPresenter() {
        return new XBasePresenter() {
            @Override
            public XIBaseModel getModel() {
                return new XIBaseModel() {
                };
            }
        };
    }
}

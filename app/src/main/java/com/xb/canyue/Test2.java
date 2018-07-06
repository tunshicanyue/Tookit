package com.xb.canyue;

import android.os.Bundle;

import com.xb.toolkit.imp.mvp.XBasePresenter;
import com.xb.toolkit.imp.mvp.XIBaseModel;
import com.xb.toolkit.ui.activity.XDefaultMVPActivity;

/**
 * Created by admin on 2018/7/5.
 */

public class Test2 extends XDefaultMVPActivity<XBasePresenter,XIBaseModel> {
    @Override
    public int layoutID() {
        return 0;
    }

    @Override
    public void onCreateView(Bundle savedInstanceState) {

    }

    @Override
    public boolean isShowActionBar() {
        return true;
    }

    @Override
    public int layoutTitleID() {
        return 0;
    }

    @Override
    protected XBasePresenter createPresenter() {
        return null;
    }
}

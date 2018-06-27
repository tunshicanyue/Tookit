package com.xb.toolkit.http.dialog;

import android.app.Activity;
import android.app.Dialog;

import com.xb.toolkit.R;

import java.lang.ref.WeakReference;

import retrofit2.http.PUT;

/**
 * Created by admin on 2018/6/27.
 */

public class XHttpDialogManager {

    public static XHttpDialogManager mXHttpDialogManager;

    public static XBaseDialog mXBaseDialog;

    public static XHttpDialogManager getInstance() {
        synchronized (XHttpDialogManager.class) {
            if (mXHttpDialogManager == null) mXHttpDialogManager = new XHttpDialogManager();
        }
        return mXHttpDialogManager;
    }

    private XHttpDialogManager() {
    }


    public static void setXBaseDialog(XBaseDialog XBaseDialog) {
        mXBaseDialog = XBaseDialog;
    }


    public void showDialog(Activity activity) {
//        if (mXBaseDialog == null) mXBaseDialog = new XBaseDialog() {
//            @Override
//            WeakReference<Activity> activity() {
//                return new WeakReference<Activity>(activity);
//            }
//
//            @Override
//            int layoutID() {
//                return R.layout.x_http_default_dialog;
//            }
//        };
//        mXBaseDialog.showDialog();
    }

    public void dismissDialog() {
        if (mXBaseDialog != null && mXBaseDialog.isShowDialog()) {
            mXBaseDialog.dismiss();
        }
    }
}

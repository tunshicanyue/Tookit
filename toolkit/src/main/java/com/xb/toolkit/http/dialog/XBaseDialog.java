package com.xb.toolkit.http.dialog;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.lang.ref.WeakReference;

/**
 * 网络请求的提示弹窗Dialog
 */
public abstract class XBaseDialog {


    private ViewGroup decorView;
    private ViewGroup rootView;

    /**
     * 当前弹窗是否显示
     */
    private boolean isShowDialog;


    /**
     * 显示的activity
     *
     * @return
     */
    abstract WeakReference<Activity> activity();

    /**
     * 返回的布局文件
     *
     * @return
     */
    abstract int layoutID();


    XBaseDialog() {
        initViews();
    }


    public void initViews() {
        Activity context = activity().get();
        if (context == null) return;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        decorView = context.getWindow().getDecorView().findViewById(android.R.id.content);
        rootView = (ViewGroup) layoutInflater.inflate(layoutID(), decorView, false);
        rootView.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        ));
    }

    public void showDialog() {
        if (isShowDialog) return;//在显示就结束
        isShowDialog = true;
        decorView.addView(rootView);
    }

    public void dismiss() {
        isShowDialog = false;
        decorView.removeView(rootView);
    }


    public boolean isShowDialog() {
        return isShowDialog;
    }


}

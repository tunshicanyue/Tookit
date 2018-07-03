package com.xb.toolkit.imp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * activity 的接口
 */
public interface IXDefaultActivity {

    /**
     * 布局
     *
     * @return
     */
    int layoutID();

    /**
     * 初始化控件
     *
     * @param savedInstanceState
     */
    void onCreateView(Bundle savedInstanceState);

    /**
     * 是否显示title
     *
     * @return
     */
    boolean isShowActionBar();

    /**
     * 返回Title布局id
     *
     * @return
     */
    int layoutTitleID();

    /**
     * title
     *
     * @return
     */
    View titleView();

    /**
     * 是否全屏
     *
     * @return
     */
    boolean isFullScreen();


    /**
     * 显示或者隐藏title
     */
    void changeTitle(boolean showOrHideTitle);

    /**
     * 页面跳转
     *
     * @param clazz       跳转的类
     * @param intent      数据传输
     * @param requestCode 响应码
     */
    void toActivity(Class<? extends Activity> clazz, Intent intent, int requestCode);

    void toActivity(Class<? extends Activity> clazz);

    void toActivity(Class<? extends Activity> clazz, int requestCode);

    void toActivity(Class<? extends Activity> clazz, Intent intent);


    /**
     * 全屏状态下的软键盘开关
     *
     * @param listener
     */
    void fullScreenKeyBoardListener(IKeyboardListener listener);

    /**
     * 非全屏状态下的软键盘监听
     *
     * @param rootView
     * @param listener
     */
    void unFullScreenKeyBoardListener(View rootView, IKeyboardListener listener);


}

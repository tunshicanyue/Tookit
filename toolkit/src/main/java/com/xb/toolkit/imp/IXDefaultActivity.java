package com.xb.toolkit.imp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;

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


    void initTitleView(View layoutTitleView);

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

    /**
     * 开启软键盘
     */
    void openKeyboard(View view);

    void closeKeyboard();

    /**
     * 开启全屏
     */
    void openFullScreen();

    /**
     * 提示框
     *
     * @param msg
     */
    void showToast(String msg);


    /**
     * 跳转到系统粉设置界面
     */
    void toSystemSetting();


    /**
     * 请求权限
     *
     * @param permissionType 请求权限的弹窗类型 不能为 0x1
     * @param listener       权限监听
     * @param permissions    权限
     */
    void requestPermission(int permissionType, IXRequestPermissionCallBack listener, @NonNull String... permissions);

    void requestPermission(IXRequestPermissionCallBack listener, @NonNull String... permissions);

    void requestPermission(@NonNull String... permissions);

    /**
     * 权限请求失败的提心弹窗
     *
     * @param permissionType 弹窗的消息类型
     * @param permission     权限
     */
    void showPermissionFailureDialog(int permissionType, String[] permission);

}

package com.xb.toolkit.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.xb.toolkit.R;
import com.xb.toolkit.Toolkit;
import com.xb.toolkit.imp.IKeyboardListener;
import com.xb.toolkit.imp.IXDefaultActivity;
import com.xb.toolkit.imp.IXPermissionListener;
import com.xb.toolkit.imp.IXRequestPermissionCallBack;
import com.xb.toolkit.ui.fragment.XDefaultFragment;
import com.xb.toolkit.utils.AppManager;
import com.xb.toolkit.utils.PermissionsUtil;
import com.xb.toolkit.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 基类Activity
 */
public abstract class XDefaultActivity extends AppCompatActivity implements IXDefaultActivity, IXPermissionListener {


    private ViewStub mIdTitleBar;
    private XDefaultFragment mXDefaultFragment;
    protected LinearLayout mXRoot;
    private Unbinder mUnbinder;
    private View mLayoutTitleView;
    /**
     * 是否全屏
     */
    private boolean isFullScreen = false;
    /**
     * 是否显示Title
     */
    private boolean isShowTitle = true;
    private ViewTreeObserver.OnGlobalLayoutListener mChangeListener;

    /**
     * 权限请求回调
     */
    private IXRequestPermissionCallBack mRequestPermissionCallBack;

    /**
     * 权限请求失败的弹窗类型
     */
    private int mPermissionType;

    /**
     * 跳转到系统权限设置的请求码
     */
    public static int REQUEST_SETTING = 0x101;
    /**
     * 其他权限没有指定的类型
     */
    public static final int PERMISSION_TYPE_OTHER = 0x1;
    private InputMethodManager imm;
    private FrameLayout mFl_layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.activity_x_default);
        mIdTitleBar = findViewById(R.id.id_title_bar);
        mFl_layout = findViewById(R.id.fl_layout);
        mXRoot = findViewById(R.id.x_root);
        addMainFragment();
    }


    private void addMainFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        mXDefaultFragment = new XDefaultFragment();
        mXDefaultFragment.setLayoutId(layoutID());
        mXDefaultFragment.setActivityCreateListener(savedInstanceState -> {
            mUnbinder = ButterKnife.bind(this);
            if (!isFullScreen() && isShowActionBar() && mIdTitleBar.getParent() != null) {
                mIdTitleBar.setLayoutResource(layoutTitleID());
                mLayoutTitleView = mIdTitleBar.inflate();
                initTitleView(mLayoutTitleView);
            }
            XDefaultActivity.this.onCreateView(savedInstanceState);
            bindListener();
        });
        transaction.replace(R.id.fl_layout, mXDefaultFragment);
        transaction.commitAllowingStateLoss();
    }


    @Override
    public void bindListener() {

    }

    private List<View> mLayoutID = new ArrayList<>();


    public void addDataEmpty(@LayoutRes int layoutID) {
        View view = LayoutInflater.from(this).inflate(layoutID, null);
        mLayoutID.add(view);
        mFl_layout.addView(view);
        view.setVisibility(View.VISIBLE);
        for (int i = 0; i < mFl_layout.getChildCount(); i++) {
            View childAt = mFl_layout.getChildAt(i);
            if (childAt != view) {
                childAt.setVisibility(View.GONE);
            } else {
                childAt.setVisibility(View.VISIBLE);
            }
        }
    }

    public void showLayout(@LayoutRes int layoutID) {
        for (int i = 0; i < mFl_layout.getChildCount(); i++) {

        }
    }


    public void updateLayout(@LayoutRes int layoutID) {
        View view = View.inflate(this, layoutID, null);
        mFl_layout.addView(view);
    }


    @Override
    public boolean isFullScreen() {
        return isFullScreen;
    }

    /**
     * 设置是否全屏
     *
     * @param fullScreen
     */
    public void setFullScreen(boolean fullScreen) {
        isFullScreen = fullScreen;
        //全屏隐藏Title
        if (isFullScreen && mLayoutTitleView != null) {
            mLayoutTitleView.setVisibility(View.GONE);
        }
        //全屏的时候隐藏title
        if (!isFullScreen) changeTitle(isShowTitle);
        //是否全屏
        if (isFullScreen()) {
            openFullScreen();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().removeActivity(this);
        if (mUnbinder != null) mUnbinder.unbind();
        if (mChangeListener != null)
            mXRoot.getViewTreeObserver().removeOnGlobalLayoutListener(mChangeListener);
    }

    @Override
    public void changeTitle(boolean showOrHideTitle) {
        isShowTitle = showOrHideTitle;
        if (mLayoutTitleView != null) {
            mLayoutTitleView.setVisibility(showOrHideTitle ? View.VISIBLE : View.GONE);
        } else {
            // 显示Title 但页面没有被加载
            if (isShowTitle) {
                mIdTitleBar.setLayoutResource(layoutTitleID());
                mIdTitleBar.inflate();
            }
        }
    }

    @Override
    public void toActivity(Class<? extends Activity> clazz) {
        toActivity(clazz, null, -1);
    }

    @Override
    public void toActivity(Class<? extends Activity> clazz, Intent intent) {
        toActivity(clazz, intent, -1);
    }

    @Override
    public void toActivity(Class<? extends Activity> clazz, Intent intent, int requestCode) {
        if (clazz == null) return;
        if (intent == null) intent = new Intent(this, clazz);
        if (requestCode == -1) startActivity(intent);
        else startActivityForResult(intent, requestCode);
    }

    @Override
    public void toActivity(Class<? extends Activity> clazz, int requestCode) {
        toActivity(clazz, null, requestCode);
    }

    /*软键盘开关*/
    @Override
    public void fullScreenKeyBoardListener(IKeyboardListener listener) {
        if (listener == null) return;
        //        stateAlwaysHidden|adjustResize
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                | WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        if (mChangeListener == null) {
            mChangeListener = () -> {
                if (ScreenUtils.isKeyboardShown(mXRoot)) {
                    listener.openKeyboard();
                } else {
                    listener.closeKeyboard();
                }
            };

        }
        mXRoot.getViewTreeObserver().removeOnGlobalLayoutListener(mChangeListener);
        mXRoot.getViewTreeObserver().addOnGlobalLayoutListener(mChangeListener);
    }

    @Override
    public void unFullScreenKeyBoardListener(View rootView, IKeyboardListener listener) {
        if (rootView == null || listener == null) return;
        //必须设置 不然无法监听 adjustResize
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        int keyHeight = ScreenUtils.getScreenHeight(Toolkit.getToolkit().getApplication()) / 3;
        rootView.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
            //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
            if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
                //键盘打开
                listener.openKeyboard();
            } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
                listener.closeKeyboard();
            }
        });
    }


    @Override
    public void openKeyboard(View view) {
        if (view == null) return;
        if (imm == null) {
            imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        view.postDelayed(() -> imm.showSoftInput(view, InputMethodManager.SHOW_FORCED), 50);
    }

    @Override
    public void closeKeyboard() {
        if (imm == null) {
            imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }

    }

    @Override
    public void openFullScreen() {
        // TODO: 2018/7/5 开启全屏模式
//        if (Build.VERSION.SDK_INT >= 19) {
//            View decorView = getWindow().getDecorView();
//            //隐藏状态栏
//            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
//            decorView.setSystemUiVisibility(uiOptions);
//            //记住如果您隐藏状态栏绝不要显示活动栏，所以隐藏它也是必要的。
//            ActionBar actionBar = getActionBar();
//            if (actionBar != null) {
//                actionBar.hide();
//            }
//        } else {
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
//            decorView.setSystemUiVisibility(option);
//        }
    }

    @Override
    public void showToast(String msg) {
        if (TextUtils.isEmpty(msg)) return;
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toSystemSetting() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, REQUEST_SETTING);
    }


    /*start权限*/
    @Override
    public final void permissionDenied(@NonNull String[] permission) {
        // 权限请求失败
        boolean hasMultDenied = false;
        for (String p : permission) {
            hasMultDenied = !ActivityCompat.shouldShowRequestPermissionRationale(this, p);
            if (hasMultDenied) {
                break;
            }
        }
        if (hasMultDenied) {
            showPermissionFailureDialog(mPermissionType, permission);
        } else {
            if (mRequestPermissionCallBack != null) {
                mRequestPermissionCallBack.permissionDenied(permission);
            }
        }
    }

    @Override
    public final void permissionGranted(@NonNull String[] permission) {
        //权限请求成功
        if (mRequestPermissionCallBack != null) {
            mRequestPermissionCallBack.permissionDenied(permission);
        }
    }


    @Override
    public final void requestPermission(int permissionType, IXRequestPermissionCallBack listener, @NonNull String... permissions) {
        mPermissionType = permissionType;
        mRequestPermissionCallBack = listener;
        // 检查是否有权限
        if (PermissionsUtil.hasPermission(this, permissions)) {
            if (listener != null) {
                listener.permissionGranted(permissions);
            } else {
                permissionGranted(permissions);
            }
        } else {
            // 请求权限
            PermissionsUtil.requestPermission(this, this, permissions, false, null);
        }
    }


    @Override
    public void requestPermission(IXRequestPermissionCallBack listener, @NonNull String... permissions) {
        requestPermission(PERMISSION_TYPE_OTHER, listener, permissions);
    }

    @Override
    public void requestPermission(@NonNull String... permissions) {
        requestPermission(PERMISSION_TYPE_OTHER, null, permissions);
    }
}

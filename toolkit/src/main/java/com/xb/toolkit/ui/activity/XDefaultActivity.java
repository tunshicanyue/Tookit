package com.xb.toolkit.ui.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.xb.toolkit.R;
import com.xb.toolkit.Toolkit;
import com.xb.toolkit.imp.IKeyboardListener;
import com.xb.toolkit.imp.IXDefaultActivity;
import com.xb.toolkit.ui.fragment.XDefaultFragment;
import com.xb.toolkit.utils.ScreenUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 基类Activity
 */
public abstract class XDefaultActivity extends AppCompatActivity implements IXDefaultActivity {


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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_x_default);
        mIdTitleBar = findViewById(R.id.id_title_bar);
        mXRoot = findViewById(R.id.x_root);
        addMainFragment();
    }


    private void addMainFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        mXDefaultFragment = new XDefaultFragment();
        mXDefaultFragment.setLayoutId(layoutID());
        mXDefaultFragment.setActivityCreateListener(savedInstanceState -> {
            mUnbinder = ButterKnife.bind(XDefaultActivity.this);
            if (!isFullScreen() && isShowActionBar() && mIdTitleBar.getParent() != null) {
                mIdTitleBar.setLayoutResource(layoutTitleID());
                mLayoutTitleView = mIdTitleBar.inflate();
            }
            XDefaultActivity.this.onCreateView(savedInstanceState);
        });
        transaction.replace(R.id.fl_layout, mXDefaultFragment);
        transaction.commitAllowingStateLoss();
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
        if (mUnbinder != null) mUnbinder.unbind();
        if (mChangeListener != null)
            mXRoot.getViewTreeObserver().removeOnGlobalLayoutListener(mChangeListener);
    }

    @Override
    public View titleView() {
        return mLayoutTitleView;
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
    public void openKeyboard(EditText editText) {
        if (editText == null) return;
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    @Override
    public void closeKeyboard(EditText editText) {
        if (editText == null) return;
        InputMethodManager imm = (InputMethodManager) this
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
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
}

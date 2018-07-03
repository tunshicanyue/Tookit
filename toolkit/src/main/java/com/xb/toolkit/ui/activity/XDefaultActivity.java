package com.xb.toolkit.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

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
    private boolean isFullScreen = false;
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
        if (isFullScreen) changeTitle(false);
        else changeTitle(isShowTitle);
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
}

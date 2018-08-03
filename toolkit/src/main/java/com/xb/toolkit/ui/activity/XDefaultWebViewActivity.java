package com.xb.toolkit.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.xb.toolkit.R;
import com.xb.toolkit.utils.ScreenUtils;

import butterknife.BindView;

public class XDefaultWebViewActivity extends XDefaultTitleActivity {


    /**
     * webView要加载的连接地址
     */
    public static final String WEB_URL = "web_url";

    /**
     * WebView的title
     * 可以不传 直接读取html中的title
     * 传递了使用传递的title
     */
    public static final String WEB_TITLE = "web_title";

    /**
     * 加载过程中的进度条颜色
     * 不传则没有
     */
    public static final String PROGRESS_COLOR = "progress_color";
    private String mWebUrl;
    private String mTitle;
    WebView mIdWebView;
    private int mProgressColor = -1;
    private View mWebLoadProgressView;

    @Override
    public int layoutID() {
        return R.layout.activity_xdefault_web_view;
    }

    @Override
    public void onCreateView(Bundle savedInstanceState) {
        Intent intent = getIntent();
        mWebUrl = intent.getStringExtra(WEB_URL);
        mTitle = intent.getStringExtra(WEB_TITLE);
        mProgressColor = intent.getIntExtra(PROGRESS_COLOR, mProgressColor);
        if (TextUtils.isEmpty(mWebUrl)
                || mWebUrl.contains("http://")
                || mWebUrl.contains("https://")) {
            showToast(getString(R.string.url_empty));
            finish();
            return;
        }
        setActionBarTitle(mTitle);
        initWebView();
    }

    private void initWebView() {
        mIdWebView = findViewById(R.id.id_web);
        mWebLoadProgressView = findViewById(R.id.web_load_progress_view);
        WebSettings webSettings = mIdWebView.getSettings();
        // 设置允许JS弹窗
        webSettings.setLoadWithOverviewMode(true);
        webSettings.supportMultipleWindows();
        webSettings.setJavaScriptEnabled(true); // 支持js
        webSettings.setDisplayZoomControls(false);

    }

    @Override
    public void bindListener() {
        mIdWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (mWebLoadProgressView != null) {
                    if (mProgressColor == -1) {
                        mWebLoadProgressView.setVisibility(View.GONE);
                        return;
                    }
                    mWebLoadProgressView.setVisibility(View.VISIBLE);
                    mWebLoadProgressView.setBackgroundColor(getResources().getColor(mProgressColor));
                    int newWidth = (int) (ScreenUtils.getScreenWidth(XDefaultWebViewActivity.this) * ((double) newProgress / 100));
                    mWebLoadProgressView.getLayoutParams().width = newWidth;
                    mWebLoadProgressView.requestLayout();
                    if (newProgress == 100) {
                        mWebLoadProgressView.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (TextUtils.isEmpty(mTitle)) {
                    setActionBarTitle(title);
                }
            }
        });
        mIdWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mIdWebView.loadUrl(mWebUrl);
    }

    @Override
    public void showPermissionFailureDialog(int permissionType, String[] permission) {

    }

    @Override
    protected void initActionBar() {

    }

    @Override
    public void actionBarLeftClick(View v) {
        if (mIdWebView.canGoBack()) {
            mIdWebView.goBack();
        } else {
            super.actionBarLeftClick(v);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mIdWebView.canGoBack()) {
                mIdWebView.goBack();//返回上一页面
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}

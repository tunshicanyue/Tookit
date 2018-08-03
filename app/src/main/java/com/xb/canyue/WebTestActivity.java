package com.xb.canyue;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import com.google.gson.Gson;
import com.xb.toolkit.utils.LogUtils;
public class WebTestActivity extends AppCompatActivity {


    private WebView mWb_view;
    private Button mTextView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_test);
        mWb_view = findViewById(R.id.wb_view);
        mTextView = findViewById(R.id.text);
        initWebView();
    }

    @SuppressLint("JavascriptInterface")
    public void initWebView() {
        WebSettings settings = mWb_view.getSettings();
        settings.setJavaScriptEnabled(true);

//        // 设置允许JS弹窗
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWb_view.addJavascriptInterface(this, "local_obj");
        WebView.setWebContentsDebuggingEnabled(true);
        settings.setBuiltInZoomControls(false);
        settings.setSupportZoom(false);
        settings.setDisplayZoomControls(false);
        settings.setDomStorageEnabled(true);
        mWb_view.requestFocus();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAppCacheEnabled(false);//是否使用缓存
        mWb_view.setWebViewClient(new WebViewClient());
        mWb_view.setWebChromeClient(new WebChromeClient());

        mWb_view.loadUrl("http://192.168.6.179/index.html");
        WebTestBean webTestBean = new Gson().fromJson("", WebTestBean.class);
        String toJson = new Gson().toJson(webTestBean);
        StringBuffer sb = new StringBuffer();
        sb.append("javascript:load_data(");
        sb.append(toJson);
        sb.append(")");
       // String replaceAll = sb.toString().replaceAll(" ", "").replaceAll("\"", "");
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWb_view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LogUtils.e("调用数据");
                        mWb_view.evaluateJavascript(sb.toString(), value -> {
                        });
                    }
                }, 500);
            }
        });

    }

    //  String html = "{\"code\":\"200\",\"message\":\"成功\",\"data\":{\"introduction\":[\"6666666\",\"777777\",\"888888\"],\"items\":[{\"name\":\"11\",\"marketing_campaign_id\":\"3\",\"from\":\"1\",\"from_relate_id\":\"1\",\"number\":\"101\",\"condition\":\"2\",\"image\":\"http:\\/\\/182.150.20.24:10012\\/zhph_commonServices\\/webservice\\/hdfs\\/showFile\\/zhph\\/zhh\\/php\\/test\\/201805\\/\\/d043a106ed9d477c9ece3329e3055b3b.jpeg\",\"updated_at\":\"2018-07-23 09:55:10\",\"created_at\":\"2018-07-20 09:57:51\",\"item_id\":\"11\"},{\"name\":\"22\",\"marketing_campaign_id\":\"3\",\"from\":\"2\",\"from_relate_id\":\"1\",\"number\":\"1230\",\"condition\":\"3\",\"image\":\"http:\\/\\/182.150.20.24:10012\\/zhph_commonServices\\/webservice\\/hdfs\\/showFile\\/zhph\\/zhh\\/php\\/test\\/201805\\/\\/d043a106ed9d477c9ece3329e3055b3b.jpeg\",\"updated_at\":\"2018-07-23 09:55:10\",\"created_at\":\"2018-07-20 09:57:51\",\"item_id\":\"12\"},{\"name\":\"33\",\"marketing_campaign_id\":\"3\",\"from\":\"2\",\"from_relate_id\":\"1\",\"number\":\"101\",\"condition\":\"1\",\"image\":\"http:\\/\\/182.150.20.24:10012\\/zhph_commonServices\\/webservice\\/hdfs\\/showFile\\/zhph\\/zhh\\/php\\/test\\/201805\\/\\/d043a106ed9d477c9ece3329e3055b3b.jpeg\",\"updated_at\":\"2018-07-23 09:55:10\",\"created_at\":\"2018-07-20 09:57:51\",\"item_id\":\"13\"},{\"name\":\"44\",\"marketing_campaign_id\":\"3\",\"from\":\"3\",\"from_relate_id\":\"0\",\"number\":\"500\",\"condition\":\"2\",\"image\":\"http:\\/\\/182.150.20.24:10012\\/zhph_commonServices\\/webservice\\/hdfs\\/showFile\\/zhph\\/zhh\\/php\\/test\\/201805\\/\\/d043a106ed9d477c9ece3329e3055b3b.jpeg\",\"updated_at\":\"2018-07-23 09:55:10\",\"created_at\":\"2018-07-20 09:57:51\",\"item_id\":\"14\"},{\"name\":\"55\",\"marketing_campaign_id\":\"3\",\"from\":\"1\",\"from_relate_id\":\"1\",\"number\":\"400\",\"condition\":\"1\",\"image\":\"http:\\/\\/182.150.20.24:10012\\/zhph_commonServices\\/webservice\\/hdfs\\/showFile\\/zhph\\/zhh\\/php\\/test\\/201805\\/\\/d043a106ed9d477c9ece3329e3055b3b.jpeg\",\"updated_at\":\"2018-07-23 09:55:11\",\"created_at\":\"2018-07-20 09:57:51\",\"item_id\":\"15\"},{\"name\":\"66\",\"marketing_campaign_id\":\"3\",\"from\":\"2\",\"from_relate_id\":\"1\",\"number\":\"600\",\"condition\":\"2\",\"image\":\"http:\\/\\/182.150.20.24:10012\\/zhph_commonServices\\/webservice\\/hdfs\\/showFile\\/zhph\\/zhh\\/php\\/test\\/201805\\/\\/d043a106ed9d477c9ece3329e3055b3b.jpeg\",\"updated_at\":\"2018-07-23 09:55:11\",\"created_at\":\"2018-07-20 09:57:51\",\"item_id\":\"16\"}],\"chance\":\"0\",\"reason\":\"1\",\"history\":[{\"user\":\"132****5766\",\"award\":\"33\"},{\"user\":\"136****6151\",\"award\":\"33\"},{\"user\":\"156****7345\",\"award\":\"22\"},{\"user\":\"136****3862\",\"award\":\"66\"},{\"user\":\"182****8847\",\"award\":\"33\"},{\"user\":\"134****6816\",\"award\":\"33\"},{\"user\":\"183****7746\",\"award\":\"33\"},{\"user\":\"187****1368\",\"award\":\"55\"},{\"user\":\"151****1300\",\"award\":\"11\"},{\"user\":\"183****7262\",\"award\":\"66\"},{\"user\":\"132****9050\",\"award\":\"55\"},{\"user\":\"131****3299\",\"award\":\"22\"},{\"user\":\"132****2553\",\"award\":\"66\"},{\"user\":\"180****1522\",\"award\":\"22\"},{\"user\":\"186****655\"},{\"user\":\"150****9010\",\"award\":\"33\"},{\"user\":\"132****9997\",\"award\":\"66\"},{\"user\":\"150****1732\",\"award\":\"22\"},{\"user\":\"135****2545\",\"award\":\"55\"},{\"user\":\"134****9806\",\"award\":\"33\"},{\"user\":\"138****2670\",\"award\":\"55\"},{\"user\":\"138****5602\",\"award\":\"66\"},{\"user\":\"131****8727\",\"award\":\"55\"},{\"user\":\"153****4056\",\"award\":\"22\"},{\"user\":\"183****5671\",\"award\":\"33\"},{\"user\":\"135****1115\",\"award\":\"66\"},{\"user\":\"189****9513\",\"award\":\"55\"},{\"user\":\"159****3118\",\"award\":\"55\"},{\"user\":\"153****2521\",\"award\":\"66\"},{\"user\":\"151****1982\",\"award\":\"11\"},{\"user\":\"137****8429\",\"award\":\"33\"},{\"user\":\"136****6700\",\"award\":\"11\"},{\"user\":\"155****6630\",\"award\":\"33\"},{\"user\":\"186****6254\",\"award\":\"22\"},{\"user\":\"151****1526\",\"award\":\"66\"},{\"user\":\"150****3219\",\"award\":\"33\"},{\"user\":\"137****4532\",\"award\":\"11\"},{\"user\":\"182****4916\",\"award\":\"33\"},{\"user\":\"186****5151\",\"award\":\"11\"},{\"user\":\"159****1871\",\"award\":\"66\"},{\"user\":\"186****8220\",\"award\":\"33\"},{\"user\":\"156****7651\",\"award\":\"55\"},{\"user\":\"150****8384\",\"award\":\"11\"},{\"user\":\"137****8992\",\"award\":\"22\"},{\"user\":\"158****7451\",\"award\":\"55\"},{\"user\":\"182****8867\",\"award\":\"55\"},{\"user\":\"131****8125\",\"award\":\"66\"},{\"user\":\"185****7528\",\"award\":\"66\"},{\"user\":\"132****5431\",\"award\":\"33\"},{\"user\":\"139****1619\",\"award\":\"33\"},{\"user\":\"137****5040\",\"award\":\"33\"},{\"user\":\"139****3811\",\"award\":\"22\"},{\"user\":\"135****6455\",\"award\":\"55\"},{\"user\":\"183****1034\",\"award\":\"55\"},{\"user\":\"130****5775\",\"award\":\"33\"},{\"user\":\"182****3335\",\"award\":\"55\"},{\"user\":\"156****7822\",\"award\":\"22\"},{\"user\":\"186****4497\",\"award\":\"22\"},{\"user\":\"130****4598\",\"award\":\"55\"},{\"user\":\"155****2871\",\"award\":\"55\"},{\"user\":\"133****9617\",\"award\":\"11\"},{\"user\":\"181****8522\",\"award\":\"55\"},{\"user\":\"187****2074\",\"award\":\"22\"},{\"user\":\"152****9797\",\"award\":\"22\"},{\"user\":\"158****8256\",\"award\":\"66\"},{\"user\":\"189****2729\",\"award\":\"33\"},{\"user\":\"159****7882\",\"award\":\"11\"},{\"user\":\"132****3562\",\"award\":\"55\"},{\"user\":\"186****4592\",\"award\":\"33\"},{\"user\":\"134****9277\",\"award\":\"33\"},{\"user\":\"152****4916\",\"award\":\"55\"},{\"user\":\"133****4513\",\"award\":\"66\"}],\"addition\":{}}}\n" + "                                               ";
}

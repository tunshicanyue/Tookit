package com.xb.canyue;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.xb.toolkit.Toolkit;
import com.xb.toolkit.http.XHttpProxy;
import com.xb.toolkit.http.imp.XOnResultListener;
import com.xb.toolkit.ui.widgets.alertview.AlertView;
import com.xb.toolkit.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    static final String TAG = "MainActivity";


    @BindView(R.id.id_btn_zoom)
    Button idBtnZoom;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

//        UITools.setOnClickListener(this,idBtnZoom);
        idBtnZoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testHttp();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_btn_zoom:
                testHttp();
                break;
        }
    }

    private void testHttp() {
        ApiTestService apiTestService = XHttpProxy.create(ApiTestService.class);
        new XHttpProxy
                .Builder()
                .setApiService(apiTestService.refreshToken("value"))
                .setClazz(RefreshTokenBean.class)
                .setShowDialog(true)
                .setContext(this)
                .setXOnResultListener(new XOnResultListener<RefreshTokenBean>() {
                    @Override
                    public void onRequestSuccess(RefreshTokenBean refreshTokenBean) {
                        LogUtils.i(refreshTokenBean.toString());
                    }

                    @Override
                    public void onRequestFailed(String failedMsg, XHttpProxy.RequestType requestType) {
                        LogUtils.i(failedMsg);
                    }
                })
                .request();

    }

    public void dialog(View view) {
        new AlertView.Builder()
                .setContext(this)
                .setMessage("message阿萨德翁发顺丰按时发生发收款方拉开发商发放")
                .setCancelText("取消")
                .setTitle("提示")
                .setDestructive(new String[]{"确定"})
                .setStyle(AlertView.Style.Alert)
                .setOnItemClickListener((o, position) -> {
                    Toast.makeText(MainActivity.this, "点击事件", Toast.LENGTH_SHORT).show();
                })
                .build().show();
    }
}

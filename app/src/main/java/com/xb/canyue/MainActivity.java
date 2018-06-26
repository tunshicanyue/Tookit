package com.xb.canyue;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.xb.toolkit.http.XHttpProxy;
import com.xb.toolkit.http.imp.XOnResultListener;
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
        XHttpProxy.init().sendHttp(
                new XHttpProxy.Builder().setApiService(apiTestService.refreshToken())
                        .setClazz(RefreshTokenBean.class)
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

        );
    }
}

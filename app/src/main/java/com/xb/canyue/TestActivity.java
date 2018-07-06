package com.xb.canyue;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.xb.toolkit.imp.IKeyboardListener;
import com.xb.toolkit.imp.IXRequestPermissionCallBack;
import com.xb.toolkit.ui.activity.XDefaultActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestActivity extends XDefaultActivity {
    @BindView(R.id.root)
    LinearLayout mRoot;

    public static final String TAG = "XDefaultActivity";
    @BindView(R.id.et_text)
    EditText mEtText;

    @Override
    public int layoutID() {
        return R.layout.activity_test;
    }

    @Override
    public void onCreateView(Bundle savedInstanceState) {
        unFullScreenKeyBoardListener(mRoot, new IKeyboardListener() {
            @Override
            public void openKeyboard() {
                Log.i(TAG, "openKeyboard: ");
            }

            @Override
            public void closeKeyboard() {

                Log.i(TAG, "closeKeyboard: ");
            }
        });
    }

    @Override
    public boolean isShowActionBar() {
        return true;
    }

    @Override
    public int layoutTitleID() {
        return R.layout.x_item_title_bar;
    }

    @Override
    public boolean isFullScreen() {
        return false;
    }

    @Override
    public void showPermissionFailureDialog(int permissionType, String[] permission) {
        Toast.makeText(this, "showPermissionFailureDialog", Toast.LENGTH_SHORT).show();
    }

    int count = 0;

    public void dialog(View view) {

        requestPermission(new IXRequestPermissionCallBack() {
            @Override
            public void permissionGranted(@NonNull String[] permission) {

                Log.i(TAG, "permissionGranted: ");
            }

            @Override
            public void permissionDenied(@NonNull String[] permission) {
                Log.i(TAG, "permissionDenied: ");
            }
        }, Manifest.permission.CALL_PHONE);
    }


}

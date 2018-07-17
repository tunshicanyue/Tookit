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
import com.xb.toolkit.ui.activity.XDefaultTitleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestActivity extends XDefaultTitleActivity {
    @Override
    public void onCreateView(Bundle savedInstanceState) {

    }

    @Override
    public void showPermissionFailureDialog(int permissionType, String[] permission) {

    }


    @Override
    public void initTitleView(View view) {
        super.initTitleView(view);
    }

    @Override
    public int layoutID() {
        return R.layout.activity_test;
    }

    @Override
    public void initActionBar() {
//        addActionBarRightText(R.id.id_right_test, R.string.title_back);
//        addActionBarRightDrawable(R.id.id_right_test22, R.drawable.ic_clear_black_24dp);
//        addActionBarRightDrawable(R.id.id_right_test3, R.drawable.ic_clear_black_24dp);
        showActionBarSearch("搜索", true);
        setActionBarLeftDrawable(R.drawable.ic_chevron_left_black_24dp,"返回");
        showActionLeftHide(false);
    }

    @Override
    public void actionBarRightClick(View view) {
        switch (view.getId()) {
            case R.id.id_right_new:
                removeActionBarRightItem(view.getId());
                break;
            case R.id.id_right_test22:
//                changeActionBarRightDrawable(R.id.id_right_test3,R.id.id_right_test3,R.mipmap.ic_launcher);
                showActionBarSearch("搜索", true);
                break;
        }
    }

    public void dialog(View view) {
        changeActionBarRightItemText(R.id.id_right_new, R.id.id_right_test, R.string.app_name);
    }
}

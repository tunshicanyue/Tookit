package com.xb.canyue;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xb.toolkit.http.rotfit.XOkHttpProxy;
import com.xb.toolkit.ui.activity.XDefaultTitleActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestActivity extends XDefaultTitleActivity {
    @BindView(R.id.et_text)
    EditText mEtText;
    @BindView(R.id.rl_detail_hint)
    LinearLayout mRlDetailHint;
    @BindView(R.id.root)
    LinearLayout mRoot;

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
        setActionBarLeftDrawable(R.drawable.ic_chevron_left_black_24dp, "返回");
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
        addDataEmpty(R.layout.activity_main);
        XOkHttpProxy.sendHttp("http://www.7kanxiaoshuo.com/");
        ArrayList<String> introduction = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            introduction.add("测试数据" + i);
        }

        LinearLayout rl_detail_hint = (LinearLayout) findViewById(R.id.rl_detail_hint);
        for (int i = 0, len = introduction.size(); i < len; i++) {
            TextView textView = new TextView(this);
            textView.setText(introduction.get(i));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            textView.setTextColor(Color.BLACK);
//            textView.setCompoundDrawables(mActivity.getResources().getDrawable(R.drawable.hint_dot), null, null, null);
            textView.setGravity(Gravity.TOP);
//            textView.setPadding(0, DensityUtil.dp2px(mActivity, 5), 0, 0);
            rl_detail_hint.addView(textView);
        }

    }

}

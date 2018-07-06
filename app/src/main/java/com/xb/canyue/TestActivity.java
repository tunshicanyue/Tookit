package com.xb.canyue;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.xb.toolkit.imp.IKeyboardListener;
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

    int count = 0;

    public void dialog(View view) {

        count++;
//        changeTitle(count % 2 == 0);

        setFullScreen(count%2 ==0);
        if (count%2 ==0) {
            closeKeyboard(mEtText);
        } else {
            openKeyboard(mEtText);
        }
    }


}

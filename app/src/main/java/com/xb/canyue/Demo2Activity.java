package com.xb.canyue;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xb.canyue.time_line.MyAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Demo2Activity extends AppCompatActivity {


    @BindView(R.id.tablayout)
    TabLayout mTablayout;
    @BindView(R.id.list_item)
    RecyclerView mListItem;
    private Unbinder mBind;
    private BaseQuickAdapter<String, BaseViewHolder> mQuickAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo2);
        mBind = ButterKnife.bind(this);
        initRecy();
        initTabLayout(mTablayout);
    }

    private void initRecy() {

        mListItem.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mQuickAdapter = new BaseQuickAdapter<String, BaseViewHolder>(android.R.layout.simple_list_item_1) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(android.R.id.text1, item);
            }
        };
        mListItem.setAdapter(mQuickAdapter);
        mQuickAdapter.addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_headre, null));
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            strings.add("测试数据" + i);
        }
        mQuickAdapter.setNewData(strings);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
    }


    /**
     * 初始化TabView
     *
     * @param tl_layout
     */
    public void initTabLayout(TabLayout tl_layout) {
        if (tl_layout == null) return;
        String tableTexts[] = {"更多借款", "额度高", "下款快", "利息低"};
        for (int i = 0; i < tableTexts.length; i++) {

            TextView textView = new TextView(this);
            textView.setText(tableTexts[i]);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            textView.setTextColor(Color.parseColor("#333333"));
            textView.setGravity(Gravity.CENTER);
            TextPaint paint = textView.getPaint();
            paint.setFakeBoldText(i == 0);
            tl_layout.addTab(tl_layout.newTab().setCustomView(textView));
        }
        tl_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                TextView customView = (TextView) tab.getCustomView();
                TextPaint paint = customView.getPaint();
                if (paint != null) paint.setFakeBoldText(true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView customView = (TextView) tab.getCustomView();
                TextPaint paint = customView.getPaint();
                if (paint != null) paint.setFakeBoldText(false);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}

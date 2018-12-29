package com.xb.canyue.time_line;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

import com.xb.canyue.R;

import java.util.ArrayList;
import java.util.List;

public class TimeLineActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);


        ArrayList<MyAdapter.LineBean> lineBeans = new ArrayList<>();


        for (int i = 0; i < 20; i++) {
            lineBeans.add(new MyAdapter.LineBean("text" + i, "title" + i));
        }


        mRecyclerView = (RecyclerView) findViewById(R.id.list_item);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this));
        mRecyclerView.setAdapter(new MyAdapter(lineBeans, this));


    }



}

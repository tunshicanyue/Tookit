package com.xb.canyue.time_line;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xb.canyue.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 2018/10/30.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHodler> {

    private List<LineBean> mLineBeans;
    private Context mContext;

    public MyAdapter(List<LineBean> lineBeans, Context context) {
        mLineBeans = lineBeans;
        mContext = context;
    }

    @Override
    public ViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHodler(LayoutInflater.from(mContext).inflate(R.layout.item_recyler, null));
    }

    @Override
    public void onBindViewHolder(ViewHodler holder, int position) {
        holder.Title.setText(mLineBeans.get(position).title);
        holder.Text.setText(mLineBeans.get(position).text);
    }


    @Override
    public int getItemCount() {
        return mLineBeans.size();
    }

    class ViewHodler extends RecyclerView.ViewHolder {
        private TextView Title, Text;

        public ViewHodler(View itemView) {
            super(itemView);
            Text = itemView.findViewById(R.id.Itemtext);
            Title = itemView.findViewById(R.id.Itemtitle);
        }
    }

    static class LineBean {
        public String text;
        public String title;

        public LineBean(String text, String title) {
            this.text = text;
            this.title = title;
        }
    }
}

package com.xb.toolkit.ui.widgets.alertview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.xb.toolkit.R;

import java.util.List;

/**
 * Created by Sai on 15/8/9.
 */
public class AlertViewAdapter extends BaseAdapter {
    private List<String> mDatas;
    private List<String> mDestructive;
    private int mCustomDestructiveColor;
    private boolean mHasTitle;

    public AlertViewAdapter(List<String> datas, List<String> destructive, int customDestructiveColor, boolean hasTitle) {
        this.mDatas = datas;
        this.mDestructive = destructive;
        this.mCustomDestructiveColor = customDestructiveColor;
        this.mHasTitle = hasTitle;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String data = mDatas.get(position);
        Holder holder = null;
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view = inflater.inflate(R.layout.item_alertbutton, null);
            holder = creatHolder(view);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        holder.UpdateUI(parent.getContext(), data, position);
        return view;
    }

    public Holder creatHolder(View view) {
        return new Holder(view);
    }

    class Holder {
        private TextView tvAlert;
        private View vDiver;
        private LinearLayout itemRoot;

        public Holder(View view) {
            tvAlert = (TextView) view.findViewById(R.id.tvAlert);
            vDiver = view.findViewById(R.id.v_diver);
            itemRoot = view.findViewById(R.id.item_root);
        }

        public void UpdateUI(Context context, String data, int position) {
            if (!mHasTitle) {
                if (position == 0) {
                    vDiver.setVisibility(View.GONE);
                } else {
                    vDiver.setVisibility(View.VISIBLE);
                }
                if (mDatas.size() == 1) {
                    itemRoot.setBackgroundResource(R.drawable.bg_actionsheet_cancel);
                } else if (mDatas.size() == 2) {
                    if (position == 0) {
                        itemRoot.setBackgroundResource(R.drawable.bg_actionsheet_first);
                    } else {
                        itemRoot.setBackgroundResource(R.drawable.bg_actionsheet_last);
                    }
                } else {
                    if (position == 0) {
                        itemRoot.setBackgroundResource(R.drawable.bg_actionsheet_first);
                    } else if (position == mDatas.size() - 1) {
                        itemRoot.setBackgroundResource(R.drawable.bg_actionsheet_last);
                    } else {
                        itemRoot.setBackgroundResource(R.drawable.bg_alertbutton_none);
                    }
                }
            } else {
                vDiver.setVisibility(View.VISIBLE);
                if (mDatas.size() == 1) {
                    itemRoot.setBackgroundResource(R.drawable.bg_actionsheet_last);
                } else if (mDatas.size() == 2) {
                    if (position == 0) {
                        itemRoot.setBackgroundResource(R.drawable.bg_alertbutton_none);
                    } else {
                        itemRoot.setBackgroundResource(R.drawable.bg_actionsheet_last);
                    }
                } else {
                    if (position == mDatas.size() - 1) {
                        itemRoot.setBackgroundResource(R.drawable.bg_actionsheet_last);
                    } else {
                        itemRoot.setBackgroundResource(R.drawable.bg_alertbutton_none);
                    }
                }
            }
            tvAlert.setText(data);
            if (mDestructive != null && mDestructive.contains(data)) {
                tvAlert.setTextColor(context.getResources().getColor(mCustomDestructiveColor == 0 ? R.color.textColor_alert_button_destructive : mCustomDestructiveColor));
            } else {
                tvAlert.setTextColor(context.getResources().getColor(R.color.textColor_alert_button_others));
            }
        }
    }
}
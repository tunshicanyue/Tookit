package com.xb.toolkit.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xb.toolkit.utils.LogUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 基类fragment
 */
public class XDefaultFragment extends Fragment {

    protected String TAG;
    private int layoutId;
    private XDefaultFragment.ActivityCreateListener activityCreateListener;
    private Unbinder mUnbinder;

    @Override
    public void onStart() {
        super.onStart();
        TAG = getClass().getSimpleName();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (layoutId() != -1) layoutId = layoutId();
        if (layoutId > 0) {
            View inflate = inflater.inflate(layoutId, null);
            mUnbinder = ButterKnife.bind(this, inflate);
            return inflate;
        }
        LogUtils.e("fragment 的布局ID不能为空");
        return null;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (activityCreateListener != null) {
            activityCreateListener.onActivityCreate(savedInstanceState);
        }
    }

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    public int layoutId() {
        return -1;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null) mUnbinder.unbind();
    }

    protected int getFragmentLayoutId() {
        return layoutId;
    }

    public void setActivityCreateListener(XDefaultFragment.ActivityCreateListener activityCreateListener) {
        this.activityCreateListener = activityCreateListener;
    }

    public interface ActivityCreateListener {
        void onActivityCreate(Bundle savedInstanceState);
    }
}

package com.xb.toolkit.ui.widgets.alertview;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.annotation.ColorRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.xb.toolkit.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sai on 15/8/9.
 * 精仿iOSAlertViewController控件
 * 点击取消按钮返回 －1，其他按钮从0开始算
 */
public class AlertView {
    public enum Style {
        ActionSheet,
        Alert
    }

    private final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM
    );
    public static final int HORIZONTAL_BUTTONS_MAXCOUNT = 2;
    public static final int CANCELPOSITION = -1;//点击取消按钮返回 －1，其他按钮从0开始算

    private String title;
    private String msg;
    private String[] destructive;
    private String[] others;
    private List<String> mDestructive;
    private List<String> mOthers;
    private String cancel;
    private ArrayList<String> mDatas = new ArrayList<>();
    private int customDestructiveColor;
    private boolean mTouchEnable = true;//点击消失
    private boolean mCancelEnable = true;//点击返回消失
    private int flag = -1;//标识高亮前还是后（sheet）

    private WeakReference<Activity> mActivityWeak;
    private ViewGroup contentContainer;
    private ViewGroup decorView;//activity的根View
    private ViewGroup rootView;//AlertView 的 根View
    private ViewGroup loAlertHeader;//窗口headerView

    private Style style = Style.Alert;

    private OnDismissListener onDismissListener;
    private OnItemClickListener onItemClickListener;
    private boolean isShowing;

    private Animation outAnim;
    private Animation inAnim;
    private int gravity = Gravity.CENTER;

    public AlertView(Builder builder) {
        this.mActivityWeak = new WeakReference<>(builder.activity);
        this.style = builder.style;
        this.title = builder.title;
        this.msg = builder.msg;
        this.cancel = builder.cancel;
        this.destructive = builder.destructive;
        this.others = builder.others;
        this.onItemClickListener = builder.onItemClickListener;
        this.customDestructiveColor = builder.customDestructiveColor;
        this.mTouchEnable = builder.mTouchEnable;
        this.mCancelEnable = builder.mCancelEnable;
        this.flag = builder.flag;
        initData(title, msg, cancel, destructive, others);
        initViews();
        init();
        initEvents();
    }

    @Deprecated
    public AlertView(String title, String msg, String cancel, String[] destructive, String[] others, Activity activity, Style style, OnItemClickListener onItemClickListener) {
        this.mActivityWeak = new WeakReference<>(activity);
        if (style != null) this.style = style;
        this.onItemClickListener = onItemClickListener;

        initData(title, msg, cancel, destructive, others);
        initViews();
        init();
        initEvents();
    }

    /**
     * 获取数据
     */
    protected void initData(String title, String msg, String cancel, String[] destructive, String[] others) {
        this.title = title;
        this.msg = msg;
        if (flag != Builder.FLAG_OTHER) {
            if (destructive != null) {
                this.mDestructive = Arrays.asList(destructive);
                this.mDatas.addAll(mDestructive);
            }
            if (others != null) {
                this.mOthers = Arrays.asList(others);
                this.mDatas.addAll(mOthers);
            }
        } else {
            if (others != null) {
                this.mOthers = Arrays.asList(others);
                this.mDatas.addAll(mOthers);
            }
            if (destructive != null) {
                this.mDestructive = Arrays.asList(destructive);
                this.mDatas.addAll(mDestructive);
            }
        }
        if (cancel != null) {
            this.cancel = cancel;
            if (style == Style.Alert && mDatas.size() < HORIZONTAL_BUTTONS_MAXCOUNT) {
                this.mDatas.add(0, cancel);
            }
        }

    }

    protected void initViews() {
        Activity context = mActivityWeak.get();
        if (context == null) return;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        decorView = context.getWindow().getDecorView().findViewById(android.R.id.content);
        rootView = (ViewGroup) layoutInflater.inflate(R.layout.layout_alertview, decorView, false);
        rootView.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        ));
        contentContainer = rootView.findViewById(R.id.content_container);
        int margin_alert_left_right = 0;
        switch (style) {
            case ActionSheet:
                params.gravity = Gravity.BOTTOM;
                margin_alert_left_right = context.getResources().getDimensionPixelSize(R.dimen.margin_actionsheet_left_right);
                params.setMargins(margin_alert_left_right, 0, margin_alert_left_right, margin_alert_left_right);
                contentContainer.setLayoutParams(params);
                gravity = Gravity.BOTTOM;
                initActionSheetViews(layoutInflater);
                break;
            case Alert:
                params.gravity = Gravity.CENTER;
                margin_alert_left_right = context.getResources().getDimensionPixelSize(R.dimen.margin_alert_left_right);
                params.setMargins(margin_alert_left_right, 0, margin_alert_left_right, 0);
                contentContainer.setLayoutParams(params);
                gravity = Gravity.CENTER;
                initAlertViews(layoutInflater);
                break;
        }
    }

    protected void initHeaderView(ViewGroup viewGroup) {
        loAlertHeader = viewGroup.findViewById(R.id.loAlertHeader);
        //标题和消息
        TextView tvAlertTitle = viewGroup.findViewById(R.id.tvAlertTitle);
        TextView tvAlertMsg = viewGroup.findViewById(R.id.tvAlertMsg);
        if (title != null) {
            tvAlertTitle.setText(title);
        } else {
            tvAlertTitle.setVisibility(View.GONE);
        }
        if (msg != null) {
            tvAlertMsg.setText(msg);
        } else {
            tvAlertMsg.setVisibility(View.GONE);
        }
    }

    protected void initListView() {
        Activity context = mActivityWeak.get();
        if (context == null) return;

        ListView alertButtonListView = contentContainer.findViewById(R.id.alertButtonListView);
        //把cancel作为footerView
        if (cancel != null && style == Style.Alert) {
            View itemView = LayoutInflater.from(context).inflate(R.layout.item_alertbutton, null);
            TextView tvAlert = itemView.findViewById(R.id.tvAlert);
            tvAlert.setText(cancel);
            tvAlert.setClickable(true);
            tvAlert.setTypeface(Typeface.DEFAULT_BOLD);
            tvAlert.setTextColor(context.getResources().getColor(R.color.textColor_alert_button_cancel));
            tvAlert.setBackgroundResource(R.drawable.bg_alertbutton_bottom);
            tvAlert.setOnClickListener(new OnTextClickListener(CANCELPOSITION));
            alertButtonListView.addFooterView(itemView);
        }
        AlertViewAdapter adapter = new AlertViewAdapter(mDatas, mDestructive, customDestructiveColor, !TextUtils.isEmpty(title));
        alertButtonListView.setAdapter(adapter);
        alertButtonListView.setOnItemClickListener((adapterView, view, position, l) -> {
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(AlertView.this, position);
            dismiss();
        });
    }

    protected void initActionSheetViews(LayoutInflater layoutInflater) {
        ViewGroup viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.layout_alertview_actionsheet, contentContainer);
        initHeaderView(viewGroup);

        initListView();
        TextView tvAlertCancel = (TextView) contentContainer.findViewById(R.id.tvAlertCancel);
        if (cancel != null) {
            tvAlertCancel.setVisibility(View.VISIBLE);
            tvAlertCancel.setText(cancel);
        }
        tvAlertCancel.setOnClickListener(new OnTextClickListener(CANCELPOSITION));
    }

    protected void initAlertViews(LayoutInflater layoutInflater) {
        Activity context = mActivityWeak.get();
        if (context == null) return;

        ViewGroup viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.layout_alertview_alert, contentContainer);
        initHeaderView(viewGroup);

        int position = 0;
        //如果总数据小于等于HORIZONTAL_BUTTONS_MAXCOUNT，则是横向button
        if (mDatas.size() <= HORIZONTAL_BUTTONS_MAXCOUNT) {
            ViewStub viewStub = contentContainer.findViewById(R.id.viewStubHorizontal);
            viewStub.inflate();
            LinearLayout loAlertButtons = contentContainer.findViewById(R.id.loAlertButtons);
            for (int i = 0; i < mDatas.size(); i++) {
                //如果不是第一个按钮
                if (i != 0) {
                    //添加上按钮之间的分割线
                    View divier = new View(context);
                    divier.setBackgroundColor(context.getResources().getColor(R.color.bgColor_divier));
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) context.getResources().getDimension(R.dimen.size_divier_none), LinearLayout.LayoutParams.MATCH_PARENT);
                    loAlertButtons.addView(divier, params);
                }
                View itemView = LayoutInflater.from(context).inflate(R.layout.item_alertbutton, null);
                TextView tvAlert = itemView.findViewById(R.id.tvAlert);
                View vDiver = itemView.findViewById(R.id.v_diver);
                vDiver.setVisibility(View.GONE);
                tvAlert.setClickable(true);

                //设置点击效果
                if (mDatas.size() == 1) {
                    tvAlert.setBackgroundResource(R.drawable.bg_alertbutton_bottom);
                } else if (i == 0) {//设置最左边的按钮效果
                    tvAlert.setBackgroundResource(R.drawable.bg_alertbutton_left);
                } else if (i == mDatas.size() - 1) {//设置最右边的按钮效果
                    tvAlert.setBackgroundResource(R.drawable.bg_alertbutton_right);
                }
                String data = mDatas.get(i);
                tvAlert.setText(data);

                //取消按钮的样式
                if (data == cancel) {
//                    tvAlert.setTypeface(Typeface.DEFAULT_BOLD);
                    tvAlert.setTextColor(context.getResources().getColor(R.color.textColor_alert_button_cancel));
                    tvAlert.setOnClickListener(new OnTextClickListener(CANCELPOSITION));
                    position = position - 1;
                }
                //高亮按钮的样式
                else if (mDestructive != null && mDestructive.contains(data)) {
                    tvAlert.setTextColor(context.getResources().getColor(customDestructiveColor == 0 ? R.color.textColor_alert_button_destructive : customDestructiveColor));
                }

                tvAlert.setOnClickListener(new OnTextClickListener(position));
                position++;
                loAlertButtons.addView(itemView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            }
        } else {
            ViewStub viewStub = contentContainer.findViewById(R.id.viewStubVertical);
            viewStub.inflate();
            initListView();
        }
    }

    protected void init() {
        inAnim = getInAnimation();
        outAnim = getOutAnimation();
    }

    protected void initEvents() {

        rootView.setOnClickListener(mTouchEnable ? v -> dismiss() : null);
        rootView.setOnKeyListener(mCancelEnable ? (v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK && isShowing()) {
                dismiss();
                return true;
            }
            return false;
        } : null);
        rootView.requestFocus();
    }

    public AlertView addExtView(View extView) {
        loAlertHeader.addView(extView);
        return this;
    }

    /**
     * show的时候调用
     *
     * @param view 这个View
     */
    private void onAttached(View view) {
        isShowing = true;
        decorView.addView(view);
        contentContainer.startAnimation(inAnim);
    }

    /**
     * 添加这个View到Activity的根视图
     */
    public void show() {
        if (isShowing()) {
            return;
        }
        onAttached(rootView);
    }

    /**
     * 检测该View是不是已经添加到根视图
     *
     * @return 如果视图已经存在该View返回true
     */
    public boolean isShowing() {
        return rootView.getParent() != null && isShowing;
    }

    public void dismiss() {
        //消失动画
        outAnim.setAnimationListener(outAnimListener);
        contentContainer.startAnimation(outAnim);
    }

    public void dismissImmediately() {
        decorView.post(() -> decorView.removeView(rootView));//解决连续弹窗造成Attempt to read from field 'int android.view.View.mViewFlags' on a null object reference
        isShowing = false;
        if (onDismissListener != null) {
            onDismissListener.onDismiss(this);
        }

    }

    public Animation getInAnimation() {
        Activity context = mActivityWeak.get();
        if (context == null) return null;

        int res = AlertAnimateUtil.getAnimationResource(this.gravity, true);
        return AnimationUtils.loadAnimation(context, res);
    }

    public Animation getOutAnimation() {
        Activity context = mActivityWeak.get();
        if (context == null) return null;

        int res = AlertAnimateUtil.getAnimationResource(this.gravity, false);
        return AnimationUtils.loadAnimation(context, res);
    }

    public AlertView setOnDismissListener(OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
        return this;
    }

    class OnTextClickListener implements View.OnClickListener {

        private int position;

        public OnTextClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(AlertView.this, position);
            dismiss();
        }
    }

    private Animation.AnimationListener outAnimListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            dismissImmediately();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    /**
     * 主要用于拓展View的时候有输入框，键盘弹出则设置MarginBottom往上顶，避免输入法挡住界面
     */
    public void setMarginBottom(int marginBottom) {
        Activity context = mActivityWeak.get();
        if (context == null) return;

        int margin_alert_left_right = context.getResources().getDimensionPixelSize(R.dimen.margin_alert_left_right);
        params.setMargins(margin_alert_left_right, 0, margin_alert_left_right, marginBottom);
        contentContainer.setLayoutParams(params);
    }

    public AlertView setTouchable(boolean isCancelable) {
        View view = rootView.findViewById(R.id.outmost_container);

        if (isCancelable) {
            view.setOnTouchListener(onCancelableTouchListener);
        } else {
            view.setOnTouchListener(null);
        }
        return this;
    }

    /**
     * Called when the user touch on black overlay in order to dismiss the dialog
     */
    private final View.OnTouchListener onCancelableTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                dismiss();
            }
            return false;
        }
    };

    /**
     * Builder for arguments
     */
    public static class Builder {
        private Activity activity;
        private Style style;
        private String title;
        private String msg;
        private String cancel;
        private String[] destructive;
        private String[] others;
        private OnItemClickListener onItemClickListener;
        private int customDestructiveColor;
        private boolean mTouchEnable = true;
        private boolean mCancelEnable = true;
        /**
         * 标识高亮在前还是在后
         */
        private int flag = -1;

        public final static int FLAG_DESTRUCTIVE = 1;
        public final static int FLAG_OTHER = 2;

        public Builder() {
            flag = -1;
        }

        public Builder setContext(Activity activity) {
            this.activity = activity;
            return this;
        }

        public Builder setStyle(Style style) {
            if (style != null) {
                this.style = style;
            }
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setMessage(String msg) {
            this.msg = msg;
            return this;
        }

        public Builder setCancelText(String cancel) {
            this.cancel = cancel;
            return this;
        }

        public Builder setDestructive(String... destructive) {
            if (flag == -1) {
                flag = FLAG_DESTRUCTIVE;
            }
            this.destructive = destructive;
            return this;
        }

        public Builder setCustomDestructiveColor(@ColorRes int color) {
            this.customDestructiveColor = color;
            return this;
        }

        public Builder setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
            return this;
        }

        public Builder setOthers(String[] others) {
            if (flag == -1) {
                flag = FLAG_OTHER;
            }
            this.others = others;
            return this;
        }

        public Builder setTouchEnable(boolean touchEnable) {
            this.mTouchEnable = touchEnable;
            return this;
        }

        public Builder setCancelEnable(boolean cancelEnable) {
            this.mCancelEnable = cancelEnable;
            return this;
        }

        public AlertView build() {
            return new AlertView(this);
        }
    }
}

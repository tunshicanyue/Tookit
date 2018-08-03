package com.xb.toolkit.ui.activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xb.toolkit.R;
import com.xb.toolkit.ui.widgets.ClearEditTextView;
import com.xb.toolkit.utils.ScreenUtils;
import com.xb.toolkit.utils.UITools;

import cn.bingoogolapple.badgeview.BGABadgeImageView;
import cn.bingoogolapple.badgeview.BGABadgeTextView;
import cn.bingoogolapple.badgeview.BGABadgeViewHelper;


/**
 * 包含title的Activity基类
 */
public abstract class XDefaultTitleActivity extends XDefaultActivity {

    private TextView mTvLeftTitle;
    private RelativeLayout mTitleCenterContainer;
    private TextView mTvCenterTitle;

    private int mLeftDrawable;

    //默认中间的文本大小
    private int mDefaultCenterSize = 14;
    /*默认的文本颜色*/
    private int mDefaultActionBarTextColor = android.R.color.white;
    /*默认actionBar背景颜色*/
    private int mDefaultActionBarBgColor = android.R.color.white;
    private RelativeLayout mRightActionContainer;
    /**
     * 功能菜单view集合
     */
    private SparseArray<View> actionBarRightItems;
    private RelativeLayout mTitleBarContainer;
    private int mDefaultActionBarRightTextColor = android.R.color.background_dark;
    private int mDefaultActionBarRightTextSize = 14;
    /*title的默认高度为45 dp*/
    private int mTitleContainerHeightDp;
    private ClearEditTextView mCetSearch;
    private boolean isShowSearch = false;


    @Override
    public boolean isShowActionBar() {
        return true;
    }

    @Override
    public int layoutTitleID() {
        return R.layout.x_item_title_bar;
    }

    @Override
    public void initTitleView(View view) {
        if (view == null) return;
        //左边
        mTvLeftTitle = view.findViewById(R.id.tv_left);
        // title
        mTitleCenterContainer = view.findViewById(R.id.title_container);
        mTvCenterTitle = view.findViewById(R.id.tv_title);
        // 搜索
        // 右边
        mRightActionContainer = view.findViewById(R.id.right_action_container);
        mTitleBarContainer = view.findViewById(R.id.title_bar_container);

        //右边控件的容器
        actionBarRightItems = new SparseArray<>();
        mTitleContainerHeightDp = ScreenUtils.dip2px(this, 45);
        //初始化控件 由子类实现
        initActionBar();
        mTvLeftTitle.setOnClickListener(this::actionBarLeftClick);
    }

    /*初始化title 的默认值 start*/
    public void setDefaultActionBarBgColor(int defaultActionBarBgColor) {
        mDefaultActionBarBgColor = defaultActionBarBgColor;
    }

    public void setTitleContainerHeightDp(int titleContainerHeightDp) {
        mTitleContainerHeightDp = titleContainerHeightDp;
    }

    public void setDefaultActionBarTextColor(int defaultActionBarTextColor) {
        mDefaultActionBarTextColor = defaultActionBarTextColor;
    }

    public void setDefaultCenterSize(int defaultCenterSize) {
        mDefaultCenterSize = defaultCenterSize;
    }

    /*初始化title 的默认值 end*/

    /*search start*/
    public void showActionBarSearch(int hintTextID, boolean isHideSoftInput) {
        showActionBarSearch(getString(hintTextID), isHideSoftInput);
    }


    public void showActionBarSearch(String hintText, boolean isHideSoftInput) {
        if (mCetSearch == null)
            mCetSearch = findViewById(R.id.cet_search);
        isShowSearch = true;
        mTvCenterTitle.setVisibility(View.GONE);
        mCetSearch.setVisibility(View.VISIBLE);
        mCetSearch.setHint(hintText);
        mCetSearch.setFocusable(false);
        mCetSearch.setFocusableInTouchMode(false);
        if (isHideSoftInput) {
            mCetSearch.setImeOptions(EditorInfo.IME_ACTION_NONE);
            mCetSearch.setInputType(InputType.TYPE_NULL);
            mCetSearch.setOnClickListener(v -> onSearchClick(v, SEARCH.SEARCH_EDIT_TEXT));
            mCetSearch.setOnEditorActionListener(null);
        } else {
            mCetSearch.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
            mCetSearch.setInputType(InputType.TYPE_CLASS_TEXT);
            mCetSearch.setOnEditorActionListener((textView, i, keyEvent) -> {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    closeKeyboard();
                    onSearchClick(textView, SEARCH.SOFT_KEYBOARD_SEARCH);
                }
                return true;
            });
            mCetSearch.setOnClickListener(v -> {
                mCetSearch.setFocusable(true);
                mCetSearch.setFocusableInTouchMode(true);
                mCetSearch.requestFocus();
                openKeyboard(mCetSearch);
                onSearchClick(v, SEARCH.SEARCH_EDIT_TEXT);
            });
        }

    }

    /**
     * 清空search框文本
     */
    public void clearSearchText() {
        if (mCetSearch != null) {
            mCetSearch.setFocusable(false);
            mCetSearch.setFocusableInTouchMode(false);
            mCetSearch.setText("");
            closeKeyboard();
        }
    }

    /**
     * 给搜索框添加textchage事件
     *
     * @param listener
     */
    public void setSearchTextOnChange(TextWatcher listener) {
        if (mCetSearch != null && listener != null) {
            mCetSearch.addTextChangedListener(listener);
        }
    }

    public void removeSearchTextListener(TextWatcher listener) {
        if (mCetSearch != null && listener != null) {
            mCetSearch.removeTextChangedListener(listener);
        }
    }


    public enum SEARCH {
        /**
         * 点击软键盘的搜索
         */
        SOFT_KEYBOARD_SEARCH,
        /**
         * 点击搜索框的搜索
         */
        SEARCH_EDIT_TEXT
    }

    /**
     * search 的点击事件
     *
     * @param view
     * @param searchType
     */
    public void onSearchClick(View view, SEARCH searchType) {

    }

    /*search end*/
     /*右边的控件开始 start*/
    // 是图片
    public View addActionBarRightDrawable(int id, @DrawableRes int drawableId, boolean isAutoWidth, boolean isChangePadding) {
        BGABadgeImageView actionBar = new BGABadgeImageView(this);
        setRightActionBarMessageStyle(actionBar.getBadgeViewHelper());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(isAutoWidth ? RelativeLayout.LayoutParams.WRAP_CONTENT : getTitleContainerHeightDp(), getTitleContainerHeightDp());
        if (actionBarRightItems.size() != 0) {
            params.addRule(RelativeLayout.END_OF, getLastRightActionBarId());
        }
        if (isChangePadding) {
            actionBar.setPadding(0, 0, (int) getResources().getDimension(R.dimen.dp9), 0);
            mTitleBarContainer.setPadding(mTitleBarContainer.getPaddingLeft(), mTitleBarContainer.getPaddingTop(),
                    (int) getResources().getDimension(R.dimen.dp4), mTitleBarContainer.getPaddingBottom());
        } else {
            actionBar.setPadding(0, 0, 0, 0);
        }
        actionBar.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        actionBar.setLayoutParams(params);
        actionBar.setId(id);
        actionBar.setImageResource(drawableId);
        addActionBar(actionBar);
        actionBarRightItems.put(id, actionBar);
        return actionBar;
    }


    public View addActionBarRightDrawable(int id, @DrawableRes int drawableId) {
        return addActionBarRightDrawable(id, drawableId, true, false);
    }

    public View addActionBarRightDrawable(int id, @DrawableRes int drawableId, boolean isChangePadding) {
        return addActionBarRightDrawable(id, drawableId, true, isChangePadding);
    }

    //是文字
    public View addActionBarRightText(int id, String text, @ColorRes int textColor, int textSize, boolean autoWidth, boolean isMoreColor, boolean isBold) {
        BGABadgeTextView textView = new BGABadgeTextView(this);
        setRightActionBarMessageStyle(textView.getBadgeViewHelper());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(autoWidth ? ViewGroup.LayoutParams.WRAP_CONTENT : getTitleContainerHeightDp(), getTitleContainerHeightDp());
        if (actionBarRightItems.size() != 0) {
            params.addRule(RelativeLayout.END_OF, getLastRightActionBarId());
        }
        textView.setLayoutParams(params);
        textView.setId(id);
        textView.setText(text);
        if (isMoreColor) {
            textView.setTextColor(getResources().getColorStateList(textColor));
        } else {
            textView.setTextColor(getResources().getColor(textColor));
        }
        if (isBold) {
            textView.getPaint().setFakeBoldText(true);//加粗
        }
        textView.setTextSize(textSize);
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(ScreenUtils.dip2px(this, 5), 0, ScreenUtils.dip2px(this, 5), 0);
        addActionBar(textView);
        actionBarRightItems.put(id, textView);
        return textView;
    }

    public View addActionBarRightText(int id, @StringRes int textId, @ColorRes int textColor, int textSize, boolean isBold) {
        return addActionBarRightText(id, getString(textId), textColor, textSize, false, false, isBold);
    }

    public View addActionBarRightText(int id, @StringRes int textId) {
        return addActionBarRightText(id, textId, mDefaultActionBarRightTextColor, mDefaultActionBarRightTextSize, false);
    }

    /**
     * 切换右边菜单
     *
     * @param id
     * @param oldId
     * @param textId
     */
    public View changeActionBarRightItemText(int id, int oldId, @StringRes int textId) {
        View temp = getRightActionBar(oldId);
        if (temp != null) {
            temp.setId(id);
            if (temp instanceof BGABadgeTextView) {
                ((BGABadgeTextView) temp).setText(textId);
            }
            actionBarRightItems.remove(oldId);
            actionBarRightItems.put(id, temp);
            return temp;
        } else {
            return null;
        }
    }


    public View changeActionBarRightDrawable(int id, int oldId, @DrawableRes int drawableId) {
        if (removeActionBarRightItem(oldId))
            return addActionBarRightDrawable(id, drawableId);
        else
            return null;
    }

    /**
     * 删除item(只能从左至右删除)
     *
     * @param oldId
     * @return
     */
    public boolean removeActionBarRightItem(int oldId) {
        View temp = getRightActionBar(oldId);
        if (temp != null) {
            actionBarRightItems.remove(oldId);
            mRightActionContainer.removeView(temp);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取ActionBar(如果是纯文本返回 BGABadgeTextView，图片则返回 BGABadgeImageView)
     *
     * @param id
     * @return
     */
    public View getRightActionBar(int id) {
        return actionBarRightItems.get(id);
    }

    private int getTitleContainerHeightDp() {
        return mTitleContainerHeightDp;
    }


    private void addActionBar(View actionBar) {
        actionBar.setOnClickListener(this::actionBarRightClick);
        mRightActionContainer.addView(actionBar);
    }

    private int getLastRightActionBarId() {
        if (actionBarRightItems != null && actionBarRightItems.size() > 0) {
            View v = actionBarRightItems.valueAt(actionBarRightItems.size() - 1);
            if (v != null) {
                return v.getId();
            }
        }
        return 0;
    }

    private void setRightActionBarMessageStyle(BGABadgeViewHelper helper) {
        helper.setBadgeBgColorInt(getResources().getColor(mDefaultActionBarBgColor));
        helper.setBadgeTextColorInt(mDefaultActionBarRightTextColor);
        helper.setBadgeTextSizeSp(mDefaultActionBarRightTextSize);
    }

    /*设置actionBar的左边控件*/

    public void showActionLeftHide(boolean isShowLeft) {
        if (mTvLeftTitle == null) return;
        mTvLeftTitle.setVisibility(isShowLeft ? View.VISIBLE : View.GONE);
    }

    public void setActionBarLeftDrawable(int drawableID, String text) {
        if (mTvLeftTitle == null) return;
        showActionLeftHide(true);
        mLeftDrawable = drawableID;
        if (drawableID != 0) {
            Drawable drawable = getResources().getDrawable(drawableID);
            if (drawable != null) {
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                mTvLeftTitle.setCompoundDrawables(drawable, null, null, null);
            }
        } else {
            mTvLeftTitle.setCompoundDrawables(null, null, null, null);
        }
        if (UITools.isEmpty(text)) {
            mTvLeftTitle.setText("");
        } else {
            mTvLeftTitle.setText(text);
        }
        mTvLeftTitle.setOnClickListener(this::actionBarLeftClick);
    }

    public void setActionBarLeftDrawable(int drawableID, int textID) {
        setActionBarLeftDrawable(drawableID, getResources().getString(textID));
    }

    public void setActionBarLeftDrawable(int textID) {
        setActionBarLeftDrawable(0, getResources().getString(textID));
    }

    /*设置中间控件 文本 颜色 字体大小*/
    public void setActionBarTitle(String title, int spSize, int colorID) {
        if (mTvCenterTitle == null && isShowSearch) return;
        if (mCetSearch != null) mCetSearch.setVisibility(View.GONE);
        mTvCenterTitle.setText(UITools.isEmpty(title) ? "" : title);
        mTvCenterTitle.setTextSize(ScreenUtils.px2sp(this, spSize));
        mTvCenterTitle.setTextColor(colorID != 0 ? getResources().getColor(colorID) : Color.BLACK);
    }


    public void setActionBarTitle(int titleID) {
        setActionBarTitle(titleID != 0 ? getString(titleID) : null, mDefaultCenterSize, mDefaultActionBarTextColor);
    }

    public void setActionBarTitle(String title) {
        setActionBarTitle(title, mDefaultCenterSize, mDefaultActionBarTextColor);
    }


    /*左边的点击事件*/
    public void actionBarLeftClick(View v) {
        finish();
    }


    /*右边的点击事件*/
    public void actionBarRightClick(View v) {

    }

    /*中间控件的设置结束*/
    /*右边的控件 end*/
    protected abstract void initActionBar();


}

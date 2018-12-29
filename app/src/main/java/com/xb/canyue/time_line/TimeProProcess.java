package com.xb.canyue.time_line;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.xb.toolkit.utils.ScreenUtils;

/**
 * @author admin
 */
public class TimeProProcess extends View {

    private Paint mPaint;
    private int mRadius;
    private Rect mBound;
    private String mText = "1";

    public TimeProProcess(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(ScreenUtils.dip2px(getContext(), 10));
        mRadius = ScreenUtils.dip2px(getContext(), 10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int paddingStart = getPaddingStart() + mRadius;
        int paddingEnd = getPaddingEnd() + mRadius;
        int width = getWidth();
        int height = getHeight();
        canvas.drawLine(paddingStart, height / 2, width - paddingEnd, height / 2, mPaint);
        canvas.drawCircle(paddingEnd, height / 2, mRadius, mPaint);
        canvas.drawCircle(width - paddingEnd, height / 2, mRadius, mPaint);

        //画文字 X=控件宽度/2 - 文本宽度/2；Y=控件高度/2 + 文本宽度/2

        mBound = new Rect();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(ScreenUtils.sp2px(getContext(), 16f));
        mPaint.setColor(Color.WHITE);
        mPaint.getTextBounds(mText, 0, mText.length(), mBound);
        float startX = getWidth() / 2 - mBound.width() / 2;
        float startY = getHeight() / 2 + mBound.height() / 2;
        canvas.drawText(mText, paddingStart -mBound.width() , startY, mPaint);
        canvas.drawText(mText, width - paddingEnd-mBound.width(), startY, mPaint);


    }
}

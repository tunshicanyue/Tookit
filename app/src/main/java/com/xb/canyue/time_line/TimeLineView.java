package com.xb.canyue.time_line;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

import com.xb.canyue.R;

/**
 * Created by admin on 2018/10/30.
 */

public class TimeLineView extends View {

    private Paint mPaint;
    private Bitmap mDecodeResource;

    public TimeLineView(Context context) {
        this(context, null);
    }

    public TimeLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mPaint = new Paint();
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(10f);
        mDecodeResource = BitmapFactory.decodeResource(getResources(), R.drawable.ic_check_black_24dp,new BitmapFactory.Options());
    }

    private int mCountStep = 3;
    private int mCircleR = 10;
    private String hintText = "测试文字";

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth() - getPaddingEnd() - getPaddingStart();
        int height = getHeight() - getPaddingTop() - getPaddingBottom();
        int itemWidth = (int) width / mCountStep;
        int startIndex = (itemWidth / 2) + mCircleR + getPaddingStart();
        float v = mPaint.measureText(hintText);


        for (int i = 0; i < mCountStep; i++) {
            canvas.drawCircle(startIndex + itemWidth * i, height / 2, mCircleR, mPaint);
            canvas.drawText(hintText, (startIndex + itemWidth * i) - (v / 2), height / 2 + mCircleR + 20, mPaint);
            if (mDecodeResource!=null){
                canvas.drawBitmap(mDecodeResource, startIndex + itemWidth * i, height / 2, mPaint);
            }
        }
        mPaint.setStrokeWidth(3f);
        canvas.drawLine(startIndex, height / 2, itemWidth * (mCountStep - 1) + startIndex, height / 2, mPaint);
    }
}

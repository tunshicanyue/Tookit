package com.xb.toolkit.utils;


import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * 图片缩放
 */
public class ZoomUtils {
   static   ZoomUtils zoomUtils;
    public static ZoomUtils getInstance() {
        synchronized (ZoomUtils.class){
            if (zoomUtils == null){
                zoomUtils = new ZoomUtils();
            }
        }
        return zoomUtils;
    }



    public static Bitmap zoomImg(Bitmap bm, int newWidth , int newHeight){
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }
}

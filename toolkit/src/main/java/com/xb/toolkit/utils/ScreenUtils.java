package com.xb.toolkit.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Method;

/**
 * Created by zhangbw(biaoweizh@gmail.com) on 2017-12-18
 * Description :屏幕工具类
 */

public class ScreenUtils {
    /**
     * 获取带状态栏的屏幕截图
     *
     * @param activity 上下文
     * @return
     */
    public static Bitmap getShotScreenWithStatusBar(Activity activity) {
        int height = getScreenHeight(activity);
        int width = getScreenWidth(activity);
        Rect outRect = new Rect();
        activity.getWindow().getDecorView()
                .getWindowVisibleDisplayFrame(outRect);
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = view.getDrawingCache();
        int w = width > bitmap.getWidth() ? bitmap.getWidth() : width;
        int h = height > bitmap.getHeight() ? bitmap.getHeight() : height;
        Bitmap screenBitmap = Bitmap.createBitmap(bitmap, 0, outRect.top,
                w, h);
        bitmap.recycle();
        view.setDrawingCacheEnabled(false);
        return screenBitmap;
    }

    /**
     * 获取带状态栏的屏幕截图
     *
     * @param activity 上下文
     * @return
     */
    public static Bitmap getShopScreenWithOutStatusBar(Activity activity) {
        int height = getScreenHeight(activity);
        int width = getScreenWidth(activity);
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = view.getDrawingCache();
        int w = width > bitmap.getWidth() ? bitmap.getWidth() : width;
        int h = height > bitmap.getHeight() ? bitmap.getHeight() : height;
        Bitmap screenBitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h);
        bitmap.recycle();
        view.setDrawingCacheEnabled(false);
        return screenBitmap;
    }

    /**
     * 获得屏幕高度
     *
     * @param context 上下文
     * @return 屏幕高度
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getRealMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕宽度
     *
     * @param context 上下文
     * @return 屏幕宽度
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getRealMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    public static boolean isKeyboardShown(View rootView) {
        final int softKeyboardHeight = 100;
        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);
        DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
        int heightDiff = rootView.getBottom() - r.bottom;
        return heightDiff > softKeyboardHeight * dm.density;
    }

    /**
     * 获得状态栏的高度
     *
     * @param context 上下文
     * @return 状态栏高度
     */
    public static int getStatusHeight(Context context) {

        int statusHeight = -1;
        if (context instanceof Activity) {
            Rect frame = new Rect();
            ((Activity) context).getWindow().getDecorView()
                    .getWindowVisibleDisplayFrame(frame);
            statusHeight = frame.top;
            if (statusHeight > 0) return statusHeight;
        }
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context 上下文
     * @param dpValue dip
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param context 上下文
     * @param pxValue 像素
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取底部导航高度
     * 不能适配所有手机
     *
     * @param context 上下文
     */
    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int rid = resources.getIdentifier("config_showNavigationBar", "bool", "android");
        if (rid > 0 && resources.getBoolean(rid)) {
            int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                int NavigationBarHeight = resources.getDimensionPixelSize(resourceId); // 获取高度;
                return NavigationBarHeight;
            }
        }
        return 0;
    }

    /**
     * 检查navigationBar是否存在
     * 不能适配所有手机
     *
     * @param activity 上下文
     */
    public static boolean checkNavigationBar(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        Display display = activity.getWindowManager().getDefaultDisplay();
        display.getMetrics(dm);

        DisplayMetrics realDisplayMetrics = new DisplayMetrics();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            display.getRealMetrics(realDisplayMetrics);
        } else {
            Class c;
            try {
                c = Class.forName("android.view.Display");
                Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
                method.invoke(display, realDisplayMetrics);
            } catch (Exception e) {
                realDisplayMetrics.setToDefaults();
                e.printStackTrace();
            }
        }

        Resources rs = activity.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        boolean hasNavBarFun = false;
        if (id > 0) {
            hasNavBarFun = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavBarFun = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavBarFun = true;
            }
        } catch (Exception e) {
            hasNavBarFun = false;
        }
        return hasNavBarFun;
    }

    /**
     * 获得 ActionBar 的高度
     *
     * @param context
     * @return
     */
    public static int getActionBarHeight(Context context) {
        int result = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            TypedValue tv = new TypedValue();
            context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true);
            result = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        }
        return result;
    }


    /**
     * 调节屏幕亮度
     *
     * @param context    上下文
     * @param brightness 亮度值（-1（回归系统亮度），0~255）
     */
    public static void changeAppBrightness(Context context, int brightness) {
        Window window = ((Activity) context).getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        if (brightness == -1) {
            lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
        } else {
            lp.screenBrightness = (brightness <= 0 ? 1 : brightness) / 255f;
        }
        window.setAttributes(lp);
    }
}

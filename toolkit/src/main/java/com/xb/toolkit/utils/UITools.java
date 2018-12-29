package com.xb.toolkit.utils;

import android.app.NotificationManager;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v4.app.NotificationManagerCompat;
import android.text.TextUtils;
import android.view.View;

import com.xb.toolkit.ui.activity.XDefaultTitleActivity;


/**
 * UI的工具类
 */
public class UITools {


    /**
     * 设置空间的点击事件
     *
     * @param listener
     * @param views
     */
    public static void setOnClickListener(View.OnClickListener listener, View... views) {
        if (views == null || views.length <= 0 || listener == null) return;
        for (View v : views) {
            if (v != null) v.setOnClickListener(listener);
            else throw new RuntimeException("View is empty");
        }
    }


    /**
     * 判断字符串是否乱码
     *
     * @param str
     * @return
     */
    public static boolean isMessyCode(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            // 当从Unicode编码向某个字符集转换时，如果在该字符集中没有对应的编码，则得到0x3f（即问号字符?）
            //从其他字符集向Unicode编码转换时，如果这个二进制数在该字符集中没有标识任何的字符，则得到的结果是0xfffd
            //System.out.println("--- " + (int) c);
            if ((int) c == 0xfffd) {
                // 存在乱码
                //System.out.println("存在乱码 " + (int) c);
                return true;
            }
        }
        return false;
    }

    /**
     * 复制文本
     *
     * @param copyText
     * @param context
     */
    public static void copyText(String copyText, Context context) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setText(copyText);        // 将文本内容放到系统剪贴板里。
    }


    /**
     * 字符是否为空
     *
     * @param msg
     * @return
     */
    public static boolean isEmpty(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            return TextUtils.isEmpty(msg.trim());
        }
        return true;
    }

    /**
     * 判断用户是否开启了通知权限
     *
     * @param context
     * @return
     */
    public static boolean isOpenNotification(Context context) {
        return NotificationManagerCompat.from(context).areNotificationsEnabled();
    }

}

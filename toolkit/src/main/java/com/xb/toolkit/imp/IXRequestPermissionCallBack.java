package com.xb.toolkit.imp;

import android.support.annotation.NonNull;

/**
 * Created by admin on 2018/7/6.
 */

public interface IXRequestPermissionCallBack {
    /**
     * 同意授权
     */
    void permissionGranted(@NonNull String[] permission);

    /**
     * 取消授权
     */
    void permissionDenied(@NonNull String[] permission);
}

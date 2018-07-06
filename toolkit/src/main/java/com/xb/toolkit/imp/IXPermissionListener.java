package com.xb.toolkit.imp;

import android.support.annotation.NonNull;


/**
 * 6.0权限请求接口回调
 */
public interface IXPermissionListener {
    /**
     * 通过授权
     *
     * @param permission
     */
    void permissionGranted(@NonNull String[] permission);

    /**
     * 拒绝授权
     *
     * @param permission
     */
    void permissionDenied(@NonNull String[] permission);
}

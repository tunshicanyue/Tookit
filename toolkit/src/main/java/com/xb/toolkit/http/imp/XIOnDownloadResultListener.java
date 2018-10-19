package com.xb.toolkit.http.imp;

import java.io.File;

/**
 * 下载的监听器
 * @author admin
 */
public interface XIOnDownloadResultListener {

    /**
     * 下载进度
     *
     * @param totalSize
     * @param progressSize
     */
    void onDownloadProgress(long totalSize, int progressSize);

    /**
     * 下载失败的监听器
     *
     * @param e
     */
    void onDownloadFailure(Exception e);

    /**
     * 下载成功
     *
     * @param file
     */
    void onDownloadSuccess(File file);
}

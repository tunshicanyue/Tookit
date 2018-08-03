package com.xb.toolkit.http.rotfit;


import android.app.DownloadManager;
import android.os.Looper;

import com.xb.toolkit.http.imp.XIOnDownloadResultListener;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Handler;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 文件下载
 */

public class DownloadUtil {

    static DownloadUtil mDownloadUtil;
    static android.os.Handler mHandler;

    public static DownloadUtil getInstance() {
        synchronized (DownloadUtil.class) {
            if (mDownloadUtil == null) mDownloadUtil = new DownloadUtil();
        }
        return mDownloadUtil;
    }


    private DownloadUtil() {
        mHandler = new android.os.Handler(Looper.getMainLooper());
    }

    /**
     * 下载文件
     *
     * @param url
     * @param destFileDir
     * @param destFileName
     * @param listener
     */
    public void download(String url,
                         String destFileDir,
                         String destFileName,
                         XIOnDownloadResultListener listener) {

        Request request = new Request.Builder().url(url)
                .build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (listener != null) mHandler.post(() -> listener.onDownloadFailure(e));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                saveDownloadFile(response, destFileDir, destFileName, listener);
            }
        });
    }

    /**
     * 保存要下载的文件到本地
     *
     * @param response
     * @param destFileDir
     * @param destFileName
     * @param listener
     */
    private void saveDownloadFile(Response response,
                                  String destFileDir,
                                  String destFileName,
                                  XIOnDownloadResultListener listener) throws IOException {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        //储存下载文件的目录
        File dir = new File(destFileDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, destFileName);
        try {
            ResponseBody body = response.body();
            if (body == null) {
                if (listener != null) {
                    mHandler.post(() -> listener.onDownloadFailure(new NullPointerException()));
                }
                return;
            }
            is = body.byteStream();
            long total = body.contentLength();
            fos = new FileOutputStream(file);
            long sum = 0;
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
                sum += len;
                int progress = (int) (sum * 1.0f / total * 100);
                //下载中更新进度条
                if (listener != null) {
                    mHandler.post(() -> listener.onDownloadProgress(total, progress));
                }
            }
            fos.flush();
            //下载完成
            if (listener != null) {
                mHandler.post(() -> listener.onDownloadSuccess(file));
            }
        } catch (Exception e) {
            if (listener != null) {
                mHandler.post(() -> listener.onDownloadFailure(e));
            }
        } finally {
            closeStream(is, fos);
        }

    }

    /**
     * 关闭数据流
     *
     * @param closeable
     */
    private void closeStream(Closeable... closeable) {
        if (closeable == null || closeable.length <= 0) return;
        for (int i = 0; i < closeable.length; i++) {
            try {
                closeable[i].close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}


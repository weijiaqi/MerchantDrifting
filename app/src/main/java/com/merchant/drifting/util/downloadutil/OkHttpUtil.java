package com.merchant.drifting.util.downloadutil;


import static com.merchant.drifting.app.FilePathConstant.STAR_PATH;

import android.app.Activity;

import com.jess.arms.utils.SystemUtil;
import com.merchant.drifting.app.application.MerchantDriftingApplication;
import com.merchant.drifting.util.PermissionDialog;
import com.merchant.drifting.util.request.CloseUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpUtil {



    /**
     * 下载文件
     *
     * @param activity activity_release_scheme
     * @param url      下载链接
     * @param callback 下载监听
     */
    public static void downLoadFile(Activity activity, String url, String fileName, DownloadCallback callback) {
        PermissionDialog.requestPermissions(activity, new PermissionDialog.PermissionCallBack() {
            @Override
            public void onSuccess() {
                downloadFile(url, fileName, callback);
            }

            @Override
            public void onFailure() {
            }

            @Override
            public void onAlwaysFailure() {
                PermissionDialog.showDialog(activity, "android.permission.WRITE_EXTERNAL_STORAGE");
            }
        });
    }



    /**
     * 下载文件
     *
     * @param url      下载链接
     * @param callback 下载监听
     */
    public static Call downloadFile(String url, String fileName, DownloadCallback callback) {
        String savePath = STAR_PATH +fileName;
        return download(url, savePath, callback);
    }


    /**
     * 下载文件
     *
     * @param url      下载链接
     * @param callback 下载回调监听
     */
    public static Call download(final String url, final String savePath, final DownloadCallback callback) {
        return request(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onDownloadFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful() || response.body() == null) {
                    callback.onDownloadFailure(new Exception("Failed to download file: "
                            + response.toString()));
                }

                InputStream is = null;
                FileOutputStream fos = null;
                try {
                    byte[] buffer = new byte[2048];
                    long total = response.body().contentLength();
                    is = response.body().byteStream();
                    fos = new FileOutputStream(new File(savePath));

                    int len = 0;
                    long sum = 0;
                    while ((len = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                        sum += len;
                        callback.onDownloading(sum, total);
                    }
                    fos.flush();
                    callback.onDownloadSuccess(savePath);
                } catch (IOException e) {
                    callback.onDownloadFailure(e);
                } finally {
                    CloseUtil.close(is, fos);
                }
            }
        });
    }

    /**
     * 构建异步请求
     *
     * @param url      请求url
     * @param callback 请求回调
     * @return Call对象
     */
    private static Call request(String url, Callback callback) {
        Request.Builder builder = new Request.Builder().url(url).removeHeader("User-Agent").addHeader("User-Agent",
                SystemUtil.getUserAgent(MerchantDriftingApplication.getContext()));
        Request request = builder.build();
        Call call = new OkHttpClient.Builder().build().newCall(request);
        call.enqueue(callback);
        return call;
    }


    /**
     * 下载回调
     */
    public interface DownloadCallback {
        void onDownloadSuccess(String savePath);

        void onDownloading(long progress, long total);

        void onDownloadFailure(Exception e);
    }

}

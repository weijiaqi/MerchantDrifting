package com.merchant.drifting.util.downloadutil;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.core.content.FileProvider;


import com.merchant.drifting.app.api.ApiProxy;

import java.io.File;
import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

/**
 * App更新管理类
 */
public class UpdateManager {

    /**
     * 是否需要更新,需要则下载
     *
     * @param context context
     * @param url     版本地址
     * @param apkPath apk保存路径
     * @param cd      订阅
     */
    public static void downloadApk(final Context context, final String url, final String apkPath, final CompositeDisposable cd) {
        ApiProxy.getDownLoad().download(url)
                .map(new Function<ResponseBody, BufferedSource>() {
                    @Override
                    public BufferedSource apply(ResponseBody responseBody) throws Exception {
                        return responseBody.source();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Observer<BufferedSource>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        cd.add(d);
                    }

                    @Override
                    public void onNext(BufferedSource bufferedSource) {
                        try {
                            writeFile(bufferedSource, new File(apkPath));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        unSubscribe(cd);
                    }

                    @Override
                    public void onComplete() {
                        CheckSysVersion(context, apkPath);
                        unSubscribe(cd);
                    }
                });

    }

    /**
     * 跳转安装 apk 文件
     *
     * @param context
     * @param apkPath
     */
    private static void CheckSysVersion(Context context, String apkPath) {
        boolean installAllowed;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            installAllowed = context.getPackageManager().canRequestPackageInstalls();
            if (installAllowed) {
                installAPK(context,apkPath);
            } else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, Uri.parse("package:" + context.getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                installAPK(context,apkPath);
            }
        } else {
            installAPK(context,apkPath);
        }
    }


    public static void installAPK(Context context, String apkPath) {
        if (apkPath == null) {
            return;
        }
        File apkFile = new File(apkPath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri apkuri = null;
        //在服务器中开启flag
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addCategory("android.intent.category.DEFAULT");
            apkuri = FileProvider.getUriForFile(context.getApplicationContext(), context.getApplicationContext().getPackageName()+".fileprovider", apkFile);
        } else {
            apkuri = Uri.parse("file://" + apkFile.toString());
        }
        intent.setDataAndType(apkuri, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }



    /**
     * 写入文件
     */
    private static void writeFile(BufferedSource source, File file) throws IOException {
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();

        if (file.exists())
            file.delete();

        BufferedSink bufferedSink = Okio.buffer(Okio.sink(file));
        bufferedSink.writeAll(source);

        bufferedSink.close();
        source.close();
    }

    /**
     * 解除订阅
     *
     * @param cd 订阅关系集合
     */
    private static void unSubscribe(CompositeDisposable cd) {
        if (cd != null && !cd.isDisposed())
            cd.dispose();
    }
}

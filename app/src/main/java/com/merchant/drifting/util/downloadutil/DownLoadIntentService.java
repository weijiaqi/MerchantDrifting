package com.merchant.drifting.util.downloadutil;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;


import com.merchant.drifting.R;
import com.merchant.drifting.data.entity.DownloadEntity;
import com.merchant.drifting.data.event.UpdateProgressEvent;
import com.merchant.drifting.util.StorageUtil;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * DownLoadIntentService
 */

public class DownLoadIntentService extends IntentService {
    private static final String ACTION_DOWNLOAD = "intentservice.action.download";
    private static final String ACTION_DOWNLOAD_FILE = "intentservice.action.downloadfile";

    private static final String DOWNLOAD_URL = "downloadUrl";
    private static final String FILE_PATH = "filePath";
    private static final String TAG = "DownLoadIntentService";
    private String channelID = "1";
    private String channelName = "channel_name";

    private CompositeDisposable cd = new CompositeDisposable();
    private NotificationManager notificationManager;
    private NotificationCompat.Builder notificationBuilder;

    public static boolean isDownLoad = true;

    public DownLoadIntentService() {
        super("DownLoadIntentService");
    }

    public static void startDownLoadService(Context context, String url, String downLoadPath) {
        try {
            Intent intent = new Intent(context, DownLoadIntentService.class);
            intent.setAction(ACTION_DOWNLOAD_FILE);
            intent.putExtra(DOWNLOAD_URL, url);
            intent.putExtra(FILE_PATH, downLoadPath);
            context.startService(intent);
        }catch (Exception e){
            Log.e(TAG,"Not allowed to start service Intent ");
        }
    }

    public static void startDownLoadService(Context context, String url) {
        try {
            Intent intent = new Intent(context, DownLoadIntentService.class);
            intent.setAction(ACTION_DOWNLOAD_FILE);
            intent.putExtra(DOWNLOAD_URL, url);
            context.startService(intent);
        }catch (Exception e){
            Log.e(TAG,"Not allowed to start service Intent ");

        }
    }

    public static void startUpdateService(Context context, String versionUrl) {
        try {
            isDownLoad = false;
            Intent intent = new Intent(context, DownLoadIntentService.class);
            intent.setAction(ACTION_DOWNLOAD);
            intent.putExtra(DOWNLOAD_URL, versionUrl);
            intent.putExtra(FILE_PATH, StorageUtil.getCacheDirectory(context).getAbsolutePath()+"/"+context.getString(R.string.app_name)+".apk");
            context.startService(intent);
        }catch (Exception e){
            Log.e(TAG,"Not allowed to start service Intent ");
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            if (ACTION_DOWNLOAD.equals(action)) {
                notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel mChannel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_LOW);
                    notificationManager.createNotificationChannel(mChannel);
                }

                notificationBuilder = new NotificationCompat.Builder(this, getString(R.string.app_name))
                        .setContentTitle("开始下载")
                        .setContentText("版本更新")
                        .setTicker(getString(R.string.app_name)+"开始下载了")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.icon_logo)
                        .setChannelId(channelID);
                if (notificationManager != null) {
                    notificationManager.notify(0, notificationBuilder.build());
                }
                String url = intent.getStringExtra(DOWNLOAD_URL);
                String apkPath = intent.getStringExtra(FILE_PATH);
                handleUpdate(url, apkPath);
            }
        }
    }

    private void handleUpdate(String url, String apkPath) {
        subscribeEvent();
        UpdateManager.downloadApk(this, url, apkPath, cd);
    }

    private void subscribeEvent() {
        ThreadProxy.create().toObservable(DownloadEntity.class)
                .subscribe(new Observer<DownloadEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        cd.add(d);
                    }

                    @Override
                    public void onNext(DownloadEntity downloadBean) {
                        int progress = (int) Math.round(downloadBean.getBytesReaded() / (double) downloadBean.getTotal() * 100);

                        if (notificationBuilder != null) {
                            notificationBuilder.setContentTitle("下载中")
                                    .setContentText(String.valueOf(progress) + "%")
//                                    .setContentInfo(String.valueOf(progress) + "%")
                                    .setProgress(100, progress, false);
                            notificationManager.notify(0, notificationBuilder.build());
                            if (progress == 100) {
                                isDownLoad = true;
                                notificationManager.cancel(0);
                            }
                            EventBus.getDefault().post(new UpdateProgressEvent(progress,100,progress==100));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        subscribeEvent();
                    }

                    @Override
                    public void onComplete() {
                        subscribeEvent();
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

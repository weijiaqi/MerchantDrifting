package com.merchant.drifting.util.downloadutil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;

import androidx.core.app.NotificationCompat;


import com.merchant.drifting.R;
import com.merchant.drifting.app.application.MerchantDriftingApplication;
import com.merchant.drifting.data.event.UpdateProgressEvent;
import com.merchant.drifting.util.FileUtil;
import com.merchant.drifting.util.PermissionDialog;
import com.merchant.drifting.util.SingleMediaScanner;
import com.merchant.drifting.util.TextUtil;
import com.merchant.drifting.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DownloadRequest {
    private static DownloadRequest instance = new DownloadRequest();
    private static NotificationManager notificationManager;
    private NotificationCompat.Builder notificationBuilder;
    private List<String> downloadList = new ArrayList<>();
    private Handler mHandler;
    private String channelID = "1";
    private String channelName = "channel_name";

    private DownloadRequest() {
    }

    static {
        notificationManager = (NotificationManager) MerchantDriftingApplication.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public static DownloadRequest whith() {
        return instance;
    }


    public void downloadWithNotification(Context context, String url) {
        if (!TextUtil.isHttpUrl(url)) {
            ToastUtil.showToast("地址错误");
            return;
        }
        if (downloadList.contains(url)) {
            ToastUtil.showToast("下载任务已存在");
            return;
        }
        downloadList.add(url);
        mHandler = new Handler(context.getMainLooper());
        PermissionDialog.requestPermissions((Activity) context, new PermissionDialog.PermissionCallBack() {
            @Override
            public void onSuccess() {
                int notifyId = createID();
                String fileName = FileUtil.getUrlFileName(url);
                OkHttpUtil.downLoadFile((Activity) context, url, fileName, new OkHttpUtil.DownloadCallback() {
                    @Override
                    public void onDownloadSuccess(String savePath) {
                        if (!TextUtils.isEmpty(fileName)) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtil.showToast("下载完成" + fileName);
                                }
                            });

                            notificationManager.cancel(notifyId);
                            downloadList.remove(url);
                            new SingleMediaScanner(context, savePath, new SingleMediaScanner.ScanListener() {
                                @Override
                                public void onScanFinish() {
                                    // scanning...
                                }
                            });
                        }
                    }

                    @Override
                    public void onDownloading(long progress, long total) {
                        notificationBuilder.setContentTitle("正在下载" + fileName)
                                .setContentText(((int) (progress * 100 / total)) + "%")
                                .setProgress(100, (int) (progress * 100 / total), false);
                        notificationManager.notify(notifyId, notificationBuilder.build());

                        EventBus.getDefault().post(new UpdateProgressEvent(progress, 100, progress == 100));
                    }

                    @Override
                    public void onDownloadFailure(Exception e) {
                        downloadList.remove(url);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.showToast("下载失败:" + fileName);
                            }
                        });
                        notificationManager.cancel(notifyId);
                    }
                });
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast("开始下载:" + fileName + ",请等待....");
                    }
                });

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    @SuppressLint("WrongConstant")
                    NotificationChannel mChannel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_LOW);
                    notificationManager.createNotificationChannel(mChannel);
                }

                notificationBuilder = new NotificationCompat.Builder(context, channelID)
                        .setContentTitle("开始下载" + fileName)
                        .setContentText("下载")
                        .setTicker("开始下载")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.icon_logo)
                        .setChannelId(channelID);
                if (notificationManager != null) {
                    notificationManager.notify(notifyId, notificationBuilder.build());
                }
            }

            @Override
            public void onFailure() {
            }

            @Override
            public void onAlwaysFailure() {
                PermissionDialog.showDialog((Activity) context, "android.permission.WRITE_EXTERNAL_STORAGE");
            }
        });
    }


    private int createID() {
        Date now = new Date();
        int id = Integer.parseInt(new SimpleDateFormat("ddHHmmss", Locale.US).format(now));
        return id;
    }
}

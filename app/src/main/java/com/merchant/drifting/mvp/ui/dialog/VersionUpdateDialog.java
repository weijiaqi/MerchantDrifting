package com.merchant.drifting.mvp.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jess.arms.base.BaseDialog;
import com.merchant.drifting.R;
import com.merchant.drifting.app.receiver.NetworkUtil;
import com.merchant.drifting.data.event.UpdateProgressEvent;
import com.merchant.drifting.util.PermissionDialog;
import com.merchant.drifting.util.downloadutil.DownLoadIntentService;
import com.merchant.drifting.util.manager.NotificationManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class VersionUpdateDialog extends BaseDialog implements View.OnClickListener {
    private Context mContext;
    private TextView mTvVersionCode, mTvContent;
    private TextView mTvUpload;
    private ImageView mIvColse;
    private String mVersionUrl;
    private int mIsToUpdate;
    private String mVersion;
    private String mToUpdateContent;

    public VersionUpdateDialog(@NonNull Context context, String versionUrl, int status, String toUpdateContent, String version) {
        super(context);
        mContext = context;
        mVersionUrl = versionUrl;
        mVersion = version;
        mToUpdateContent = toUpdateContent;
        mIsToUpdate=status;
    }


    @Override
    protected int getContentView() {
        return R.layout.dialog_version_update;
    }

    @Override
    protected float getDialogWith() {
        return 1f;
    }


    @Override
    protected void initView() {
        super.initView();
        mTvVersionCode = findViewById(R.id.tv_version_code);
        mTvContent = findViewById(R.id.tv_content);
        mTvUpload = findViewById(R.id.tv_upload);
        mIvColse = findViewById(R.id.iv_colse);
    }

    @Override
    protected void initDatas() {
        super.initDatas();
        EventBus.getDefault().register(this);
        if (mIsToUpdate==1){
            mIvColse.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(mVersion)) {
            mTvVersionCode.setText(String.format("%s版本全新上线", mVersion));
        }
        if (!TextUtils.isEmpty(mToUpdateContent)) {
            mTvContent.setText(mToUpdateContent);
            mTvContent.setVisibility(View.VISIBLE);
        } else {
            mTvContent.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mIvColse.setOnClickListener(this);
        mTvUpload.setOnClickListener(this);
        setCanceledOnTouchOutside(false);
        setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return true;
            }
        });
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                EventBus.getDefault().unregister(this);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_colse:
                dismiss();
                break;
            case R.id.tv_upload:
                if (mIsToUpdate!=1){
                    dismiss();
                }
                if (DownLoadIntentService.isDownLoad) {
                    if (NetworkUtil.isWifi(mContext)) {
                        downLoad((Activity) mContext);
                    } else {
                        if (NetworkUtil.checkNetworkState(mContext)) {
                            showWifiDialog((Activity) mContext);
                        }
                    }
                }
                break;
        }
    }

    /**
     * 下载
     *
     * @param activity
     */
    private void downLoad(Activity activity) {
        PermissionDialog.requestPermissions(activity, new PermissionDialog.PermissionCallBack() {
            @Override
            public void onSuccess() {
                if (NotificationManager.isOpenNotification(activity)) {
                    mTvUpload.setText("下载中");
                    DownLoadIntentService.startUpdateService(activity, mVersionUrl);
                } else {
                    showNotificationDialog(activity);
                }
            }

            @Override
            public void onFailure() {
            }

            @Override
            public void onAlwaysFailure() {
                PermissionDialog.showDialog(activity,"android.permission.WRITE_EXTERNAL_STORAGE");
            }
        });
    }

    /**
     * 判断是否WIFI dialog
     */
    private void showWifiDialog(Activity activity) {
        CurrencyDialog currencyDialog = new CurrencyDialog(activity);
        currencyDialog.show();
        currencyDialog.setTitleText("非Wifi状态下是否下载？");
        currencyDialog.setCanceledOnTouchOutside(false);
        currencyDialog.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return true;
            }
        });
        currencyDialog.setOnClickCallback(type -> {
            if (type == CurrencyDialog.SELECT_FINISH) {
                downLoad(activity);
            }
        });
    }

    /**
     * 打开通知dialog
     */
    private void showNotificationDialog(Activity activity) {
        String permissionsTitle = "通知管理";
        String permissionsDescribe ="请开启通知，开通后可正常查看下载进度";
        CurrencyDialog currencyDialog = new CurrencyDialog(activity);
        currencyDialog.show();
        currencyDialog.setType(2);
        currencyDialog.setText("去设置");
        currencyDialog.setTitleText(permissionsTitle, permissionsDescribe);
        currencyDialog.setCanceledOnTouchOutside(false);
        currencyDialog.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return true;
            }
        });
        currencyDialog.setOnClickCallback(new OnClickCallback() {
            @Override
            public void onClickType(int type) {
                mTvUpload.setText("下载中");
                DownLoadIntentService.startUpdateService(activity, mVersionUrl);
                if (type == CurrencyDialog.SELECT_FINISH) {
                    NotificationManager.openNotification(activity);
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void UpdateProgressEvent(UpdateProgressEvent event) {
        if (!event.isDone()) {
            mTvUpload.setText("下载中"+ ":" + event.getBytesRead() + "%");
        }else{
            mTvUpload.setText("下载成功");
        }
    }


}

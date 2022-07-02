package com.merchant.drifting.mvp.ui.dialog;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.jess.arms.base.BaseDialog;
import com.merchant.drifting.R;


/**
 * @Description: 加载框
 * @Author: wjq
 * @CreateDate: 2021/8/26 16:45
 */
public class LoadingDialog extends BaseDialog implements LifecycleObserver {
    private static final int FADED_ROUND_SPINNER = 0;
    //    private ImageView mIvLoading;
    private Context mContext;

    public LoadingDialog(Context context, int tasksign_dialog) {
        super(context, tasksign_dialog);
        this.mContext = context;
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        if (context instanceof FragmentActivity) {
            ((FragmentActivity) context).getLifecycle().addObserver(this);
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_loading;
    }

    @Override
    protected float getDialogWith() {
        return 1.0f;
    }

    @Override
    protected void initView() {
        super.initView();
//        mIvLoading = findViewById(R.id.iv_loading);
    }

    @Override
    protected void initDatas() {
        super.initDatas();
        this.setCanceledOnTouchOutside(false);
        setSpinnerType(FADED_ROUND_SPINNER);
    }

    private void setSpinnerType(int spinnerType) {
        switch (spinnerType) {
            case 0:
//                GlideUtil.create().loadFileGifImage(mContext, R.drawable.icon_loading, mIvLoading);
                break;
        }
    }

    @Override
    public void dismiss() {
        if (mContext instanceof Activity) {
            Activity activity = (Activity) mContext;
            if (!activity.isFinishing()) {
                super.dismiss();
            }
        }
    }

    @Override
    public void show() {
        if (mContext instanceof Activity) {
            Activity activity = (Activity) mContext;
            if (!activity.isFinishing()) {
                super.show();
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        if (isShowing()) {
            dismiss();
        }
    }

}


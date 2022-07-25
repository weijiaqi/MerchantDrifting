package com.merchant.drifting.mvp.ui.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jess.arms.base.BaseDialog;
import com.merchant.drifting.R;

/**
 * @Description: 退出
 * @Author : WeiJiaQI
 * @Time : 2022/7/19 14:52
 */
public class LogOutDialog extends BaseDialog implements View.OnClickListener {

    public static final int SELECT_FINISH = 0x01;
    private TextView mTvCofim, mTvCancel;

    public LogOutDialog(@NonNull Activity activity) {
        super(activity);
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_log_out;
    }

    @Override
    protected float getDialogWith() {
        return 1f;
    }

    @Override
    protected void initView() {
        super.initView();
        mTvCofim = findViewById(R.id.tv_cofim);
        mTvCancel = findViewById(R.id.tv_cancel);
    }


    @Override
    protected void initEvents() {
        super.initEvents();
        mTvCancel.setOnClickListener(this);
        mTvCofim.setOnClickListener(this);
    }

    @Override
    protected void initDatas() {
        super.initDatas();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_cofim:
                dismiss();
                if (onClickCallback != null) {
                    onClickCallback.onClickType(SELECT_FINISH);
                }
                break;
        }

    }
}

package com.merchant.drifting.mvp.ui.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jess.arms.base.BaseDialog;
import com.merchant.drifting.R;

public class MessageDialog extends BaseDialog implements View.OnClickListener {

    private TextView mTvCancel, mTvCofim;
    public static final int SELECT_FINISH = 0x01;

    public MessageDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void initView() {
        super.initView();
        mTvCancel = findViewById(R.id.tv_cancel);
        mTvCofim = findViewById(R.id.tv_cofim);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mTvCancel.setOnClickListener(this);
        mTvCofim.setOnClickListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_message;
    }

    @Override
    protected float getDialogWith() {
        return 1f;
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

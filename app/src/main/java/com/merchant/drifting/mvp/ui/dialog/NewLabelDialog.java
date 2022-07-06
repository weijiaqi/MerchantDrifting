package com.merchant.drifting.mvp.ui.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hjq.shape.view.ShapeEditText;
import com.jess.arms.base.BaseDialog;
import com.merchant.drifting.R;
import com.merchant.drifting.util.StringUtil;
import com.merchant.drifting.util.ToastUtil;

public class NewLabelDialog extends BaseDialog implements View.OnClickListener {

    private TextView mTvCancel, mTvCofim;

    private ShapeEditText mEtName;


    public NewLabelDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void initView() {
        super.initView();
        mTvCancel = findViewById(R.id.tv_cancel);
        mTvCofim = findViewById(R.id.tv_cofim);
        mEtName = findViewById(R.id.et_name);
    }

    @Override
    protected void initEvents() {
        super.initEvents();
        mTvCancel.setOnClickListener(this);
        mTvCofim.setOnClickListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_new_label;
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
                if (StringUtil.isEmpty(mEtName.getText().toString())) {
                    ToastUtil.showToast("请输入10字以内的标签");
                    return;
                }
                dismiss();
                if (onContentClickCallback != null) {
                    onContentClickCallback.onContetClick(mEtName.getText().toString());
                }
                break;
        }
    }
}

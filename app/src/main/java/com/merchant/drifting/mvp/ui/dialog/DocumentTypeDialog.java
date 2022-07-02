package com.merchant.drifting.mvp.ui.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jess.arms.base.BaseDialog;
import com.merchant.drifting.R;
import com.merchant.drifting.util.ClickUtil;

/**
 * @Description: 身份证选择
 * @Author     : WeiJiaQI
 * @Time       : 2022/7/2 10:06
 */
public class DocumentTypeDialog extends BaseDialog implements View.OnClickListener {

    private TextView tvCardType0, tvCancle;

    public DocumentTypeDialog(@NonNull Context context) {
        super(context);
    }

    public DocumentTypeDialog(Context context, int dialogStyle) {
        super(context, dialogStyle);
    }

    @Override
    protected void getWindows(Window window) {
        super.getWindows(window);

        if (window != null) {
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.PictureDialog);
        }
    }

    @Override
    protected void initView() {
        super.initView();
        tvCancle = findViewById(R.id.tv_cancel);
        tvCardType0 = findViewById(R.id.tv_card_type_0);

    }

    @Override
    protected void initDatas() {
        super.initDatas();
        tvCancle.setOnClickListener(this);
        tvCardType0.setOnClickListener(this);

    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_document_type;
    }

    @Override
    protected float getDialogWith() {
        return 1f;
    }

    @Override
    public void onClick(View v) {
        if (!ClickUtil.isFastClick(v.getId())) {
            switch (v.getId()) {
                case R.id.tv_cancel:
                    dismiss();
                    break;

                case R.id.tv_card_type_0:
                    if (onClickCallback != null) {
                        onClickCallback.onClickType(0);
                    }
                    dismiss();
                    break;


            }
        }
    }
}

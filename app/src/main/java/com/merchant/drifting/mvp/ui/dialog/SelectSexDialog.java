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
 * Tag:选择地区
 *
 * @author Xaoyv
 * date 2021/7/24 19:11
 */
public class SelectSexDialog extends BaseDialog implements View.OnClickListener {

    private TextView tvCardType0, tvCardType1, tvCancle;

    public SelectSexDialog(@NonNull Context context) {
        super(context);
    }

    public SelectSexDialog(Context context, int dialogStyle) {
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
        tvCardType1 = findViewById(R.id.tv_card_type_1);
    }

    @Override
    protected void initDatas() {
        super.initDatas();
        tvCancle.setOnClickListener(this);
        tvCardType0.setOnClickListener(this);
        tvCardType1.setOnClickListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_select_sex;
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

                case R.id.tv_card_type_1:
                    if (onClickCallback != null) {
                        onClickCallback.onClickType(1);
                    }
                    dismiss();
                    break;
            }
        }
    }
}

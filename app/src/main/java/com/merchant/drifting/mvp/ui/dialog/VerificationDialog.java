package com.merchant.drifting.mvp.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;

import com.jess.arms.base.BaseDialog;
import com.merchant.drifting.R;
import com.merchant.drifting.mvp.model.entity.WriteOffEntity;
import com.merchant.drifting.util.GlideUtil;

/**
 * @Description: 核验dialog
 * @Author : WeiJiaQI
 * @Time : 2022/7/15 16:46
 */
public class VerificationDialog extends BaseDialog implements View.OnClickListener {

    public static final int SELECT_FINISH = 0x01;
    private TextView mTvOrderNo, mTvTitle, mTvDesc, mTvPrice, mTvCofim;
    private ImageView mIvPic;
    private Context context;
    private WriteOffEntity writeOffEntity;

    public VerificationDialog(@NonNull Context context, WriteOffEntity entity) {
        super(context);
        this.context = context;
        this.writeOffEntity = entity;
    }


    @Override
    protected int getContentView() {
        return R.layout.dialog_verification;
    }


    @Override
    protected void initView() {
        super.initView();
        mTvOrderNo = findViewById(R.id.tv_order_no);
        mTvTitle = findViewById(R.id.tv_title);
        mTvDesc = findViewById(R.id.tv_desc);
        mTvPrice = findViewById(R.id.tv_price);
        mIvPic = findViewById(R.id.iv_pic);
        mTvCofim = findViewById(R.id.tv_cofim);
    }


    @Override
    protected void initDatas() {
        super.initDatas();
        mTvCofim.setOnClickListener(this);
        mTvOrderNo.setText(writeOffEntity.getOrder_sub_sn());
        mTvTitle.setText(writeOffEntity.getSku_name());
        mTvDesc.setText(writeOffEntity.getIntro());
        mTvPrice.setText("¥" + writeOffEntity.getPrice());
        GlideUtil.create().loadNormalPic(context,writeOffEntity.getImage(),mIvPic);
    }


    @Override
    protected float getDialogWith() {
        return 0.8f;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cofim:
                dismiss();

                break;
        }
    }
}

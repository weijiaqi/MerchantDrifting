package com.merchant.drifting.mvp.ui.activity.login;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.merchant.drifting.R;
import com.merchant.drifting.util.ClickUtil;
import com.merchant.drifting.util.ToastUtil;
import com.merchant.drifting.util.animator.AnimatorUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Description: 商家中心
 * @Author : WeiJiaQI
 * @Time : 2022/6/16 13:42
 */
public class MerchantCenterActivity extends BaseActivity {
    @BindView(R.id.iv_pic1)
    ImageView mPic1;
    @BindView(R.id.iv_pic2)
    ImageView mPic2;
    @BindView(R.id.iv_pic3)
    ImageView mPic3;
    @BindView(R.id.iv_pic4)
    ImageView mPic4;
    @BindView(R.id.iv_pic5)
    ImageView mPic5;
    @BindView(R.id.iv_logo)
    ImageView mIvLogo;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_merchant_center;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(false);
        AnimatorUtil.floatAnim(mPic1, 4000);
        AnimatorUtil.floatAnim(mPic2, 4000);
        AnimatorUtil.floatAnim(mPic5, 4000);
        AnimatorUtil.ZoomAnim(mPic3, 4000);
        AnimatorUtil.ZoomAnim(mPic4, 4000);
    }


    @OnClick({R.id.tv_login, R.id.tv_open_shop})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.tv_login:
                    VerificationCodeActivity.start(this, false);
                    break;
                case R.id.tv_open_shop:
                    BusinessOpeningActivity.start(this,false);
                    break;
            }
        }
    }
}

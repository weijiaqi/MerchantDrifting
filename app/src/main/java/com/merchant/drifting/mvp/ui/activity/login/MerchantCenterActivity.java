package com.merchant.drifting.mvp.ui.activity.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.BaseEntity;
import com.jess.arms.di.component.AppComponent;
import com.merchant.drifting.R;
import com.merchant.drifting.mvp.model.entity.HaveShopEntity;
import com.merchant.drifting.mvp.ui.activity.home.HomeActivity;
import com.merchant.drifting.mvp.ui.activity.index.SwitchMerchantsActivity;
import com.merchant.drifting.mvp.ui.activity.user.ApplicationCompletedActivity;
import com.merchant.drifting.mvp.ui.activity.user.OpenShopActivity;
import com.merchant.drifting.storageinfo.Preferences;
import com.merchant.drifting.util.ClickUtil;
import com.merchant.drifting.util.request.RequestUtil;
import com.merchant.drifting.util.animator.AnimatorUtil;
import com.merchant.drifting.util.callback.BaseDataCallBack;

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

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, MerchantCenterActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

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
        if (Preferences.isAnony()) {
            if (!TextUtils.isEmpty(Preferences.getShopId())) {
                HomeActivity.start(this, true);
            } else {
                RequestUtil.create().haveshop(entity -> {
                    if (entity != null && entity.getCode() == 200) {
                        if (entity.getData().getTotal() > 0) {
                            Preferences.saveShopId(entity.getData().getShops().get(0).getShop_id() + "");
                            Preferences.saveShopName(entity.getData().getShops().get(0).getShop_name() + "");
                            HomeActivity.start(this, true);
                        } else {
                            SwitchMerchantsActivity.start(this, 1, true);
                        }
                    }
                });
            }
        } else {
            AnimatorUtil.floatAnim(mPic1, 4000);
            AnimatorUtil.floatAnim(mPic2, 4000);
            AnimatorUtil.floatAnim(mPic5, 4000);
            AnimatorUtil.ZoomAnim(mPic3, 4000);
            AnimatorUtil.ZoomAnim(mPic4, 4000);
        }
    }


    @OnClick({R.id.tv_login, R.id.tv_open_shop})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.tv_login:
                    VerificationCodeActivity.start(this, false);
                    break;
                case R.id.tv_open_shop:
                    BusinessOpeningActivity.start(this, false);
                    break;
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestUtil.create().disDispose();
    }
}

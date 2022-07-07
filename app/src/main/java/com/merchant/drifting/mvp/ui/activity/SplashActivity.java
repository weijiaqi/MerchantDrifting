package com.merchant.drifting.mvp.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.merchant.drifting.R;
import com.merchant.drifting.mvp.ui.activity.home.HomeActivity;
import com.merchant.drifting.mvp.ui.activity.index.SwitchMerchantsActivity;
import com.merchant.drifting.mvp.ui.activity.login.MerchantCenterActivity;
import com.merchant.drifting.storageinfo.Preferences;
import com.merchant.drifting.util.request.RequestUtil;



/**
 * 启动页
 */
public class SplashActivity extends BaseActivity {

    private Handler mHandler = new Handler();

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_splash;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mHandler.postDelayed(mHomeRunnable, 1000);
    }

    Runnable mHomeRunnable = () -> {
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
        }else {
            MerchantCenterActivity.start(this,false);
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mHomeRunnable);
    }
}

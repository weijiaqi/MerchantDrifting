package com.merchant.drifting.mvp.ui.activity.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.merchant.drifting.R;
import com.merchant.drifting.mvp.ui.activity.index.BankCardManagementActivity;
import com.merchant.drifting.mvp.ui.activity.login.MerchantCenterActivity;
import com.merchant.drifting.mvp.ui.activity.web.ShowWebViewActivity;
import com.merchant.drifting.util.ClickUtil;
import com.merchant.drifting.util.LogInOutDataUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Description: 设置
 * @Author : WeiJiaQI
 * @Time : 2022/6/29 11:23
 */
public class SetUpActivity extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, SetUpActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_set_up;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(false);
        mToolbarTitle.setText("设置");
    }

    @OnClick({R.id.toolbar_back, R.id.tv_exit, R.id.rl_privacy,R.id.rl_user})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.tv_exit:
                    LogInOutDataUtil.successOutClearData();
                    MerchantCenterActivity.start(this, true);
                    break;
                case R.id.rl_privacy:  //隐私管理
                    ShowWebViewActivity.start(this, 1, false);
                    break;
                case R.id.rl_user:  //隐私管理
                    ShowWebViewActivity.start(this, 2, false);
                    break;
            }
        }
    }
}

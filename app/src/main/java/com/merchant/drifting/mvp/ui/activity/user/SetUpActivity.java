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
import com.jess.arms.base.BaseDialog;
import com.jess.arms.base.BaseEntity;
import com.jess.arms.di.component.AppComponent;
import com.merchant.drifting.R;
import com.merchant.drifting.app.application.MerchantDriftingApplication;
import com.merchant.drifting.mvp.ui.activity.index.BankCardManagementActivity;
import com.merchant.drifting.mvp.ui.activity.login.MerchantCenterActivity;
import com.merchant.drifting.mvp.ui.activity.web.ShowWebViewActivity;
import com.merchant.drifting.mvp.ui.dialog.LogOutDialog;
import com.merchant.drifting.util.AppUtil;
import com.merchant.drifting.util.ClickUtil;
import com.merchant.drifting.util.LogInOutDataUtil;
import com.merchant.drifting.util.StringUtil;
import com.merchant.drifting.util.callback.BaseDataCallBack;
import com.merchant.drifting.util.request.RequestUtil;

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
    @BindView(R.id.tv_version_code)
    TextView mTvVersionCode;
    private LogOutDialog logOutDialog;

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
        mTvVersionCode.setText("版本号：V" + StringUtil.formatNullString(AppUtil.getVerName(MerchantDriftingApplication.getContext())+"("+AppUtil.getVersionCode(MerchantDriftingApplication.getContext())+")"));
    }

    @OnClick({R.id.toolbar_back, R.id.tv_exit, R.id.rl_privacy, R.id.rl_user, R.id.rl_log_out})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.tv_exit:
                    exit();
                    break;
                case R.id.rl_privacy:  //隐私管理
                    ShowWebViewActivity.start(this, 1, false);
                    break;
                case R.id.rl_user:  //隐私管理
                    ShowWebViewActivity.start(this, 2, false);
                    break;
                case R.id.rl_log_out:  //注销信息
                    logOutDialog = new LogOutDialog(this);
                    logOutDialog.show();
                    logOutDialog.setOnClickCallback(type -> {
                        if (type == LogOutDialog.SELECT_FINISH) {
                            RequestUtil.create().businessunregister(entity -> {
                                if (entity != null && entity.getCode() == 200) {
                                    exit();
                                }
                            });
                        }
                    });
                    break;
            }
        }
    }


    public void exit() {
        LogInOutDataUtil.successOutClearData();
        MerchantCenterActivity.start(this, true);
    }
}

package com.merchant.drifting.mvp.ui.activity.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.merchant.drifting.R;
import com.merchant.drifting.mvp.ui.activity.home.HomeActivity;
import com.merchant.drifting.util.ClickUtil;

import butterknife.OnClick;

/**
 * @Description: 申请完成
 * @Author : WeiJiaQI
 * @Time : 2022/6/24 11:16
 */
public class ApplicationCompletedActivity extends BaseActivity {

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, ApplicationCompletedActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_application_completed;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @OnClick({R.id.tv_complete})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.tv_complete:
                    HomeActivity.start(this, true);
                    break;
            }
        }
    }
}

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
import com.merchant.drifting.mvp.ui.activity.home.HomeActivity;
import com.merchant.drifting.util.ClickUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Description: 申请完成
 * @Author : WeiJiaQI
 * @Time : 2022/6/24 11:16
 */
public class ApplicationCompletedActivity extends BaseActivity {

    @BindView(R.id.tv_store_name)
    TextView mTvStoreName;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;

    private static String EXTRA_NAME = "extra_name";
    private static String EXTRA_STORE_NAME = "extra_store_name";
    private static String EXTRA_PHONE = "extra_phone";

    private String name, storename, phone;

    public static void start(Context context, String name, String storename, String phone, boolean closePage) {
        Intent intent = new Intent(context, ApplicationCompletedActivity.class);
        intent.putExtra(EXTRA_NAME, name);
        intent.putExtra(EXTRA_STORE_NAME, storename);
        intent.putExtra(EXTRA_PHONE, phone);
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
        if (getIntent() != null) {
            name = getIntent().getExtras().getString(EXTRA_NAME);
            storename = getIntent().getExtras().getString(EXTRA_STORE_NAME);
            phone = getIntent().getExtras().getString(EXTRA_PHONE);
        }
        mTvName.setText(name);
        mTvStoreName.setText(storename);
        mTvPhone.setText(phone);
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

package com.merchant.drifting.mvp.ui.activity.index;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import com.merchant.drifting.R;
import com.merchant.drifting.di.component.DaggerAddBankCardActivityComponent;
import com.merchant.drifting.mvp.contract.AddBankCardActivityContract;
import com.merchant.drifting.mvp.presenter.AddBankCardActivityPresenter;
import com.merchant.drifting.util.ClickUtil;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/06/30 10:18
 *
 * @author 添加银行卡
 * module name is AddBankCardActivityActivity
 */
public class AddBankCardActivityActivity extends BaseActivity<AddBankCardActivityPresenter> implements AddBankCardActivityContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.tv_get_code)
    TextView mTvGetCode;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, AddBankCardActivityActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAddBankCardActivityComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_add_bank_card; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(false);
        mToolbarTitle.setText("添加银行卡");
        initListener();
    }

    public void initListener() {
        mTvGetCode.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        mTvGetCode.getPaint().setAntiAlias(true);//抗锯齿
    }

    @OnClick({R.id.toolbar_back})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
            }
        }
    }

    public Activity getActivity() {
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {

    }
}
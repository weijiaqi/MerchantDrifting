package com.merchant.drifting.mvp.ui.activity.login;
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
import com.merchant.drifting.di.component.DaggerVerificationCodeLoginComponent;
import com.merchant.drifting.mvp.contract.VerificationCodeLoginContract;
import com.merchant.drifting.mvp.presenter.VerificationCodeLoginPresenter;
import com.merchant.drifting.mvp.ui.activity.home.HomeActivity;
import com.merchant.drifting.mvp.ui.activity.merchant.NewsActivity;
import com.merchant.drifting.util.ClickUtil;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/06/20 10:24
 * @author  验证码 登录
 * module name is VerificationCodeLoginActivity
 */
public class VerificationCodeLoginActivity extends BaseActivity<VerificationCodeLoginPresenter> implements VerificationCodeLoginContract.View {
    @BindView(R.id.tv_bar)
    TextView mTvBar;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.tv_code)
    TextView mTvCode;
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerVerificationCodeLoginComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }


    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, VerificationCodeLoginActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState){
        return R.layout.activity_verification_code_login; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(false);
        setStatusBarHeight(mTvBar);
        mToolbarTitle.setText("登录");
        initListener();
    }

    public void initListener() {
        mTvCode.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        mTvCode.getPaint().setAntiAlias(true);//抗锯齿
    }

    public Activity getActivity(){
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @OnClick({R.id.toolbar_back,R.id.tv_verification})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.tv_verification:  //验证
                    HomeActivity.start(this,true);
                    break;
            }
        }
    }
}
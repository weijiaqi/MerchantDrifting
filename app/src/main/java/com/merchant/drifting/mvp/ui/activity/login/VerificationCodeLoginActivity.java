package com.merchant.drifting.mvp.ui.activity.login;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.BaseEntity;
import com.jess.arms.di.component.AppComponent;
import com.merchant.drifting.R;
import com.merchant.drifting.di.component.DaggerVerificationCodeLoginComponent;
import com.merchant.drifting.mvp.contract.VerificationCodeLoginContract;
import com.merchant.drifting.mvp.model.entity.HaveShopEntity;
import com.merchant.drifting.mvp.model.entity.LoginEntity;
import com.merchant.drifting.mvp.presenter.VerificationCodeLoginPresenter;
import com.merchant.drifting.mvp.ui.activity.home.HomeActivity;
import com.merchant.drifting.mvp.ui.activity.index.SwitchMerchantsActivity;
import com.merchant.drifting.mvp.ui.activity.merchant.NewsActivity;
import com.merchant.drifting.util.ClickUtil;
import com.merchant.drifting.util.StringUtil;
import com.merchant.drifting.util.ToastUtil;
import com.merchant.drifting.util.callback.BaseDataCallBack;
import com.merchant.drifting.util.request.RequestUtil;
import com.merchant.drifting.view.VerificationCodeView;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/06/20 10:24
 *
 * @author 验证码 登录
 * module name is VerificationCodeLoginActivity
 */
public class VerificationCodeLoginActivity extends BaseActivity<VerificationCodeLoginPresenter> implements VerificationCodeLoginContract.View {
    @BindView(R.id.tv_bar)
    TextView mTvBar;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.verification_code)
    VerificationCodeView mCode;
    private CountDownTimer timer;
    private static final String EXTRA_PHONE = "extra_phone";
    private String phone, code;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerVerificationCodeLoginComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }


    public static void start(Context context, String phone, boolean closePage) {
        Intent intent = new Intent(context, VerificationCodeLoginActivity.class);
        intent.putExtra(EXTRA_PHONE, phone);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_verification_code_login; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(false);
        setStatusBarHeight(mTvBar);
        mToolbarTitle.setText("登录");

        if (getIntent() != null) {
            phone = getIntent().getStringExtra(EXTRA_PHONE);
        }
        initListener();
    }

    public void initListener() {
        mTvPhone.setText(phone);

        mTvTime.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        mTvTime.getPaint().setAntiAlias(true);//抗锯齿

        startCountDown();

        mCode.setInputListener(text -> {
            code = text;
        });
    }

    @Override
    public void onCodeSuccess() {

    }

    @Override
    public void OnLoginSuccess(LoginEntity entity) {
        RequestUtil.create().haveshop(entity1 -> {
            if (entity1 != null && entity1.getCode() == 200) {
                if (entity1.getData().getTotal() > 0) {
                    HomeActivity.start(this, true);
                } else {
                    SwitchMerchantsActivity.start(this, 1, true);
                }
            }
        });


    }

    @Override
    public void onNetError() {

    }

    public Activity getActivity() {
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtil.showToast(message);
    }

    @OnClick({R.id.toolbar_back, R.id.tv_verification, R.id.tv_time})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.tv_verification:  //验证
                    if (StringUtil.isEmpty(code)) {
                        showMessage("请输入验证码");
                        return;
                    }
                    login();
                    break;
                case R.id.tv_time:
                    if (mPresenter != null) {
                        mPresenter.getCodeData(phone, 2);
                    }
                    break;
            }
        }
    }


    public void login() {
        if (mPresenter != null) {
            mPresenter.login(phone, code);
        }
    }

    /**
     * 开始倒计时
     */
    private void startCountDown() {
        if (timer == null) {
            timer = new CountDownTimer(60 * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    //剩余秒数
                    int surplusSeconds = (int) (millisUntilFinished / 1000);
                    mTvTime.setEnabled(false);
                    mTvTime.setText(surplusSeconds + "S");
                }

                @Override
                public void onFinish() {
                    mTvTime.setEnabled(true);
                    mTvTime.setText("重新获取");
                    cleanCountDown();
                }
            }.start();
        }
    }

    /**
     * 结束倒计时
     */
    public void cleanCountDown() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cleanCountDown();
    }
}
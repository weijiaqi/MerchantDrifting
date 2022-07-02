package com.merchant.drifting.mvp.ui.activity.login;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import com.merchant.drifting.R;
import com.merchant.drifting.di.component.DaggerVerificationCodeComponent;
import com.merchant.drifting.mvp.contract.VerificationCodeContract;
import com.merchant.drifting.mvp.model.entity.LoginEntity;
import com.merchant.drifting.mvp.presenter.VerificationCodePresenter;
import com.merchant.drifting.util.ClickUtil;
import com.merchant.drifting.util.StringUtil;
import com.merchant.drifting.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/06/16 18:34
 *
 * @author 手机号登录
 * module name is VerificationCodeActivity
 */
public class VerificationCodeActivity extends BaseActivity<VerificationCodePresenter> implements VerificationCodeContract.View {
    @BindView(R.id.tv_protocol)
    TextView mTvProtocol;
    @BindView(R.id.et_phone)
    TextView mEtPhone;
    @BindView(R.id.ck_protocol)
    CheckBox mCkProtocol;
    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, VerificationCodeActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerVerificationCodeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_verification_code; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(false);
        initListener();
    }

    public void initListener() {
        setUserComment();
    }

    /**
     * @description 给隐私设置颜色
     */
    public void setUserComment() {
        mTvProtocol.setOnLongClickListener(v -> true);
        mTvProtocol.setHighlightColor(getResources().getColor(android.R.color.transparent));
        mTvProtocol.setText("阅读并同意商家中心的");
        mTvProtocol.append(buildRegisterSpannableString("《用户协议》"));
        mTvProtocol.append("和");
        mTvProtocol.append(buildPrivacySpannableString("《隐私政策》"));
        mTvProtocol.setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * @description 隐私政策
     */
    private SpannableString buildPrivacySpannableString(String privacy) {
        SpannableString userSpannable = new SpannableString(privacy);
        userSpannable.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                if (!ClickUtil.isFastClick(widget.getId())) {

                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(getResources().getColor(R.color.color_42));
                ds.setUnderlineText(false);
            }
        }, 0, userSpannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return userSpannable;
    }

    /**
     * @description 用户协议
     */
    private SpannableString buildRegisterSpannableString(String register) {
        SpannableString userSpannable = new SpannableString(register);
        userSpannable.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                if (!ClickUtil.isFastClick(widget.getId())) {

                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(getResources().getColor(R.color.color_42));
                ds.setUnderlineText(false);
            }
        }, 0, userSpannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return userSpannable;
    }


    @Override
    public void OnGetCodeSuccess() {
        VerificationCodeLoginActivity.start(this, mEtPhone.getText().toString(),false);
    }



    @Override
    public void onNetError() {

    }

    public Activity getActivity() {
        return this;
    }


    @OnClick({R.id.tv_open_shop, R.id.tv_login})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.tv_open_shop:
                    BusinessOpeningActivity.start(this, false);
                    break;
                case R.id.tv_login:
                    if (StringUtil.isEmpty(mEtPhone.getText().toString())) {
                        showMessage("请输入手机号");
                        return;
                    }
                    if (!mCkProtocol.isChecked()) {
                        showMessage("请勾选是否同意服务隐私协议!");
                        return;
                    }
                    if (mPresenter != null) {
                        mPresenter.getCodeData(mEtPhone.getText().toString(), 2);
                    }

                    break;
            }
        }
    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtil.showToast(message);
    }
}
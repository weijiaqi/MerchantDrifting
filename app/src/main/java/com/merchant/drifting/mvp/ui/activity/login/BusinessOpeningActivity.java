package com.merchant.drifting.mvp.ui.activity.login;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import com.merchant.drifting.R;
import com.merchant.drifting.di.component.DaggerBusinessOpeningComponent;
import com.merchant.drifting.mvp.contract.BusinessOpeningContract;
import com.merchant.drifting.mvp.presenter.BusinessOpeningPresenter;
import com.merchant.drifting.mvp.ui.activity.user.OpenShopActivity;
import com.merchant.drifting.util.ClickUtil;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/06/20 13:34
 *
 * @author 商家开店
 * module name is BusinessOpeningActivity
 */
public class BusinessOpeningActivity extends BaseActivity<BusinessOpeningPresenter> implements BusinessOpeningContract.View {
    @BindView(R.id.tv_bar)
    TextView mTvBar;
    @BindView(R.id.tv_get_code)
    TextView mTvGetCode;
    @BindView(R.id.tv_protocol)
    TextView mTvProtocol;
    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, BusinessOpeningActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerBusinessOpeningComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_business_opening; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(false);
        setStatusBarHeight(mTvBar);
        initListener();
    }

    public void initListener() {
        mTvGetCode.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        mTvGetCode.getPaint().setAntiAlias(true);//抗锯齿
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


    public Activity getActivity() {
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @OnClick({R.id.toolbar_back,R.id.tv_login})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.tv_login:
                    OpenShopActivity.start(this,false);
                    break;
            }
        }
    }
}
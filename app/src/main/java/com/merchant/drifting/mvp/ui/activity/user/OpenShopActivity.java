package com.merchant.drifting.mvp.ui.activity.user;

import android.app.Activity;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.merchant.drifting.R;
import com.merchant.drifting.mvp.ui.activity.merchant.NewsActivity;
import com.merchant.drifting.util.ClickUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Description: 我要开店
 * @Author : WeiJiaQI
 * @Time : 2022/6/22 16:00
 */
public class OpenShopActivity extends BaseActivity {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.tv_protocol)
    TextView mTvProtocol;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, OpenShopActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_open_shop;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(false);
        mToolbarTitle.setText("我要开店");
        initListener();
    }

    public void initListener() {
        setStoreComment();
    }

    /**
     * @description 给隐私设置颜色
     */
    public void setStoreComment() {
        mTvProtocol.setOnLongClickListener(v -> true);
        mTvProtocol.setHighlightColor(getResources().getColor(android.R.color.transparent));
        mTvProtocol.setText("阅读并同意签署的");
        mTvProtocol.append(buildStoreSpannableString("《开店协议》"));

        mTvProtocol.setMovementMethod(LinkMovementMethod.getInstance());
    }


    /**
     * @description 开店协议
     */
    private SpannableString buildStoreSpannableString(String privacy) {
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


    @OnClick({R.id.toolbar_back, R.id.tv_be_ready})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.tv_be_ready:
                    ApplicationMaterialsActivity.start(this,false);
                    break;
            }
        }
    }
}

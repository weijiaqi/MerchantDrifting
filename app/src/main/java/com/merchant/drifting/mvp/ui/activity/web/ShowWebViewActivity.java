package com.merchant.drifting.mvp.ui.activity.web;



import static com.merchant.drifting.WebUrlConstant.USER_LICENSE;
import static com.merchant.drifting.WebUrlConstant.USER_PRIVACY;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.merchant.drifting.R;
import com.merchant.drifting.util.ClickUtil;
import com.merchant.drifting.util.ViewUtil;
import com.merchant.drifting.view.customWebview.CustomWebView;
import com.merchant.drifting.view.customWebview.WebLoadingListener;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Description: H5界面
 * @Author : WeiJiaQI
 * @Time : 2022/5/9 11:51
 */
public class ShowWebViewActivity extends BaseActivity implements WebLoadingListener {

    @BindView(R.id.fl_container)
    FrameLayout mFlContainer;
    @BindView(R.id.toolbar_title)
    TextView mTvTitle;
    @BindView(R.id.fl_web)
    FrameLayout mFlWeb;

    private CustomWebView mWebView;
    private static final String WEB_TYPE = "web_type";
    private int mType = -1;
    private boolean loadErrored;

    public static void start(Context context, int type, boolean closePage) {
        Intent intent = new Intent(context, ShowWebViewActivity.class);
        intent.putExtra(WEB_TYPE, type);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_show_web;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        mType = getIntent().getIntExtra(WEB_TYPE, 0);
        initView();
    }

    public void initView() {
        mWebView = new CustomWebView(getApplicationContext(), true);
        mWebView.setWebLoadListener(this);
        mWebView.enAbleDownLoad(this);
        mWebView.setVerticalScrollBarEnabled(false); //垂直不显示滚动条
        mFlWeb.addView(mWebView);
        mWebView.clearCache(true);
        setDatas();
    }


    private void setDatas() {
        switch (mType) {
            case 0:
                mWebView.setWebChromeClient(new WebChromeClient() {
                    @Override
                    public void onReceivedTitle(WebView view, String title) {
                        super.onReceivedTitle(view, title);
                        mTvTitle.setText(title);
                    }
                });
                break;
            case 1:
                mTvTitle.setText("隐私协议");
                mWebView.loadUrl(USER_PRIVACY);
                break;
            case 2:
                mTvTitle.setText("用户协议");
                mWebView.loadUrl(USER_LICENSE);
                break;
        }
    }


    @OnClick({R.id.toolbar_back})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    if (mWebView.canGoBack()) {
                        mWebView.goBack();
                    } else {
                        finish();
                    }
                    break;
            }
        }
    }

    @Override
    public void onLoadError() {
        loadErrored = true;
    }

    @Override
    public void onLoadStart() {
        ViewUtil.create().setAnimation(this, mFlContainer);
    }

    @Override
    public void onLoadFinish() {
        if (loadErrored) {
            mFlWeb.setVisibility(View.GONE);
        } else {
            mFlWeb.setVisibility(View.VISIBLE);
        }
        ViewUtil.create().setView(mFlContainer);
    }

    @Override
    public void startProgress(int newProgress) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView != null) {
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                    return true;
                    //退出网页
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }
}

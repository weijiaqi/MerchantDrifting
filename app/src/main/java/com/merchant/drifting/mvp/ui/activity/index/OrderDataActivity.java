package com.merchant.drifting.mvp.ui.activity.index;

import static com.merchant.drifting.app.api.Api.WEB_LINECHART_BASEURL;

import android.annotation.SuppressLint;
import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.hjq.shape.view.ShapeTextView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.merchant.drifting.R;
import com.merchant.drifting.di.component.DaggerOrderDataComponent;
import com.merchant.drifting.mvp.contract.OrderDataContract;
import com.merchant.drifting.mvp.model.entity.ShopStaticOrderEntity;
import com.merchant.drifting.mvp.presenter.OrderDataPresenter;
import com.merchant.drifting.mvp.ui.adapter.OrderDataAdapter;
import com.merchant.drifting.storageinfo.Preferences;
import com.merchant.drifting.util.ClickUtil;
import com.merchant.drifting.util.GsonUtil;
import com.merchant.drifting.util.SpannableUtil;
import com.merchant.drifting.util.StringUtil;
import com.merchant.drifting.view.ThickLineTextSpan;
import com.merchant.drifting.view.customWebview.CustomWebView;
import com.merchant.drifting.view.customWebview.WebLoadingListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created on 2022/06/27 14:08
 *
 * @author 订单数据
 * module name is OrderDataActivity
 */
public class OrderDataActivity extends BaseActivity<OrderDataPresenter> implements OrderDataContract.View, WebLoadingListener {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.tv_order_num)
    TextView mTvOrderNum;
    @BindView(R.id.tv_order_trend)
    TextView mTvOrderTrend;
    @BindView(R.id.tv_order_rank)
    TextView mTvOrderRank;
    @BindView(R.id.rcy_rank)
    RecyclerView mRcyRank;
    @BindView(R.id.tv_bar)
    TextView mTvBar;
    @BindView(R.id.tv_today_order_num)
    TextView mTvTodayNum;
    @BindView(R.id.tv_week_order_num)
    TextView mTvWeekNum;
    @BindView(R.id.tv_today_up_down)
    ShapeTextView mTvTodayUpDown;
    @BindView(R.id.tv_week_up_down)
    ShapeTextView mTvWeekUpDown;
    @BindView(R.id.tv_turnover)
    TextView mTvtrunover;
    @BindView(R.id.tv_week)
    TextView mTvWeek;
    @BindView(R.id.web_frame)
    LinearLayout webFrame;
    private CustomWebView webView;
    private SpannableStringBuilder passer;
    private OrderDataAdapter orderDataAdapter;
    private Handler mHandler = new Handler();
    private String data;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, OrderDataActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerOrderDataComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_order_data; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        setStatusBarHeight(mTvBar);
        mToolbarTitle.setText("订单数据");

        initListener();
    }


    @SuppressLint("JavascriptInterface")
    public void initListener() {
        initTextSpan(mTvOrderTrend, "订单实时走势 ");
        initTextSpan(mTvOrderRank, "茶饮销售排行 ");
        mRcyRank.setHasFixedSize(true);
        mRcyRank.setLayoutManager(new LinearLayoutManager(this));
        orderDataAdapter = new OrderDataAdapter(new ArrayList<>());
        mRcyRank.setAdapter(orderDataAdapter);

        webView = new CustomWebView(this);
        webView.setVerticalScrollBarEnabled(false);
        webView.setWebLoadListener(this);
        webView.enAbleDownLoad(this);
        webView.setFocusable(false);
        webView.setFocusableInTouchMode(false);


        if (mPresenter != null) {
            mPresenter.statisticorder(Preferences.getShopId());
        }
    }


    @SuppressLint("ResourceType")
    @Override
    public void OnOrderDataSuccess(ShopStaticOrderEntity entity) {
        if (entity != null) {
            passer = SpannableUtil.getBuilder(this, StringUtil.frontCommaValue(entity.getToday_total())).append("单").setTextSize(17).build();
            mTvOrderNum.setText(passer);
            mTvTodayNum.setText(StringUtil.frontCommaValue(entity.getToday_total()));
            mTvWeekNum.setText(StringUtil.frontCommaValue(entity.getThis_week_total()));
            mTvtrunover.setText("今日营业额：¥" + StringUtil.frontCommaValue(entity.getTurnover()));
            mTvWeek.setText("本周营业额：¥" + StringUtil.frontCommaValue(entity.getThis_week_turnover()));
            if (entity.getToday_ratio() >= 0) {
                mTvTodayUpDown.getShapeDrawableBuilder().setSolidColor(getColor(R.color.color_c2_19)).intoBackground();
                mTvTodayUpDown.getTextColorBuilder().setTextColor(getColor(R.color.color_c2)).intoTextColor();
                mTvTodayUpDown.setText("+" + entity.getToday_ratio() * 100 + "%");
            } else {
                mTvTodayUpDown.getShapeDrawableBuilder().setSolidColor(getColor(R.color.color_42_19)).intoBackground();
                mTvTodayUpDown.getTextColorBuilder().setTextColor(getColor(R.color.color_42c)).intoTextColor();
                mTvTodayUpDown.setText(entity.getToday_ratio() * 100 + "%");
            }
            if (entity.getThis_week_ratio() >= 0) {
                mTvWeekUpDown.getShapeDrawableBuilder().setSolidColor(getColor(R.color.color_c2_19)).intoBackground();
                mTvWeekUpDown.getTextColorBuilder().setTextColor(getColor(R.color.color_c2)).intoTextColor();
                mTvWeekUpDown.setText("+" + entity.getThis_week_ratio() * 100 + "%");
            } else {
                mTvWeekUpDown.getShapeDrawableBuilder().setSolidColor(getColor(R.color.color_42_19)).intoBackground();
                mTvWeekUpDown.getTextColorBuilder().setTextColor(getColor(R.color.color_42c)).intoTextColor();
                mTvWeekUpDown.setText(entity.getThis_week_ratio() * 100 + "%");
            }
            List<ShopStaticOrderEntity.RankingBean> rankingBeanList = entity.getRanking();
            orderDataAdapter.setData(rankingBeanList);
            data = GsonUtil.toJson(entity.getTrending());
            webView.loadUrl(WEB_LINECHART_BASEURL);
            webFrame.addView(webView);

        }
    }


    @Override
    public void onNetError() {

    }

    public Activity getActivity() {
        return this;
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


    private void initTextSpan(TextView textView, String textContent) {
        SpannableStringBuilder mSpannableStringBuilder = new SpannableStringBuilder(textContent);
        ThickLineTextSpan mThickLineTextSpan = new ThickLineTextSpan(this);
        mSpannableStringBuilder.setSpan(mThickLineTextSpan, 0, textContent.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(mSpannableStringBuilder);
    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void onLoadError() {

    }

    @Override
    public void onLoadStart() {

    }

    @Override
    public void onLoadFinish() {
        Log.e("111111", data);
        webView.loadUrl("javascript:setChartData('" + data + "')");
    }

    @Override
    public void startProgress(int newProgress) {

    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.destroy();
        }
        super.onDestroy();
    }
}
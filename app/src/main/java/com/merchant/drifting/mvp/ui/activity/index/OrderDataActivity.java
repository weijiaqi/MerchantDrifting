package com.merchant.drifting.mvp.ui.activity.index;

import android.annotation.SuppressLint;
import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import com.merchant.drifting.R;
import com.merchant.drifting.di.component.DaggerOrderDataComponent;
import com.merchant.drifting.mvp.contract.OrderDataContract;
import com.merchant.drifting.mvp.model.entity.OrderDataEntity;
import com.merchant.drifting.mvp.model.entity.OrderRecordEntity;
import com.merchant.drifting.mvp.presenter.OrderDataPresenter;
import com.merchant.drifting.mvp.ui.adapter.OrderDataAdapter;
import com.merchant.drifting.util.ClickUtil;
import com.merchant.drifting.util.SpannableUtil;
import com.merchant.drifting.util.StringUtil;
import com.merchant.drifting.view.ThickLineTextSpan;

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
public class OrderDataActivity extends BaseActivity<OrderDataPresenter> implements OrderDataContract.View {
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
    private SpannableStringBuilder passer;
    private OrderDataAdapter orderDataAdapter;

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

    @SuppressLint("ResourceType")
    public void initListener() {
        initTextSpan(mTvOrderTrend, "订单实时走势 ");
        initTextSpan(mTvOrderRank, "茶饮销售排行 ");
        passer = SpannableUtil.getBuilder(this, StringUtil.frontCommaValue(8076)).append("单").setTextSize(17).build();
        mTvOrderNum.setText(passer);
        mTvTodayNum.setText(StringUtil.frontCommaValue(8078));
        mTvWeekNum.setText(StringUtil.frontCommaValue(18262));
        mRcyRank.setHasFixedSize(true);
        mRcyRank.setLayoutManager(new LinearLayoutManager(this));
        orderDataAdapter = new OrderDataAdapter(new ArrayList<>());
        mRcyRank.setAdapter(orderDataAdapter);
        orderDataAdapter.setData(getData());
    }


    public List<OrderDataEntity> getData() {
        List<OrderDataEntity> list = new ArrayList<>();
        list.add(new OrderDataEntity("1"));
        list.add(new OrderDataEntity("2"));
        list.add(new OrderDataEntity("3"));
        list.add(new OrderDataEntity("4"));
        list.add(new OrderDataEntity("5"));
        return list;
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
}
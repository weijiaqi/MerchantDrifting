package com.merchant.drifting.mvp.ui.activity.index;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import com.jess.arms.utils.SystemUtil;
import com.merchant.drifting.R;
import com.merchant.drifting.di.component.DaggerRunningRecordsComponent;
import com.merchant.drifting.mvp.contract.RunningRecordsContract;
import com.merchant.drifting.mvp.model.entity.BusinessBillEntity;
import com.merchant.drifting.mvp.presenter.RunningRecordsPresenter;
import com.merchant.drifting.mvp.ui.adapter.RunningRecordsAdapter;
import com.merchant.drifting.picker.DateEntity;
import com.merchant.drifting.picker.DateMode;
import com.merchant.drifting.picker.DatePicker;
import com.merchant.drifting.picker.OnDatePickedListener;
import com.merchant.drifting.picker.OnDatePickedSelectedListener;
import com.merchant.drifting.picker.UnitDateFormatter;
import com.merchant.drifting.picker.dialog.DialogColor;
import com.merchant.drifting.picker.dialog.DialogConfig;
import com.merchant.drifting.picker.dialog.DialogStyle;
import com.merchant.drifting.picker.widget.DateWheelLayout;
import com.merchant.drifting.storageinfo.Preferences;
import com.merchant.drifting.util.ClickUtil;
import com.merchant.drifting.util.DateUtil;
import com.merchant.drifting.util.StringUtil;
import com.merchant.drifting.util.ToastUtil;
import com.merchant.drifting.util.ViewUtil;
import com.merchant.drifting.util.request.RequestUtil;
import com.rb.core.xrecycleview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created on 2022/06/30 10:35
 *
 * @author 流水记录
 * module name is RunningRecordsActivity
 */
public class RunningRecordsActivity extends BaseActivity<RunningRecordsPresenter> implements RunningRecordsContract.View, OnDatePickedListener, OnDatePickedSelectedListener, XRecyclerView.LoadingListener {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.tv_bar)
    TextView mTvBar;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.rcy_flowing_water)
    XRecyclerView mRcyFlowingWater;
    @BindView(R.id.fl_container)
    FrameLayout mFlState;
    @BindView(R.id.tv_balance)
    TextView mTvBalance;
    private int status = 3;
    private DatePicker picker;
    private DateWheelLayout wheelLayout;
    private int mPage = 1;
    private int limit = 10;

    private String date;
    private RunningRecordsAdapter adapter;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, RunningRecordsActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRunningRecordsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_running_records; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(false);
        setStatusBarHeight(mTvBar);
        mToolbarTitle.setText("流水记录");
        initListener();
    }

    public void initListener() {
        RequestUtil.create().balance(Preferences.getShopId(),entity -> {
            if (entity != null & entity.getCode() == 200) {
                mTvBalance.setText("¥ " + StringUtil.frontCDecimalValue(entity.getData().getBalance()));
            }
        });

        DialogConfig.setDialogStyle(DialogStyle.One);

        mRcyFlowingWater.setLayoutManager(new LinearLayoutManager(this));
        mRcyFlowingWater.setLoadingListener(this);
        adapter = new RunningRecordsAdapter(new ArrayList<>());
        mRcyFlowingWater.setAdapter(adapter);
        setDate(DateUtil.unxiToDateYMDMD());
    }


    public void setDate(String date) {
        mTvTime.setText(date);
        getData(status, date, mPage, true);
    }

    public void getData(  int status, String date, int mPage, boolean loadType) {
        if (mPresenter != null) {
            mPresenter.businessbill( Preferences.getShopId(),  status, date, mPage, limit, loadType);
        }
    }

    @Override
    public void onRefresh() {
        mPage = 1;
        getData(status, date, mPage, true);
    }

    @Override
    public void onLoadMore() {
        getData(status, date, mPage, true);
    }

    @Override
    public void onloadStart() {
        if (adapter.getDatas() == null || adapter.getDatas().size() == 0) {
            ViewUtil.create().setAnimation(this, mFlState);
        }
    }

    @Override
    public void onRunningRecordSuccess(BusinessBillEntity entity, boolean isNotData) {
        List<BusinessBillEntity.ListBean> list = entity.getList();
        if (list != null && list.size() > 0) {
            if (isNotData) {
                mPage = 2;
                adapter.setData(list);
            } else {
                mPage++;
                adapter.addData(list);
            }
        }
    }

    @Override
    public void loadFinish(boolean loadType, boolean isNotData) {
        if (mRcyFlowingWater == null) {
            return;
        }
        if (!loadType && isNotData) {
            mRcyFlowingWater.loadEndLine();
        } else {
            mRcyFlowingWater.refreshEndComplete();
        }
    }

    @Override
    public void loadState(int dataState) {
        if (dataState == ViewUtil.NOT_DATA) {
            ViewUtil.create().setView(this, mFlState, ViewUtil.NOT_DATA);
        } else if (dataState == ViewUtil.NOT_SERVER) {
            ViewUtil.create().setView(this, mFlState, ViewUtil.NOT_SERVER);
        } else if (dataState == ViewUtil.NOT_NETWORK) {
            ViewUtil.create().setView(this, mFlState, ViewUtil.NOT_NETWORK);
        } else {
            ViewUtil.create().setView(mFlState);
        }
    }

    @Override
    public void onNetError() {

    }

    public Activity getActivity() {
        return this;
    }

    @OnClick({R.id.toolbar_back, R.id.rl_picker})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.rl_picker:
                    setSelectPiker();
                    break;
            }
        }
    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtil.showToast(message);
    }




    public void setSelectPiker() {
        DialogConfig.setDialogType(status);
        picker = new DatePicker(this);
        wheelLayout = picker.getWheelLayout();
        wheelLayout.setDateMode(status==3?DateMode.YEAR_MONTH_DAY:DateMode.YEAR_MONTH);
        wheelLayout.setDateFormatter(new UnitDateFormatter());
        wheelLayout.setRange(DateEntity.target(2021, 01, 01), DateEntity.target(2050, 12, 31), DateEntity.today());
        wheelLayout.setCurtainEnabled(true);
        wheelLayout.setIndicatorEnabled(true);
        wheelLayout.setSelectedTextSize(14 * getResources().getDisplayMetrics().scaledDensity);
        wheelLayout.setTextSize(14 * getResources().getDisplayMetrics().scaledDensity);
        picker.setOnDatePickedListener(this);
        picker.setOnDatePickedSelectedListener(this);
        picker.show();
    }


    @Override
    public void onDatePicked(String year, String month, String day) {
        if (status == 3) {
            date = year + "/" + month + "/" + day;
        } else {
            date = year + "/" + month;
        }
        if (adapter != null) {
            adapter.clearData();
            mPage = 1;
            setDate(date);
        }
    }


    @Override
    public void onDatePicked(int type) {
        status = type;
        wheelLayout.setDateMode(status == 3 ? DateMode.YEAR_MONTH_DAY : DateMode.YEAR_MONTH);
    }

}
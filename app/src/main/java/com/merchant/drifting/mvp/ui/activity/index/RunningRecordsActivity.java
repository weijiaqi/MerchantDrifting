package com.merchant.drifting.mvp.ui.activity.index;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import com.merchant.drifting.R;
import com.merchant.drifting.di.component.DaggerRunningRecordsComponent;
import com.merchant.drifting.mvp.contract.RunningRecordsContract;
import com.merchant.drifting.mvp.presenter.RunningRecordsPresenter;
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
import com.merchant.drifting.util.ClickUtil;
import com.merchant.drifting.util.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created on 2022/06/30 10:35
 *
 * @author 流水记录
 * module name is RunningRecordsActivity
 */
public class RunningRecordsActivity extends BaseActivity<RunningRecordsPresenter> implements RunningRecordsContract.View, OnDatePickedListener, OnDatePickedSelectedListener {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.tv_bar)
    TextView mTvBar;

    private int status=1;
    private DatePicker picker;
    private DateWheelLayout wheelLayout;

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
        DialogConfig.setDialogStyle(DialogStyle.Default);
        DialogConfig.setDialogColor(new DialogColor()
                .cancelTextColor(0xFF0099CC)
                .okTextColor(0xFF0099CC));
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

    }

    @Override
    public void onDatePicked(int year, int month, int day) {
        if (status==1){
            ToastUtil.showToast(year+"---"+month+"---"+day);
        }else {
            ToastUtil.showToast(year+"---"+month);
        }
    }

    @Override
    public void onDatePicked(int type) {
        status=type;
        wheelLayout.setDateMode(status == 1 ? DateMode.YEAR_MONTH_DAY : DateMode.YEAR_MONTH);
    }


    public void setSelectPiker() {
        picker = new DatePicker(this);
        wheelLayout = picker.getWheelLayout();
        wheelLayout.setDateMode(DateMode.YEAR_MONTH_DAY );
        wheelLayout.setDateFormatter(new UnitDateFormatter());
        wheelLayout.setRange(DateEntity.target(2021, 1, 1), DateEntity.target(2050, 12, 31), DateEntity.today());
        wheelLayout.setCurtainEnabled(true);
        wheelLayout.setIndicatorEnabled(true);
        wheelLayout.setSelectedTextSize(14 * getResources().getDisplayMetrics().scaledDensity);
        wheelLayout.setTextSize(14 * getResources().getDisplayMetrics().scaledDensity);
        picker.setOnDatePickedListener(this);
        picker.setOnDatePickedSelectedListener(this);
        picker.show();
    }
}
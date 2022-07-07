package com.merchant.drifting.mvp.ui.activity.index;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;


import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.delegate.IFragment;
import com.jess.arms.di.component.AppComponent;

import com.merchant.drifting.R;
import com.merchant.drifting.data.entity.TimeEntity;
import com.merchant.drifting.di.component.DaggerStoreManagementComponent;
import com.merchant.drifting.mvp.contract.StoreManagementContract;
import com.merchant.drifting.mvp.model.entity.ShopInfoEntity;
import com.merchant.drifting.mvp.presenter.StoreManagementPresenter;
import com.merchant.drifting.picker.OnTimePickedListener;
import com.merchant.drifting.picker.TimeMode;
import com.merchant.drifting.picker.TimePicker;
import com.merchant.drifting.picker.UnitTimeFormatter;
import com.merchant.drifting.picker.dialog.DialogConfig;
import com.merchant.drifting.picker.dialog.DialogStyle;
import com.merchant.drifting.picker.widget.TimeWheelLayout;
import com.merchant.drifting.storageinfo.Preferences;
import com.merchant.drifting.util.ClickUtil;
import com.merchant.drifting.util.ToastUtil;
import com.merchant.drifting.util.ViewUtil;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/06/28 15:56
 *
 * @author 店铺管理
 * module name is StoreManagementActivity
 */
public class StoreManagementActivity extends BaseActivity<StoreManagementPresenter> implements StoreManagementContract.View, OnTimePickedListener {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    private String opening, opening_end;

    private boolean selected;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, StoreManagementActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerStoreManagementComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_store_management; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(false);
        mToolbarTitle.setText("店铺管理");
        DialogConfig.setDialogStyle(DialogStyle.Two);

        initListener();
    }

    public void initListener() {
        if (mPresenter != null) {
            mPresenter.shopinfo(Preferences.getShopId());
        }
    }

    @OnClick({R.id.toolbar_back, R.id.rl_selected})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.rl_selected:
                    opening = "";
                    selected = true;
                    showPicker("开始营业时间");
                    break;
            }
        }
    }


    public void showPicker(String content) {
        TimePicker picker = new TimePicker(this);
        picker.setTitle(content);
        TimeWheelLayout wheelLayout = picker.getWheelLayout();
        wheelLayout.setTimeMode(TimeMode.HOUR_24_NO_SECOND);
        wheelLayout.setTimeFormatter(new UnitTimeFormatter());
        wheelLayout.setTextSize(14 * getResources().getDisplayMetrics().scaledDensity);
        wheelLayout.setDefaultValue(TimeEntity.now());
        wheelLayout.setResetWhenLinkage(false);
        picker.setOnTimePickedListener(this);
        picker.show();
    }

    @Override
    public void OnShopInfoSuccess(ShopInfoEntity entity) {
        if (entity != null) {
            mTvPhone.setText(entity.getMobile());
            mTvTime.setText(entity.getOpening() + "-" + entity.getOpening_end());
        }
    }

    @Override
    public void SetOpeningTimeSuccess() {
        showMessage("保存成功");
    }

    @Override
    public void onNetError() {

    }


    @Override
    public void showLoading() {
        ViewUtil.create().show(this);
    }

    @Override
    public void hideLoading() {
        ViewUtil.create().dismiss();
    }


    public Activity getActivity() {
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtil.showToast(message);
    }

    @Override
    public void onTimePicked(String hour, String minute, String second) {
        if (!TextUtils.isEmpty(opening)) {
            opening_end = hour + ":" + minute;
            mTvTime.setText(opening + "-" + opening_end);
            if (mPresenter != null) {
                showLoading();
                mPresenter.setOpeningTime(Preferences.getShopId(), opening, opening_end);
            }
        } else {
            opening = hour + ":" + minute;
        }
        if (selected) {
            selected = false;
            showPicker("结束营业时间");
        }
    }
}
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
import com.merchant.drifting.di.component.DaggerStoreManagementComponent;
import com.merchant.drifting.mvp.contract.StoreManagementContract;
import com.merchant.drifting.mvp.presenter.StoreManagementPresenter;
import com.merchant.drifting.util.ClickUtil;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/06/28 15:56
 * @author 店铺管理
 * module name is StoreManagementActivity
 */
public class StoreManagementActivity extends BaseActivity<StoreManagementPresenter> implements StoreManagementContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
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
    public int initView(@Nullable Bundle savedInstanceState){
        return R.layout.activity_store_management; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(false);
        mToolbarTitle.setText("店铺管理");
        initListener();
    }

    public void initListener() {

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

    public Activity getActivity(){
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {

    }
}
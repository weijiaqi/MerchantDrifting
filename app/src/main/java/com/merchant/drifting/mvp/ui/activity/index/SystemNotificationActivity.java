package com.merchant.drifting.mvp.ui.activity.index;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.merchant.drifting.R;
import com.merchant.drifting.di.component.DaggerSystemNotificationComponent;
import com.merchant.drifting.mvp.contract.SystemNotificationContract;
import com.merchant.drifting.mvp.model.entity.SystemNotificationEntity;
import com.merchant.drifting.mvp.presenter.SystemNotificationPresenter;
import com.merchant.drifting.mvp.ui.adapter.SystemNotificationAdapter;
import com.merchant.drifting.util.ClickUtil;
import com.rb.core.xrecycleview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/06/24 17:05
 *
 * @author 通知
 * module name is SystemNotificationActivity
 */
public class SystemNotificationActivity extends BaseActivity<SystemNotificationPresenter> implements SystemNotificationContract.View, XRecyclerView.LoadingListener {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.rcy_public)
    XRecyclerView mRcyPublic;
    private SystemNotificationAdapter systemNotificationAdapter;
    private static String EXTRA_TYPE = "extra_type";
    private int type;

    public static void start(Context context, int type, boolean closePage) {
        Intent intent = new Intent(context, SystemNotificationActivity.class);
        intent.putExtra(EXTRA_TYPE, type);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSystemNotificationComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_refresh; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(false);
        if (getIntent() != null) {
            type = getIntent().getExtras().getInt(EXTRA_TYPE);
        }
        mToolbarTitle.setText("系统通知");
        initListener();
    }

    public void initListener() {
        mRcyPublic.setLayoutManager(new LinearLayoutManager(this));
        mRcyPublic.setLoadingListener(this);
        systemNotificationAdapter = new SystemNotificationAdapter(new ArrayList<>());
        mRcyPublic.setAdapter(systemNotificationAdapter);
        systemNotificationAdapter.setData(getData());
    }


    public List<SystemNotificationEntity> getData() {
        List<SystemNotificationEntity> list = new ArrayList<>();
        list.add(new SystemNotificationEntity("1"));
        list.add(new SystemNotificationEntity("2"));
        list.add(new SystemNotificationEntity("3"));
        list.add(new SystemNotificationEntity("4"));
        return list;
    }


    public Activity getActivity() {
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

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

}
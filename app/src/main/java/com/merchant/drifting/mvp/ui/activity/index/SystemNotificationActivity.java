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
import com.merchant.drifting.R;
import com.merchant.drifting.di.component.DaggerSystemNotificationComponent;
import com.merchant.drifting.mvp.contract.SystemNotificationContract;
import com.merchant.drifting.mvp.model.entity.SystemNotificationEntity;
import com.merchant.drifting.mvp.presenter.SystemNotificationPresenter;
import com.merchant.drifting.mvp.ui.adapter.SystemNotificationAdapter;
import com.merchant.drifting.util.ClickUtil;
import com.merchant.drifting.util.ToastUtil;
import com.merchant.drifting.util.ViewUtil;
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
    @BindView(R.id.fl_container)
    FrameLayout mFlState;
    private SystemNotificationAdapter systemNotificationAdapter;
    private static String EXTRA_TYPE = "extra_type";
    private int type;
    private int mPage = 1;
    private int limit = 10;

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
        mToolbarTitle.setText(type==1?"系统通知":"订单通知");
        initListener();
    }

    public void initListener() {
        mRcyPublic.setLayoutManager(new LinearLayoutManager(this));
        mRcyPublic.setLoadingListener(this);
        systemNotificationAdapter = new SystemNotificationAdapter(new ArrayList<>(),type);
        mRcyPublic.setAdapter(systemNotificationAdapter);
        getData(mPage, true);
    }


    public void getData(int mPage, boolean loadType) {
        if (mPresenter != null) {
            mPresenter.messagelist(type, mPage, limit, loadType);
        }
    }


    @Override
    public void onRefresh() {
        mPage = 1;
        getData(mPage, true);
    }

    @Override
    public void onLoadMore() {
        getData(mPage, false);
    }

    @Override
    public void onloadStart() {
        if (systemNotificationAdapter.getDatas() == null || systemNotificationAdapter.getDatas().size() == 0) {
            ViewUtil.create().setAnimation(this, mFlState);
        }
    }

    @Override
    public void onPathDetailSuccess(SystemNotificationEntity entity, boolean isNotData) {
        List<SystemNotificationEntity.ListBean> list = entity.getList();
        if (list != null && list.size() > 0) {
            if (isNotData) {
                mPage = 2;
                systemNotificationAdapter.setData(list);
            } else {
                mPage++;
                systemNotificationAdapter.addData(list);
            }
        }
    }

    @Override
    public void loadFinish(boolean loadType, boolean isNotData) {
        if (mRcyPublic == null) {
            return;
        }
        if (!loadType && isNotData) {
            mRcyPublic.loadEndLine();
        } else {
            mRcyPublic.refreshEndComplete();
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

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtil.showToast(message);
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
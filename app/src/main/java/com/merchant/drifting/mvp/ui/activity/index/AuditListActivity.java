package com.merchant.drifting.mvp.ui.activity.index;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import com.merchant.drifting.R;
import com.merchant.drifting.data.entity.ApplicationRecordEntity;
import com.merchant.drifting.data.event.AddItemEvent;

import com.merchant.drifting.di.component.DaggerAuditListComponent;
import com.merchant.drifting.mvp.contract.AuditListContract;
import com.merchant.drifting.mvp.model.entity.AuditingEntity;
import com.merchant.drifting.mvp.presenter.AuditListPresenter;
import com.merchant.drifting.mvp.ui.adapter.AuditListAdapter;
import com.merchant.drifting.mvp.ui.adapter.AuditListPagerAdapter;
import com.merchant.drifting.storageinfo.Preferences;
import com.merchant.drifting.util.ClickUtil;
import com.merchant.drifting.util.TextUtil;
import com.merchant.drifting.util.ViewUtil;
import com.rb.core.tab.view.indicator.IndicatorViewPager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/07/06 09:47
 *
 * @author 审核列表
 * module name is AuditListActivityActivity
 */
public class AuditListActivity extends BaseActivity<AuditListPresenter> implements AuditListContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.rcy_record)
    RecyclerView mRcyRecord;
    @BindView(R.id.fl_container)
    FrameLayout mFlState;

//    @BindView(R.id.indicator_tablayout)
//    ScrollIndicatorView mIndicatorTablayout;
//    @BindView(R.id.view_pager)
//    ViewPager viewPager;

    private IndicatorViewPager indicatorViewPager;
    private AuditListAdapter adapter;
    private List<ApplicationRecordEntity> mTabTitle;

    private AuditListPagerAdapter auditListPagerAdapter;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, AuditListActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAuditListComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_audit_list; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(false);
        mToolbarTitle.setText("审核列表");
        initListener();
    }

    public void initListener() {
//        getData();
//        mIndicatorTablayout.setOnTransitionListener(new OnTransitionTextListener().setValueFromRes(this,
//                R.color.color_42, R.color.color_97, R.dimen.text_size, R.dimen.text_size));
//        indicatorViewPager = new IndicatorViewPager(mIndicatorTablayout, viewPager);
//        indicatorViewPager.setIndicatorScrollBar(new LayoutBar(this, R.layout.layout_indicator_view));
//        adapter = new AuditListAdapter(getSupportFragmentManager());
//        adapter.setData(mTabTitle);
//        if (indicatorViewPager != null) {
//            indicatorViewPager.setAdapter(adapter);
//            if (adapter != null && adapter.getCount() > 0) {
//                indicatorViewPager.setCurrentItem(0, false);
//            }
//        }

        mRcyRecord.setLayoutManager(new LinearLayoutManager(this));
        auditListPagerAdapter = new AuditListPagerAdapter(new ArrayList<>());
        mRcyRecord.setAdapter(auditListPagerAdapter);
        if (mPresenter != null) {
            mPresenter.auditing(Preferences.getShopId(), 0);
        }
    }

    public void getData() {
        mTabTitle = new ArrayList();
        mTabTitle.clear();
        mTabTitle.add(new ApplicationRecordEntity("审核中", 1));
        mTabTitle.add(new ApplicationRecordEntity("已通过", 2));
        mTabTitle.add(new ApplicationRecordEntity("已驳回", 3));
    }


    @Override
    public void onloadStart() {
        if (auditListPagerAdapter.getDatas() == null || auditListPagerAdapter.getDatas().size() == 0) {
            ViewUtil.create().setAnimation(this, mFlState);
        }
    }

    @Override
    public void loadState(int dataState) {
        if (auditListPagerAdapter.getDatas() == null || auditListPagerAdapter.getDatas().size() == 0) {
            if (dataState == ViewUtil.NOT_DATA) {
                ViewUtil.create().setView(this, mFlState, ViewUtil.NOT_DATA);
            } else if (dataState == ViewUtil.NOT_SERVER) {
                ViewUtil.create().setView(this, mFlState, ViewUtil.NOT_SERVER);
            } else if (dataState == ViewUtil.NOT_NETWORK) {
                ViewUtil.create().setView(this, mFlState, ViewUtil.NOT_NETWORK);
            } else {
                ViewUtil.create().setView(mFlState);
            }
        } else {
            ViewUtil.create().setView(mFlState);
        }
    }

    @Override
    public void OnAuditingSuccess(List<AuditingEntity> list) {
        if (list != null && list.size() > 0) {
            auditListPagerAdapter.setData(list);
        }
    }

    @Override
    public void onNetError() {

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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void AddItemEvent(AddItemEvent event) {
        if (event != null) {
            for (int i = 0; i <= auditListPagerAdapter.getDatas().size(); i++) {
                if (TextUtils.equals(event.getSku_code(), auditListPagerAdapter.getDatas().get(i).getSku_code())) {
                    auditListPagerAdapter.getDatas().get(i).setAudit(1);
                    auditListPagerAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    public Activity getActivity() {
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {

    }
}
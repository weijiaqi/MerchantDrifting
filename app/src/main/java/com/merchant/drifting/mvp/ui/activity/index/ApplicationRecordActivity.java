package com.merchant.drifting.mvp.ui.activity.index;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import com.merchant.drifting.R;
import com.merchant.drifting.data.entity.ApplicationRecordEntity;
import com.merchant.drifting.di.component.DaggerApplicationRecordComponent;
import com.merchant.drifting.mvp.contract.ApplicationRecordContract;
import com.merchant.drifting.mvp.model.entity.ShopApplyLogEntity;
import com.merchant.drifting.mvp.presenter.ApplicationRecordPresenter;
import com.merchant.drifting.mvp.ui.adapter.ApplicationRecordAdapter;
import com.merchant.drifting.mvp.ui.adapter.RecordAdapter;
import com.merchant.drifting.util.ClickUtil;
import com.merchant.drifting.util.ViewUtil;
import com.rb.core.tab.view.indicator.IndicatorViewPager;
import com.rb.core.tab.view.indicator.ScrollIndicatorView;
import com.rb.core.tab.view.indicator.slidebar.LayoutBar;
import com.rb.core.tab.view.indicator.transition.OnTransitionTextListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/06/24 17:41
 *
 * @author 申请记录
 * module name is ApplicationRecordActivity
 */
public class ApplicationRecordActivity extends BaseActivity<ApplicationRecordPresenter> implements ApplicationRecordContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.rcy_record)
    RecyclerView mRcyRecord;
    @BindView(R.id.fl_container)
    FrameLayout mFlState;
    private RecordAdapter recordAdapter;
//    @BindView(R.id.indicator_tablayout)
//    ScrollIndicatorView mIndicatorTablayout;
//    @BindView(R.id.view_pager)
//    ViewPager viewPager;
    private IndicatorViewPager indicatorViewPager;
    private ApplicationRecordAdapter adapter;
    private List<ApplicationRecordEntity> mTabTitle;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, ApplicationRecordActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerApplicationRecordComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_application_record; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(false);
        mToolbarTitle.setText("申请记录");
        initListener();
    }

    public void initListener() {
//        getData();
//        mIndicatorTablayout.setOnTransitionListener(new OnTransitionTextListener().setValueFromRes(this,
//                R.color.color_42, R.color.color_97, R.dimen.text_size, R.dimen.text_size));
//        indicatorViewPager = new IndicatorViewPager(mIndicatorTablayout, viewPager);
//        indicatorViewPager.setIndicatorScrollBar(new LayoutBar(this, R.layout.layout_indicator_view));
//       adapter=new ApplicationRecordAdapter(getSupportFragmentManager());
//        adapter.setData(mTabTitle);
//        if (indicatorViewPager != null) {
//            indicatorViewPager.setAdapter(adapter);
//            if (adapter != null && adapter.getCount() > 0) {
//                indicatorViewPager.setCurrentItem(0, false);
//            }
//        }

        mRcyRecord.setLayoutManager(new LinearLayoutManager(this));
        recordAdapter = new RecordAdapter(new ArrayList<>());
        mRcyRecord.setAdapter(recordAdapter);
        if (mPresenter != null) {
            mPresenter.shopapplyLog(-1);
        }
    }

    public void getData() {
        mTabTitle = new ArrayList();
        mTabTitle.clear();
        mTabTitle.add(new ApplicationRecordEntity("全部", -1));
        mTabTitle.add(new ApplicationRecordEntity("审核中", 0));
        mTabTitle.add(new ApplicationRecordEntity("已完结", 1));
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

    @Override
    public void onloadStart() {
        if (recordAdapter.getDatas() == null || recordAdapter.getDatas().size() == 0) {
            ViewUtil.create().setAnimation(this, mFlState);
        }
    }

    @Override
    public void loadState(int dataState) {
        if (recordAdapter.getDatas() == null || recordAdapter.getDatas().size() == 0) {
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
    public void OnApplyLog(List<ShopApplyLogEntity> list) {
        if (list != null && list.size() > 0) {
            recordAdapter.setData(list);
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

    }
}
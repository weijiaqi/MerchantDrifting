package com.merchant.drifting.mvp.ui.activity.home;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.merchant.drifting.R;
import com.merchant.drifting.di.component.DaggerHomeComponent;
import com.merchant.drifting.mvp.contract.HomeContract;
import com.merchant.drifting.mvp.presenter.HomePresenter;
import com.merchant.drifting.mvp.ui.adapter.HomeTabAdapter;
import com.merchant.drifting.mvp.ui.adapter.OrderRecordAdapter;
import com.merchant.drifting.mvp.ui.fragment.IndexFragment;
import com.merchant.drifting.mvp.ui.fragment.MineFragment;
import com.merchant.drifting.mvp.ui.fragment.OrderFragment;
import com.merchant.drifting.mvp.ui.listener.OnTabTransitionTextListener;
import com.merchant.drifting.util.request.RequestUtil;
import com.rb.core.tab.view.indicator.FixedIndicatorView;
import com.rb.core.tab.view.indicator.IndicatorViewPager;
import com.rb.core.tab.view.viewpager.SViewPager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Created on 2022/06/24 11:46
 *
 * @author 主界面
 * module name is HomeActivity
 */
public class HomeActivity extends BaseActivity<HomePresenter> implements HomeContract.View {
    @BindView(R.id.tab_layout)
    FixedIndicatorView mTabLayout;
    @BindView(R.id.vp_home)
    SViewPager mVpHome;
    private List<Fragment> mFragments;
    private IndexFragment indexFragment;
    private OrderFragment orderFragment;
    private MineFragment mineFragment;
    private String[] mTabTitles;
    private int[] mIconList;
    private OnTabTransitionTextListener textListener;
    private IndicatorViewPager mIndicatorViewPager;
    private HomeTabAdapter tabAdapter;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerHomeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_home; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initListener();
    }

    @Override
    protected void initVisible() {
        super.initVisible();
        if (mPresenter != null) {
            mPresenter.getVersionInfo(this);
        }
    }


    public void initListener() {
        initTab();
    }


    public void initTab() {
        mFragments = new ArrayList<>();
        indexFragment = new IndexFragment();
        orderFragment = new OrderFragment();
        mineFragment = new MineFragment();
        mTabTitles = new String[]{"首页", "订单", "我的"};
        mIconList = new int[]{R.drawable.tab_index_selector, R.drawable.tab_order_selector, R.drawable.tab_mine_selector};
        mFragments.add(indexFragment);
        mFragments.add(orderFragment);
        mFragments.add(mineFragment);
        textListener = new OnTabTransitionTextListener();
        mTabLayout.setOnTransitionListener(textListener);
        mVpHome.setCanScroll(false);
        mVpHome.setOffscreenPageLimit(1);
        mIndicatorViewPager = new IndicatorViewPager(mTabLayout, mVpHome);
        tabAdapter = new HomeTabAdapter(this, getSupportFragmentManager(), mTabTitles, mIconList, mFragments);
        mIndicatorViewPager.setAdapter(tabAdapter);

    }

    @Override
    public void finishSuccess() {
        finish();
    }

    public Activity getActivity() {
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            killApp();
        }
        return false;
    }


    public void killApp() {
        if (mPresenter != null) {
            mPresenter.exitBy2Click();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestUtil.create().disDispose();
    }

    /**
     * 公共方法
     * 跳转
     */
    public void skipToCommunity(int index) {
        mIndicatorViewPager.setCurrentItem(index, false);
    }
}
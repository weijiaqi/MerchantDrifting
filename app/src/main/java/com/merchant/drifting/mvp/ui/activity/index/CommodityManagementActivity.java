package com.merchant.drifting.mvp.ui.activity.index;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.merchant.drifting.R;
import com.merchant.drifting.data.entity.ApplicationRecordEntity;
import com.merchant.drifting.data.event.GoodsOnOffEvent;
import com.merchant.drifting.di.component.DaggerCommodityManagementComponent;
import com.merchant.drifting.mvp.contract.CommodityManagementContract;
import com.merchant.drifting.mvp.presenter.CommodityManagementPresenter;
import com.merchant.drifting.mvp.ui.adapter.ApplicationRecordAdapter;
import com.merchant.drifting.mvp.ui.adapter.CommodityManagementAdapter;
import com.merchant.drifting.mvp.ui.fragment.CommodityManagerFragment;
import com.merchant.drifting.util.ClickUtil;
import com.rb.core.tab.view.indicator.IndicatorViewPager;
import com.rb.core.tab.view.indicator.ScrollIndicatorView;
import com.rb.core.tab.view.indicator.slidebar.LayoutBar;
import com.rb.core.tab.view.indicator.transition.OnTransitionTextListener;
import com.rb.core.tab.view.viewpager.SViewPager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/07/01 11:13
 *
 * @author 商品管理
 * module name is CommodityManagementActivity
 */
public class CommodityManagementActivity extends BaseActivity<CommodityManagementPresenter> implements CommodityManagementContract.View {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.iv_right)
    ImageView mIvRight;
    @BindView(R.id.indicator_tablayout)
    ScrollIndicatorView tabLayout;
    @BindView(R.id.view_pager)
    SViewPager viewPager;
    @BindView(R.id.rl_tab)
    RelativeLayout mRlTab;
    @BindView(R.id.rl_tab2)
    RelativeLayout mRlTab2;
    @BindView(R.id.rl_bottoms)
    RelativeLayout mRlBottom;

    private IndicatorViewPager indicatorViewPager;
    private CommodityManagementAdapter adapter;
    private List<ApplicationRecordEntity> mTabTitle;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, CommodityManagementActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCommodityManagementComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_commodity_management; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(false);
        mToolbarTitle.setText("商品管理");
        mIvRight.setVisibility(View.VISIBLE);
        mIvRight.setImageResource(R.drawable.shop_add);
        viewPager.setCanScroll(true);
        initListener();
    }

    public void initListener() {
        getData();
        tabLayout.setOnTransitionListener(new OnTransitionTextListener().setValueFromRes(this,
                R.color.color_42, R.color.color_97, R.dimen.text_size, R.dimen.text_size));
        indicatorViewPager = new IndicatorViewPager(tabLayout, viewPager);
        indicatorViewPager.setIndicatorScrollBar(new LayoutBar(this, R.layout.layout_indicator_view));
        adapter = new CommodityManagementAdapter(getSupportFragmentManager());
        adapter.setData(mTabTitle);
        if (indicatorViewPager != null) {
            indicatorViewPager.setAdapter(adapter);
            if (adapter != null && adapter.getCount() > 0) {
                indicatorViewPager.setCurrentItem(0, false);
            }
        }
    }


    public void getData() {
        mTabTitle = new ArrayList();
        mTabTitle.clear();
        mTabTitle.add(new ApplicationRecordEntity("在售中", 1));
        mTabTitle.add(new ApplicationRecordEntity("已下架", 2));
    }


    public Activity getActivity() {
        return this;
    }


    @OnClick({R.id.toolbar_back, R.id.tv_choice, R.id.tv_cancel, R.id.iv_right})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.tv_choice:  //选择
                    setTab(true);
                    break;
                case R.id.tv_cancel:  //取消
                    setTab(false);
                    break;
                case R.id.iv_right:  //申请商品
                    ApplyGoodsActivity.start(this,false);
                    break;
            }
        }
    }

    public void setTab(boolean status) {
        viewPager.setCanScroll(status ? false : true);
        for (int i = 0; i < tabLayout.getChildCount(); i++) {
            tabLayout.getChildAt(i).setClickable(status ? false : true);
        }

        mRlTab.setVisibility(status ? View.GONE : View.VISIBLE);
        mRlTab2.setVisibility(status ? View.VISIBLE : View.GONE);
        mRlBottom.setVisibility(status ? View.VISIBLE : View.GONE);
        GoodsOnOffEvent goodsOnOffEvent = new GoodsOnOffEvent();
        goodsOnOffEvent.setEdit(status);
        EventBus.getDefault().post(goodsOnOffEvent);
    }


    @Override
    public void showMessage(@NonNull String message) {

    }
}
package com.merchant.drifting.mvp.ui.activity.index;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.merchant.drifting.R;
import com.merchant.drifting.di.component.DaggerApplyGoodsComponent;
import com.merchant.drifting.mvp.contract.ApplyGoodsContract;
import com.merchant.drifting.mvp.model.entity.AvailableAllEntity;
import com.merchant.drifting.mvp.presenter.ApplyGoodsPresenter;
import com.merchant.drifting.mvp.ui.adapter.ApplyGoodsAdapter;
import com.merchant.drifting.mvp.ui.adapter.AuditListAdapter;
import com.merchant.drifting.storageinfo.Preferences;
import com.merchant.drifting.util.ClickUtil;
import com.merchant.drifting.util.GlideUtil;
import com.merchant.drifting.util.ViewUtil;
import com.merchant.drifting.view.ScaleInTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/07/02 18:12
 *
 * @author 申请商品
 * module name is ApplyGoodsActivity
 */
public class ApplyGoodsActivity extends BaseActivity<ApplyGoodsPresenter> implements ApplyGoodsContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.frame)
    FrameLayout frame;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.vp_img)
    ViewPager mViewPager;
    @BindView(R.id.rcy_shop)
    RecyclerView mRcyShop;
    @BindView(R.id.fl_container)
    FrameLayout mFlState;
    @BindView(R.id.iv_right_word)
    TextView mIvRightWord;
    private List<AvailableAllEntity> list;
    private ApplyGoodsAdapter applyGoodsAdapter;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, ApplyGoodsActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerApplyGoodsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_apply_goods; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(false);
        mToolbarTitle.setText("申请商品");
        mIvRightWord.setVisibility(View.VISIBLE);
        mIvRightWord.setText("审核列表");
        mIvRightWord.setTextColor(getColor(R.color.color_42));
        initListener();
    }

    public void initListener() {

        onloadStart();
        if (mPresenter != null) {
            mPresenter.availableAll(Preferences.getShopId());
        }
        applyGoodsAdapter = new ApplyGoodsAdapter(new ArrayList<>());
        mRcyShop.setLayoutManager(new GridLayoutManager(this, 2));
        mRcyShop.setAdapter(applyGoodsAdapter);


        frame.setOnTouchListener((view, motionEvent) -> mViewPager.onTouchEvent(motionEvent));
    }


    @Override
    public void OnAvailableAllSuccess(List<AvailableAllEntity> entity) {
        if (entity != null && entity.size() > 0) {
            loadState(ViewUtil.NOT_NULL);

            list = entity;
            MyAdapter myAdapter = new MyAdapter();
            mViewPager.setOffscreenPageLimit(list.size());
            mViewPager.setClipChildren(false);
            mViewPager.setAdapter(myAdapter);
            mViewPager.setCurrentItem(list.size() * 100);
            mViewPager.setPageTransformer(true, new ScaleInTransformer());

            mTvTitle.setText(list.get(0).getName());
            applyGoodsAdapter.setData(list.get(0).getCups());
            mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    mTvTitle.setText(list.get(position % list.size()).getName());
                    if (list.get(position % list.size()).getCups().size() > 0) {
                        mRcyShop.setVisibility(View.VISIBLE);
                        loadState(ViewUtil.NOT_NULL);
                        applyGoodsAdapter.setData(list.get(position % list.size()).getCups());
                    } else {
                        mRcyShop.setVisibility(View.GONE);
                        loadState(ViewUtil.NOT_DATA);
                    }

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }


    public void onloadStart() {
        ViewUtil.create().setAnimation(this, mFlState);
    }

    /**
     * 数据状态
     *
     * @param
     */
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


    @OnClick({R.id.toolbar_back,R.id.iv_right_word})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.iv_right_word:
                    AuditListActivity.start(this,false);
                    break;
            }
        }
    }


    private class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView iv = new ImageView(getApplication());
            GlideUtil.create().loadLongImage(ApplyGoodsActivity.this, list.get(position % list.size()).getImage(), iv);
            container.addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

    @Override
    public void showMessage(@NonNull String message) {

    }
}
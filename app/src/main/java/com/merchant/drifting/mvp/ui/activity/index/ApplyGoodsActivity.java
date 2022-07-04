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
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.merchant.drifting.R;
import com.merchant.drifting.data.entity.TransactionEntity;
import com.merchant.drifting.di.component.DaggerApplyGoodsComponent;
import com.merchant.drifting.mvp.contract.ApplyGoodsContract;
import com.merchant.drifting.mvp.model.entity.ApplyGoodsEntity;
import com.merchant.drifting.mvp.model.entity.CommodityManagerEntity;
import com.merchant.drifting.mvp.presenter.ApplyGoodsPresenter;
import com.merchant.drifting.mvp.ui.adapter.ApplyGoodsAdapter;
import com.merchant.drifting.util.ClickUtil;
import com.merchant.drifting.util.GlideUtil;
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
    @BindView(R.id.vp_img)
    ViewPager mViewPager;
    @BindView(R.id.rcy_shop)
    RecyclerView mRcyShop;
    private List<ApplyGoodsEntity> entityList;
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
        initListener();
    }

    public void initListener() {
        getData();
        MyAdapter myAdapter = new MyAdapter();
        mViewPager.setOffscreenPageLimit(entityList.size());
        mViewPager.setClipChildren(false);
        mViewPager.setAdapter(myAdapter);
        mViewPager.setCurrentItem(entityList.size() * 100);
        mViewPager.setPageTransformer(true, new ScaleInTransformer());
        frame.setOnTouchListener((view, motionEvent) -> mViewPager.onTouchEvent(motionEvent));

        applyGoodsAdapter = new ApplyGoodsAdapter(new ArrayList<>());
        mRcyShop.setLayoutManager(new GridLayoutManager(this, 2));
        mRcyShop.setAdapter(applyGoodsAdapter);
        applyGoodsAdapter.setData(getDatas());
    }


    public List<CommodityManagerEntity> getDatas() {
        List<CommodityManagerEntity> list = new ArrayList<>();
        list.add(new CommodityManagerEntity("1"));
        list.add(new CommodityManagerEntity("2"));
        list.add(new CommodityManagerEntity("3"));
        list.add(new CommodityManagerEntity("4"));
        list.add(new CommodityManagerEntity("5"));
        return list;
    }

    public void getData() {
        entityList = new ArrayList<>();
        entityList.add(new ApplyGoodsEntity(R.drawable.demo1, 1));
        entityList.add(new ApplyGoodsEntity(R.drawable.demo2, 2));
        entityList.add(new ApplyGoodsEntity(R.drawable.demo3, 3));
        entityList.add(new ApplyGoodsEntity(R.drawable.demo4, 4));
        entityList.add(new ApplyGoodsEntity(R.drawable.demo5, 5));
        entityList.add(new ApplyGoodsEntity(R.drawable.demo6, 6));
        entityList.add(new ApplyGoodsEntity(R.drawable.demo7, 7));
        entityList.add(new ApplyGoodsEntity(R.drawable.demo8, 8));
    }

    public Activity getActivity() {
        return this;
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
            iv.setImageResource(entityList.get(position % entityList.size()).getPic());
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
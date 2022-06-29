package com.merchant.drifting.mvp.ui.fragment;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.merchant.drifting.R;
import com.merchant.drifting.di.component.DaggerOrderComponent;
import com.merchant.drifting.mvp.contract.OrderContract;
import com.merchant.drifting.mvp.model.entity.OrderEntity;
import com.merchant.drifting.mvp.model.entity.SystemNotificationEntity;
import com.merchant.drifting.mvp.presenter.OrderPresenter;
import com.merchant.drifting.mvp.ui.adapter.OrderAdater;
import com.merchant.drifting.mvp.ui.adapter.SystemNotificationAdapter;
import com.rb.core.xrecycleview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Created on 2022/06/24 12:09
 *
 * @author 订单
 * module name is OrderFragment
 */
public class OrderFragment extends BaseFragment<OrderPresenter> implements OrderContract.View,XRecyclerView.LoadingListener {
    @BindView(R.id.tv_bar)
    TextView mTvBar;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_back)
    RelativeLayout mToolBarBack;
    @BindView(R.id.rcy_public)
    XRecyclerView mRcyPublic;

    private OrderAdater orderAdater;
    public static OrderFragment newInstance() {
        OrderFragment fragment = new OrderFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerOrderComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

    /**
     * 在 onActivityCreate()时调用
     */
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(false);
        setStatusBarHeight(mTvBar);
        mToolbarTitle.setText("核销订单");
        mToolBarBack.setVisibility(View.GONE);
        initListener();
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    private void initListener() {
        mRcyPublic.setLayoutManager(new LinearLayoutManager(mContext));
        mRcyPublic.setLoadingListener(this);
        orderAdater = new OrderAdater(new ArrayList<>());
        mRcyPublic.setAdapter(orderAdater);
        orderAdater.setData(getData());
    }


    public List<OrderEntity> getData() {
        List<OrderEntity> list = new ArrayList<>();
        list.add(new OrderEntity("1"));
        list.add(new OrderEntity("2"));
        list.add(new OrderEntity("3"));
        list.add(new OrderEntity("4"));
        return list;
    }

    public Fragment getFragment() {
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
}
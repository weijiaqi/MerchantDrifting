package com.merchant.drifting.mvp.ui.fragment;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.merchant.drifting.R;
import com.merchant.drifting.di.component.DaggerOrderComponent;
import com.merchant.drifting.mvp.contract.OrderContract;

import com.merchant.drifting.mvp.model.entity.WriteOffListEntity;
import com.merchant.drifting.mvp.presenter.OrderPresenter;
import com.merchant.drifting.mvp.ui.adapter.OrderAdater;

import com.merchant.drifting.storageinfo.Preferences;
import com.merchant.drifting.util.ViewUtil;
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
public class OrderFragment extends BaseFragment<OrderPresenter> implements OrderContract.View, XRecyclerView.LoadingListener {
    @BindView(R.id.tv_bar)
    TextView mTvBar;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_back)
    RelativeLayout mToolBarBack;
    @BindView(R.id.rcy_public)
    XRecyclerView mRcyPublic;
    @BindView(R.id.fl_container)
    FrameLayout mFlState;

    private OrderAdater orderAdater;

    private int mPage = 1;
    private int limit = 10;

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
        getData(mPage, true);
    }


    public void getData(int mPage, boolean loadType) {
        if (mPresenter != null) {
            mPresenter.writeOffList(Preferences.getShopId(), mPage, limit, loadType);
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
        if (orderAdater.getDatas() == null || orderAdater.getDatas().size() == 0) {
            ViewUtil.create().setAnimation(mContext, mFlState);
        }
    }

    @Override
    public void onWriteOffListSuccess(WriteOffListEntity entity, boolean isNotData) {
        List<WriteOffListEntity.ListBean> list = entity.getList();
        if (list != null && list.size() > 0) {
            if (isNotData) {
                mPage = 2;
                orderAdater.setData(list);
            } else {
                mPage++;
                orderAdater.addData(list);
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
            ViewUtil.create().setView(mContext, mFlState, ViewUtil.NOT_DATA);
        } else if (dataState == ViewUtil.NOT_SERVER) {
            ViewUtil.create().setView(mContext, mFlState, ViewUtil.NOT_SERVER);
        } else if (dataState == ViewUtil.NOT_NETWORK) {
            ViewUtil.create().setView(mContext, mFlState, ViewUtil.NOT_NETWORK);
        } else {
            ViewUtil.create().setView(mFlState);
        }
    }

    @Override
    public void onNetError() {

    }

    public Fragment getFragment() {
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {

    }


}
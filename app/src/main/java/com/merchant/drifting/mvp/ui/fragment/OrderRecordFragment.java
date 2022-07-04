package com.merchant.drifting.mvp.ui.fragment;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.merchant.drifting.R;
import com.merchant.drifting.di.component.DaggerOrderRecordComponent;
import com.merchant.drifting.mvp.contract.OrderRecordContract;
import com.merchant.drifting.mvp.model.entity.OrderRecordEntity;
import com.merchant.drifting.mvp.presenter.OrderRecordPresenter;
import com.merchant.drifting.mvp.ui.adapter.OrderRecordAdapter;
import com.merchant.drifting.storageinfo.Preferences;
import com.merchant.drifting.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Created on 2022/06/29 15:25
 *
 * @author
 * module name is OrderRecordFragment
 */
public class OrderRecordFragment extends BaseFragment<OrderRecordPresenter> implements OrderRecordContract.View {
    @BindView(R.id.rcy_public)
    RecyclerView mRcyOrderRecord;
    @BindView(R.id.fl_container)
    FrameLayout mFlState;

    private static final String BUNDLE_TYPE = "bundle_type";

    private int type, days;
    private OrderRecordAdapter orderRecordAdapter;

    public static OrderRecordFragment newInstance(int type) {
        OrderRecordFragment fragment = new OrderRecordFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerOrderRecordComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order_record, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        type = args.getInt(BUNDLE_TYPE);
        switch (type) {
            case 0:
                days = 1;
                break;
            case 1:
                days = 7;
                break;
            case 2:
                days = 30;
                break;
        }
    }

    /**
     * 在 onActivityCreate()时调用
     */
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    protected void onFirstVisible() {
        super.onFirstVisible();
        initListener();
    }

    private void initListener() {
        mRcyOrderRecord.setLayoutManager(new LinearLayoutManager(mContext));
        orderRecordAdapter = new OrderRecordAdapter(new ArrayList<>());
        mRcyOrderRecord.setAdapter(orderRecordAdapter);
        if (mPresenter != null) {
            mPresenter.salesranking(Preferences.getShopId(), days);
        }
    }


    @Override
    public void onloadStart() {
        if (orderRecordAdapter.getDatas() == null || orderRecordAdapter.getDatas().size() == 0) {
            ViewUtil.create().setAnimation(mContext, mFlState);
        }
    }

    @Override
    public void loadState(int dataState) {
        if (orderRecordAdapter.getDatas() == null || orderRecordAdapter.getDatas().size() == 0) {
            if (dataState == ViewUtil.NOT_DATA) {
                ViewUtil.create().setView(mContext, mFlState, ViewUtil.NOT_DATA);
            } else if (dataState == ViewUtil.NOT_SERVER) {
                ViewUtil.create().setView(mContext, mFlState, ViewUtil.NOT_SERVER);
            } else if (dataState == ViewUtil.NOT_NETWORK) {
                ViewUtil.create().setView(mContext, mFlState, ViewUtil.NOT_NETWORK);
            } else {
                ViewUtil.create().setView(mFlState);
            }
        } else {
            ViewUtil.create().setView(mFlState);
        }
    }

    @Override
    public void OnSalesRanking(List<OrderRecordEntity> list) {
        if (list != null && list.size() > 0) {
            orderRecordAdapter.setData(list);
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
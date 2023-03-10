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
import com.merchant.drifting.di.component.DaggerRecordComponent;

import com.merchant.drifting.mvp.contract.RecordContract;

import com.merchant.drifting.mvp.model.entity.ShopApplyLogEntity;
import com.merchant.drifting.mvp.presenter.RecordPresenter;
import com.merchant.drifting.mvp.ui.adapter.RecordAdapter;

import com.merchant.drifting.util.ViewUtil;
import com.rb.core.xrecycleview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Created on 2022/06/24 19:18
 *
 * @author 申请记录
 * module name is RecordFragment
 */
public class RecordFragment extends BaseFragment<RecordPresenter> implements RecordContract.View {
    @BindView(R.id.rcy_record)
    RecyclerView mRcyRecord;
    @BindView(R.id.fl_container)
    FrameLayout mFlState;
    private static final String EXTRA_TYPE = "extra_type";
    private RecordAdapter recordAdapter;
    private int type;

    public static RecordFragment newInstance(int type) {
        RecordFragment fragment = new RecordFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerRecordComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_record, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        type = args.getInt(EXTRA_TYPE);
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
        mRcyRecord.setLayoutManager(new LinearLayoutManager(mContext));
        recordAdapter = new RecordAdapter(new ArrayList<>());
        mRcyRecord.setAdapter(recordAdapter);
        if (mPresenter != null) {
            mPresenter.shopapplyLog(type);
        }
    }


    @Override
    public void onloadStart() {
        if (recordAdapter.getDatas() == null || recordAdapter.getDatas().size() == 0) {
            ViewUtil.create().setAnimation(mContext, mFlState);
        }
    }

    @Override
    public void loadState(int dataState) {
        if (recordAdapter.getDatas() == null || recordAdapter.getDatas().size() == 0) {
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
    public void OnApplyLog(List<ShopApplyLogEntity> list) {
        if (list != null && list.size() > 0) {
            recordAdapter.setData(list);
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
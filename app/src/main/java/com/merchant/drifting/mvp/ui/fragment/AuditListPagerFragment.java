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

import com.merchant.drifting.di.component.DaggerAuditListPagerComponent;
import com.merchant.drifting.mvp.contract.AuditListPagerContract;
import com.merchant.drifting.mvp.model.entity.AuditingEntity;
import com.merchant.drifting.mvp.presenter.AuditListPagerPresenter;
import com.merchant.drifting.mvp.ui.adapter.AuditListPagerAdapter;
import com.merchant.drifting.storageinfo.Preferences;
import com.merchant.drifting.util.ViewUtil;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Created on 2022/07/06 10:03
 *
 * @author 审核列表
 * module name is AuditListPagerFragment
 */
public   class AuditListPagerFragment extends BaseFragment<AuditListPagerPresenter> implements AuditListPagerContract.View {
    @BindView(R.id.rcy_record)
    RecyclerView mRcyRecord;
    @BindView(R.id.fl_container)
    FrameLayout mFlState;
    private static final String EXTRA_TYPE = "extra_type";

    private int type;
    private AuditListPagerAdapter auditListPagerAdapter;

    public static AuditListPagerFragment newInstance(int type) {
        AuditListPagerFragment fragment = new AuditListPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerAuditListPagerComponent //如找不到该类,请编译一下项目
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
        auditListPagerAdapter = new AuditListPagerAdapter(new ArrayList<>());
        mRcyRecord.setAdapter(auditListPagerAdapter);
        if (mPresenter != null) {
            mPresenter.shopapplyLog(Preferences.getShopId(), type);
        }
    }

    @Override
    public void onloadStart() {
        if (auditListPagerAdapter.getDatas() == null || auditListPagerAdapter.getDatas().size() == 0) {
            ViewUtil.create().setAnimation(mContext, mFlState);
        }
    }

    @Override
    public void loadState(int dataState) {
        if (auditListPagerAdapter.getDatas() == null || auditListPagerAdapter.getDatas().size() == 0) {
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
    public void OnAuditingSuccess(List<AuditingEntity> list) {
        if (list != null && list.size() > 0) {
            auditListPagerAdapter.setData(list);
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
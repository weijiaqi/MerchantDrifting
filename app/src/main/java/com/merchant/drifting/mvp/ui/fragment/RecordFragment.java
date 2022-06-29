package com.merchant.drifting.mvp.ui.fragment;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.merchant.drifting.R;
import com.merchant.drifting.di.component.DaggerRecordComponent;

import com.merchant.drifting.mvp.contract.RecordContract;
import com.merchant.drifting.mvp.model.entity.RecordEntity;
import com.merchant.drifting.mvp.presenter.RecordPresenter;
import com.merchant.drifting.mvp.ui.adapter.RecordAdapter;

import com.rb.core.xrecycleview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Created on 2022/06/24 19:18
 *
 * @author 谢况
 * module name is RecordFragment
 */
public class RecordFragment extends BaseFragment<RecordPresenter> implements RecordContract.View,XRecyclerView.LoadingListener {
    @BindView(R.id.rcy_record)
    XRecyclerView mRcyRecord;
    private static final String EXTRA_TYPE = "extra_type";
    private RecordAdapter recordAdapter;
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

    /**
     * 在 onActivityCreate()时调用
     */
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initListener();
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    private void initListener() {
        mRcyRecord.setLayoutManager(new LinearLayoutManager(mContext));
        mRcyRecord.setLoadingListener(this);
        recordAdapter = new RecordAdapter(new ArrayList<>());
        mRcyRecord.setAdapter(recordAdapter);
        recordAdapter.setData(getData());
    }


    public List<RecordEntity> getData() {
        List<RecordEntity> list = new ArrayList<>();
        list.add(new RecordEntity("1"));
        list.add(new RecordEntity("2"));
        list.add(new RecordEntity("3"));
        list.add(new RecordEntity("4"));
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
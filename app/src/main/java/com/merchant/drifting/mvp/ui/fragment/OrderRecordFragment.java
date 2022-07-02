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

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.merchant.drifting.R;
import com.merchant.drifting.di.component.DaggerOrderRecordComponent;
import com.merchant.drifting.mvp.contract.OrderRecordContract;
import com.merchant.drifting.mvp.model.entity.OrderRecordEntity;
import com.merchant.drifting.mvp.presenter.OrderRecordPresenter;
import com.merchant.drifting.mvp.ui.adapter.OrderRecordAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Created on 2022/06/29 15:25
 *
 * @author 谢况
 * module name is OrderRecordFragment
 */
public class OrderRecordFragment extends BaseFragment<OrderRecordPresenter> implements OrderRecordContract.View {
    @BindView(R.id.rcy_public)
    RecyclerView mRcyOrderRecord;


    private static final String BUNDLE_TYPE = "bundle_type";

    private int type;
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
        type =  args.getInt(BUNDLE_TYPE);
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
        mRcyOrderRecord.setLayoutManager(new LinearLayoutManager(mContext));
        orderRecordAdapter = new OrderRecordAdapter(new ArrayList<>());
        mRcyOrderRecord.setAdapter(orderRecordAdapter);
        orderRecordAdapter.setData(getData2());
    }


    public List<OrderRecordEntity> getData2() {
        List<OrderRecordEntity> list = new ArrayList<>();
        list.add(new OrderRecordEntity("1"));
        list.add(new OrderRecordEntity("2"));
        if (type==1){
            list.add(new OrderRecordEntity("3"));
            list.add(new OrderRecordEntity("4"));
        }
        if (type==2){
            list.add(new OrderRecordEntity("3"));
            list.add(new OrderRecordEntity("4"));
            list.add(new OrderRecordEntity("5"));
            list.add(new OrderRecordEntity("6"));
        }

        return list;
    }

    public Fragment getFragment() {
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {

    }
}
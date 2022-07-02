package com.merchant.drifting.mvp.ui.fragment;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.merchant.drifting.R;

import com.merchant.drifting.data.event.GoodsOnOffEvent;
import com.merchant.drifting.di.component.DaggerCommodityManagerComponent;
import com.merchant.drifting.mvp.contract.CommodityManagerContract;
import com.merchant.drifting.mvp.model.entity.CommodityManagerEntity;
import com.merchant.drifting.mvp.model.entity.OrderDataEntity;
import com.merchant.drifting.mvp.presenter.CommodityManagerPresenter;
import com.merchant.drifting.mvp.ui.adapter.CommodityManagerAdapter;
import com.merchant.drifting.util.ToastUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Created on 2022/07/01 11:45
 *
 * @author 商品管理
 * module name is CommodityManagerFragment
 */
public class CommodityManagerFragment extends BaseFragment<CommodityManagerPresenter> implements CommodityManagerContract.View {

    @BindView(R.id.rcy_public)
    RecyclerView mRcyPublic;

    private static final String BUNDLE_TYPE = "bundle_type";

    private int type;
    private CommodityManagerAdapter commodityManagerAdapter;

    private boolean isDelete;

    public static CommodityManagerFragment newInstance(int type) {
        CommodityManagerFragment fragment = new CommodityManagerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerCommodityManagerComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_commodity_manager, container, false);
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
        mRcyPublic.setLayoutManager(new GridLayoutManager(mContext, 2));
        commodityManagerAdapter = new CommodityManagerAdapter(new ArrayList<>(), sellectCount -> {

        });
        mRcyPublic.setAdapter(commodityManagerAdapter);
        commodityManagerAdapter.setData(getData());
    }


    public List<CommodityManagerEntity> getData() {
        List<CommodityManagerEntity> list = new ArrayList<>();
        list.add(new CommodityManagerEntity("1"));
        list.add(new CommodityManagerEntity("2"));
        list.add(new CommodityManagerEntity("3"));
        list.add(new CommodityManagerEntity("4"));
        list.add(new CommodityManagerEntity("5"));
        return list;
    }

    public Fragment getFragment() {
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {

    }


    /**
     * 删除Event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void GoodsOnOffEvent(GoodsOnOffEvent editEvent) {
        if (editEvent != null) {
            isDelete = editEvent.isEdit();
            commodityManagerAdapter.setIdDelete(isDelete ? true : false);
            if (isDelete == false) {
                commodityManagerAdapter.clearCheck();
            }
        }
    }
}
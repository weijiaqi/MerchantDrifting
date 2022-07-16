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
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.merchant.drifting.R;

import com.merchant.drifting.data.event.GoodsAllEvent;
import com.merchant.drifting.data.event.GoodsOnOffEvent;
import com.merchant.drifting.data.event.GoodsShelfEvent;
import com.merchant.drifting.data.event.SelectAllEvent;
import com.merchant.drifting.di.component.DaggerCommodityManagerComponent;
import com.merchant.drifting.mvp.contract.CommodityManagerContract;

import com.merchant.drifting.mvp.model.entity.CommodityManagerEntity;
import com.merchant.drifting.mvp.model.entity.GoodsListEntity;
import com.merchant.drifting.mvp.presenter.CommodityManagerPresenter;
import com.merchant.drifting.mvp.ui.adapter.CommodityManagerAdapter;
import com.merchant.drifting.storageinfo.Preferences;
import com.merchant.drifting.util.ClickUtil;
import com.merchant.drifting.util.ToastUtil;
import com.merchant.drifting.util.ViewUtil;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/07/01 11:45
 *
 * @author 商品管理
 * module name is CommodityManagerFragment
 */
public class CommodityManagerFragment extends BaseFragment<CommodityManagerPresenter> implements CommodityManagerContract.View {
    @BindView(R.id.rcy_public)
    RecyclerView mRcyPublic;
    @BindView(R.id.fl_container)
    FrameLayout mFlState;
    @BindView(R.id.rl_bottoms)
    RelativeLayout mRlBottom;
    @BindView(R.id.tv_off_shelf)
    TextView mTvOffShelf;
    @BindView(R.id.tv_top)
    TextView mTvTop;
    private static final String BUNDLE_TYPE = "bundle_type";
    private int type, count;
    private CommodityManagerAdapter commodityManagerAdapter;
    private boolean isDelete;
    private boolean hasSellectAll;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        type = args.getInt(BUNDLE_TYPE);
    }

    @Override
    protected void onVisible() {
        super.onVisible();
        onGoodList();
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
        mRcyPublic.setLayoutManager(new GridLayoutManager(mContext, 2));
        commodityManagerAdapter = new CommodityManagerAdapter(new ArrayList<>(), sellectCount -> {
            count = sellectCount;
            SelectAllEvent event = new SelectAllEvent();
            if (sellectCount != commodityManagerAdapter.getItemCount()) {
                event.setSelectedall(true);
            } else if (sellectCount == commodityManagerAdapter.getItemCount()) {
                event.setSelectedall(false);
            }
            EventBus.getDefault().post(event);
        });
        mRcyPublic.setAdapter(commodityManagerAdapter);
        onGoodList();
    }

    public void onGoodList() {
        if (mPresenter != null) {
            mPresenter.goodslist(Preferences.getShopId(), type);
        }
    }


    @Override
    public void onloadStart() {
        if (commodityManagerAdapter.getDatas() == null || commodityManagerAdapter.getDatas().size() == 0) {
            ViewUtil.create().setAnimation(mContext, mFlState);
        }
    }


    @Override
    public void OnGoodsListSuccess(List<GoodsListEntity> list) {
        commodityManagerAdapter.setIdDelete(false);
        commodityManagerAdapter.setData(list);
        if (commodityManagerAdapter.getItemCount() == 0) {
            loadState(ViewUtil.NOT_DATA);
        }
    }

    @Override
    public void OnShelfSuccess() {
        commodityManagerAdapter.setIdDelete(false);
        mRlBottom.setVisibility(View.GONE);
        commodityManagerAdapter.clearCheck();
        EventBus.getDefault().post(new GoodsShelfEvent());
        onGoodList();
    }

    @Override
    public void loadState(int dataState) {
        if (commodityManagerAdapter.getItemCount() == 0) {
            mTvTop.setVisibility(View.GONE);
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
            mTvTop.setVisibility(View.VISIBLE);
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
        ToastUtil.showToast(message);
    }

    @OnClick({R.id.tv_off_shelf})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.tv_off_shelf:   //上下架
                    if (count == 0) {
                        showMessage("请选择商品");
                        return;
                    }
                    if (mPresenter != null) {
                        mPresenter.deleteCollect(commodityManagerAdapter, type);
                    }
                    break;
            }
        }
    }

    /**
     * 选择
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void GoodsOnOffEvent(GoodsOnOffEvent editEvent) {
        if (editEvent != null) {
            isDelete = editEvent.isEdit();
            mRlBottom.setVisibility(isDelete ? View.VISIBLE : View.GONE);
            if (isDelete) {
                if (type == 1) {
                    mTvOffShelf.setText("下架");
                } else {
                    mTvOffShelf.setText("上架");
                }
            }
            commodityManagerAdapter.setIdDelete(isDelete ? true : false);
        }
    }


    /**
     * 全选/取消全选
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void GoodsAllEvent(GoodsAllEvent goodsAllEvent) {
        if (goodsAllEvent != null) {
            hasSellectAll = goodsAllEvent.isSelected();
            if (hasSellectAll) {
                commodityManagerAdapter.selectAll();
            } else {
                commodityManagerAdapter.clearCheck();
            }
        }
    }

}
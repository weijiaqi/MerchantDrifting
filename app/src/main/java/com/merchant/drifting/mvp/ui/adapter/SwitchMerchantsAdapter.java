package com.merchant.drifting.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseRecyclerAdapter;
import com.jess.arms.base.BaseRecyclerHolder;
import com.merchant.drifting.R;
import com.merchant.drifting.mvp.model.entity.MerchantsEntity;
import com.merchant.drifting.mvp.model.entity.ShopListEntity;
import com.merchant.drifting.mvp.ui.holder.SwitchMerchantsHolder;
import com.merchant.drifting.mvp.ui.holder.SystemNotificationHolder;

import java.util.List;

public class SwitchMerchantsAdapter extends BaseRecyclerAdapter<ShopListEntity> {
    public SwitchMerchantsAdapter(List<ShopListEntity> infos) {
        super(infos);
    }

    @Override
    public void getHolder(BaseRecyclerHolder holder, int position) {
        SwitchMerchantsHolder switchMerchantsHolder = (SwitchMerchantsHolder) holder;
        switchMerchantsHolder.setData(mDatas, position);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_switch_merchants;
    }

    @Override
    public BaseRecyclerHolder getCreateViewHolder(View view, int viewType) {
        return new SwitchMerchantsHolder(view);
    }
}

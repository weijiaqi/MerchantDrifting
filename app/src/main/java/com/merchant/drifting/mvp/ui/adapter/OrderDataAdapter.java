package com.merchant.drifting.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseRecyclerAdapter;
import com.jess.arms.base.BaseRecyclerHolder;
import com.merchant.drifting.R;
import com.merchant.drifting.mvp.model.entity.OrderDataEntity;

import com.merchant.drifting.mvp.ui.holder.OrderDataHolder;

import java.util.List;


public class OrderDataAdapter extends BaseRecyclerAdapter<OrderDataEntity> {

    public OrderDataAdapter(List<OrderDataEntity> infos) {
        super(infos);
    }


    @Override
    public void getHolder(BaseRecyclerHolder holder, int position) {
        OrderDataHolder orderDataHolder=(OrderDataHolder) holder;
        orderDataHolder.setData(mDatas,position);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_order_data;
    }

    @Override
    public BaseRecyclerHolder getCreateViewHolder(View view, int viewType) {
        return new OrderDataHolder(view);
    }
}

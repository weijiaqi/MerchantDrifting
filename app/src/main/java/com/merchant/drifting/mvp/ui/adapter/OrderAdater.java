package com.merchant.drifting.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseRecyclerAdapter;
import com.jess.arms.base.BaseRecyclerHolder;
import com.merchant.drifting.R;
import com.merchant.drifting.mvp.model.entity.OrderEntity;
import com.merchant.drifting.mvp.ui.holder.OrderHolder;

import java.util.List;

public class OrderAdater extends BaseRecyclerAdapter<OrderEntity> {

    public OrderAdater(List<OrderEntity> infos) {
        super(infos);
    }

    @Override
    public void getHolder(BaseRecyclerHolder holder, int position) {
        OrderHolder orderHolder = (OrderHolder) holder;
        orderHolder.setData(mDatas, position);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_order;
    }

    @Override
    public BaseRecyclerHolder getCreateViewHolder(View view, int viewType) {
        return new OrderHolder(view);
    }
}

package com.merchant.drifting.mvp.ui.adapter;

import android.view.View;
import com.jess.arms.base.BaseRecyclerAdapter;
import com.jess.arms.base.BaseRecyclerHolder;
import com.merchant.drifting.R;
import com.merchant.drifting.mvp.model.entity.OrderRecordEntity;
import com.merchant.drifting.mvp.ui.holder.OrderRecordHolder;

import java.util.List;

public class OrderRecordAdapter extends BaseRecyclerAdapter<OrderRecordEntity> {

    public OrderRecordAdapter(List<OrderRecordEntity> infos) {
        super(infos);
    }

    @Override
    public void getHolder(BaseRecyclerHolder holder, int position) {
        OrderRecordHolder orderRecordHolder=(OrderRecordHolder) holder;
        orderRecordHolder.setData(mDatas,position);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_order_record;
    }

    @Override
    public BaseRecyclerHolder getCreateViewHolder(View view, int viewType) {
        return new OrderRecordHolder(view);
    }
}

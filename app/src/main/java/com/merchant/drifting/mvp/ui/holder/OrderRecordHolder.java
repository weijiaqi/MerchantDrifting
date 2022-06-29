package com.merchant.drifting.mvp.ui.holder;

import android.view.View;

import androidx.annotation.NonNull;

import com.jess.arms.base.BaseRecyclerHolder;
import com.merchant.drifting.mvp.model.entity.OrderRecordEntity;
import java.util.List;

public class OrderRecordHolder extends BaseRecyclerHolder {

    public OrderRecordHolder(View itemView) {
        super(itemView);
    }


    public void setData(@NonNull List<OrderRecordEntity> listBeanList, int position) {

    }
}

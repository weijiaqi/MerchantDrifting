package com.merchant.drifting.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseRecyclerAdapter;
import com.jess.arms.base.BaseRecyclerHolder;
import com.merchant.drifting.R;
import com.merchant.drifting.mvp.model.entity.AvailableAllEntity;
import com.merchant.drifting.mvp.ui.holder.ApplyGoodsHolder;
import java.util.List;

public class ApplyGoodsAdapter extends BaseRecyclerAdapter<AvailableAllEntity.CupsBean> {

    public ApplyGoodsAdapter(List<AvailableAllEntity.CupsBean> infos) {
        super(infos);
    }


    @Override
    public void getHolder(BaseRecyclerHolder holder, int position) {
        ApplyGoodsHolder applyGoodsHolder = (ApplyGoodsHolder) holder;
        applyGoodsHolder.setData(mDatas, position);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_app_goods;
    }

    @Override
    public BaseRecyclerHolder getCreateViewHolder(View view, int viewType) {
        return new ApplyGoodsHolder(view);
    }
}
package com.merchant.drifting.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseRecyclerAdapter;
import com.jess.arms.base.BaseRecyclerHolder;
import com.merchant.drifting.R;
import com.merchant.drifting.mvp.model.entity.RecordEntity;
import com.merchant.drifting.mvp.ui.holder.RecordHolder;



import java.util.List;

public class RecordAdapter  extends BaseRecyclerAdapter<RecordEntity> {

    public RecordAdapter(List<RecordEntity> infos) {
        super(infos);
    }

    @Override
    public void getHolder(BaseRecyclerHolder holder, int position) {
        RecordHolder recordHolder=(RecordHolder) holder;
        recordHolder.setData(mDatas, position);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_record;
    }

    @Override
    public BaseRecyclerHolder getCreateViewHolder(View view, int viewType) {
        return new RecordHolder(view);
    }
}

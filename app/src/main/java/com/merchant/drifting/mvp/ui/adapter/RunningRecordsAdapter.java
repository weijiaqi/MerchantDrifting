package com.merchant.drifting.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseRecyclerAdapter;
import com.jess.arms.base.BaseRecyclerHolder;
import com.merchant.drifting.R;
import com.merchant.drifting.mvp.model.entity.BusinessBillEntity;
import com.merchant.drifting.mvp.ui.holder.RunningRecordsHolder;

import java.util.List;

public class RunningRecordsAdapter  extends BaseRecyclerAdapter<BusinessBillEntity.ListBean> {

    public RunningRecordsAdapter(List<BusinessBillEntity.ListBean> infos) {
        super(infos);
    }

    @Override
    public void getHolder(BaseRecyclerHolder holder, int position) {
        RunningRecordsHolder runningRecordsHolder=(RunningRecordsHolder) holder;
        runningRecordsHolder.setData(mDatas, position);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_running_records;
    }

    @Override
    public BaseRecyclerHolder getCreateViewHolder(View view, int viewType) {
        return new RunningRecordsHolder(view);
    }
}

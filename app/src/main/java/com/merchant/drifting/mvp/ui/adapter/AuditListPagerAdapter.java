package com.merchant.drifting.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseRecyclerAdapter;
import com.jess.arms.base.BaseRecyclerHolder;
import com.merchant.drifting.R;
import com.merchant.drifting.mvp.model.entity.AuditingEntity;

import com.merchant.drifting.mvp.ui.holder.AuditListPagerHolder;
import com.merchant.drifting.mvp.ui.holder.RecordHolder;

import java.util.List;

public class AuditListPagerAdapter extends BaseRecyclerAdapter<AuditingEntity> {

    public AuditListPagerAdapter(List<AuditingEntity> infos) {
        super(infos);
    }

    @Override
    public void getHolder(BaseRecyclerHolder holder, int position) {
        AuditListPagerHolder auditListPagerHolder=(AuditListPagerHolder) holder;
        auditListPagerHolder.setData(mDatas, position);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_audit_list;
    }


    @Override
    public BaseRecyclerHolder getCreateViewHolder(View view, int viewType) {
        return new AuditListPagerHolder(view);
    }
}


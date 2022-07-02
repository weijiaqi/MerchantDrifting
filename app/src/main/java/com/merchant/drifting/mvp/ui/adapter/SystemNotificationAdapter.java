package com.merchant.drifting.mvp.ui.adapter;


import android.view.View;

import com.jess.arms.base.BaseRecyclerAdapter;
import com.jess.arms.base.BaseRecyclerHolder;
import com.merchant.drifting.R;
import com.merchant.drifting.mvp.model.entity.SystemNotificationEntity;
import com.merchant.drifting.mvp.ui.holder.SystemNotificationHolder;

import java.util.List;

public class SystemNotificationAdapter extends BaseRecyclerAdapter<SystemNotificationEntity.ListBean> {

    private int type;
    public SystemNotificationAdapter(List<SystemNotificationEntity.ListBean> infos,int status) {
        super(infos);
        type=status;
    }

    @Override
    public void getHolder(BaseRecyclerHolder holder, int position) {
        SystemNotificationHolder systemNotificationHolder=(SystemNotificationHolder) holder;
        systemNotificationHolder.setData(mDatas, position,type);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_system_notification;
    }

    @Override
    public BaseRecyclerHolder getCreateViewHolder(View view, int viewType) {
        return new SystemNotificationHolder(view);
    }
}

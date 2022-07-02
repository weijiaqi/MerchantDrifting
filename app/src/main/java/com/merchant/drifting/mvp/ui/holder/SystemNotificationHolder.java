package com.merchant.drifting.mvp.ui.holder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jess.arms.base.BaseRecyclerHolder;
import com.merchant.drifting.R;
import com.merchant.drifting.mvp.model.entity.SystemNotificationEntity;
import com.merchant.drifting.util.DateUtil;
import com.merchant.drifting.util.TextUtil;

import java.util.List;

import butterknife.BindView;

public class SystemNotificationHolder extends BaseRecyclerHolder {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_desc)
    TextView mTvDesc;
    @BindView(R.id.tv_status)
    TextView mTvStatus;
    private Context context;
    public SystemNotificationHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
    }

    public void setData(@NonNull List<SystemNotificationEntity.ListBean> listBeanList, int position, int type) {
        TextUtil.setText(mTvTitle, listBeanList.get(position).getTitle());
        TextUtil.setText(mTvTime, DateUtil.unxiToDateYMDHM(listBeanList.get(position).getCreated_at_int() + ""));
        TextUtil.setText(mTvDesc, listBeanList.get(position).getContent());
        if (type == 1) {
            mTvStatus.setTextColor(context.getColor(R.color.color_42));
        } else {
            mTvStatus.setTextColor(context.getColor(R.color.color_f9));
        }
    }
}

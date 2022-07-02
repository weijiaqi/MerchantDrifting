package com.merchant.drifting.mvp.ui.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseRecyclerHolder;
import com.merchant.drifting.R;
import com.merchant.drifting.mvp.model.entity.BusinessBillEntity;
import com.merchant.drifting.util.DateUtil;
import com.merchant.drifting.util.TextUtil;

import java.util.List;

import butterknife.BindView;

public class RunningRecordsHolder extends BaseRecyclerHolder {

    @BindView(R.id.iv_pic)
    ImageView mIvPic;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_price)
    TextView mTvPrice;

    public RunningRecordsHolder(View itemView) {
        super(itemView);
    }

    public void setData(List<BusinessBillEntity.ListBean> list, int postion) {
        TextUtil.setText(mTvTime, DateUtil.unixToDateMDH(list.get(postion).getCreated_at_int() + ""));
    }
}

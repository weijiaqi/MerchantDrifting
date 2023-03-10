package com.merchant.drifting.mvp.ui.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseRecyclerHolder;
import com.merchant.drifting.R;
import com.merchant.drifting.mvp.model.entity.BusinessBillEntity;
import com.merchant.drifting.util.DateUtil;
import com.merchant.drifting.util.GlideUtil;
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

    private Context context;
    public RunningRecordsHolder(View itemView) {
        super(itemView);
        context=itemView.getContext();
    }

    public void setData(List<BusinessBillEntity.ListBean> list, int postion) {
        GlideUtil.create().loadHeadCirclePic(context, list.get(postion).getImage(), mIvPic);
        TextUtil.setText(mTvTime, DateUtil.unixToDateMDH(list.get(postion).getCreated_at_int() + ""));
        if (list.get(postion).getBill_type() == 1) {
            mTvTitle.setText("提现");
            TextUtil.setText(mTvPrice, "-" + list.get(postion).getMoney());
        } else {
            mTvTitle.setText("订单收入");
            TextUtil.setText(mTvPrice, "+" + list.get(postion).getMoney());
        }


    }
}

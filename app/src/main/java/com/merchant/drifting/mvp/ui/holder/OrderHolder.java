package com.merchant.drifting.mvp.ui.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jess.arms.base.BaseRecyclerHolder;
import com.merchant.drifting.R;

import com.merchant.drifting.mvp.model.entity.WriteOffListEntity;
import com.merchant.drifting.mvp.ui.activity.index.OrderDetailActivity;
import com.merchant.drifting.util.DateUtil;
import com.merchant.drifting.util.GlideUtil;
import com.merchant.drifting.util.TextUtil;

import java.util.List;

import butterknife.BindView;

public class OrderHolder extends BaseRecyclerHolder {
    @BindView(R.id.ll_content)
    LinearLayout mLlContent;
    @BindView(R.id.tv_store_no)
    TextView mTvStoreNo;
    @BindView(R.id.tv_status)
    TextView mTvStatus;
    @BindView(R.id.iv_pic)
    ImageView mIvPic;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_desc)
    TextView mTvDesc;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    private Context context;

    public OrderHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
    }

    public void setData(@NonNull List<WriteOffListEntity.ListBean> listBeanList, int position) {
        TextUtil.setText(mTvStoreNo, listBeanList.get(position).getOrder_sub_sn());
        if (listBeanList.get(position).getStatus() == 1) {
            mTvStatus.setText("已核销");
        } else {
            mTvStatus.setText("未核销");
        }
        GlideUtil.create().loadNormalPic(context, listBeanList.get(position).getSmall_image(),mIvPic);
        TextUtil.setText(mTvTitle, listBeanList.get(position).getSku_name());
        TextUtil.setText(mTvDesc, listBeanList.get(position).getIntro());
        TextUtil.setText(mTvPrice, "¥" + listBeanList.get(position).getMoney());
        TextUtil.setText(mTvTime, DateUtil.unxiToDateYMDHM(listBeanList.get(position).getCreated_at_int() + ""));
        mLlContent.setOnClickListener(v -> {
            OrderDetailActivity.start(context, listBeanList.get(position).getWrite_off_id(), false);
        });
    }
}

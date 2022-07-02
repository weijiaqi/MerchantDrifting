package com.merchant.drifting.mvp.ui.holder;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jess.arms.base.BaseRecyclerHolder;
import com.jess.arms.utils.ArmsUtils;
import com.merchant.drifting.R;

import com.merchant.drifting.mvp.model.entity.ShopApplyLogEntity;
import com.merchant.drifting.mvp.ui.activity.user.ApplicationMaterialsActivity;
import com.merchant.drifting.util.DateUtil;
import com.merchant.drifting.util.SpannableUtil;
import com.merchant.drifting.util.TextUtil;


import java.util.List;

import butterknife.BindView;

public class RecordHolder extends BaseRecyclerHolder {

    @BindView(R.id.tv_reason)
    TextView mTvReason;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.tv_status)
    TextView mTvStatus;
    @BindView(R.id.tv_application_time)
    TextView mTvApplicationTime;
    @BindView(R.id.rl_content)
    RelativeLayout mRlContent;
    @BindView(R.id.rl_error)
    RelativeLayout mRlError;
    @BindView(R.id.tv_continue_apply)
    TextView mTvContinue;
    private SpannableStringBuilder passer;
    private ViewGroup.LayoutParams pp;
    private Context context;

    public RecordHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
    }


    public void setData(@NonNull List<ShopApplyLogEntity> listBeanList, int position) {
        TextUtil.setText(mTvTitle, listBeanList.get(position).getShop_name());
        TextUtil.setText(mTvPhone,"+86 " + listBeanList.get(position).getMobile());
        TextUtil.setText(mTvApplicationTime,DateUtil.unxiToDateYMDHM(listBeanList.get(position).getApply_at_int()+""));
        pp=mRlContent.getLayoutParams();
        if (listBeanList.get(position).getStatus() == 1 || listBeanList.get(position).getStatus() == 0) {
            pp.height = ArmsUtils.dip2px(context, 148);
        } else {
            pp.height = ArmsUtils.dip2px(context, 190);
        }
        mRlContent.setLayoutParams(pp);

        if (listBeanList.get(position).getStatus() == 1) {
            mTvStatus.setText("已通过");
            mRlError.setVisibility(View.GONE);
            mTvStatus.setTextColor(context.getColor(R.color.color_42c));
        } else if (listBeanList.get(position).getStatus() == 0) {
            mRlError.setVisibility(View.GONE);
            mTvStatus.setText("审核中");
            mTvStatus.setTextColor(context.getColor(R.color.color_42));
        } else {
            mRlError.setVisibility(View.VISIBLE);
            mTvStatus.setText("已驳回");
            mTvStatus.setTextColor(context.getColor(R.color.color_c2));
            passer = SpannableUtil.getBuilder(context, "原因： ").setForegroundColor(R.color.color_c2).append("照片模糊").build();
            mTvReason.setText(passer);
        }

        mTvContinue.setOnClickListener(v->{
            ApplicationMaterialsActivity.start(context, false);
        });
    }
}

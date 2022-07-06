package com.merchant.drifting.mvp.ui.holder;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jess.arms.base.BaseRecyclerHolder;
import com.jess.arms.utils.ArmsUtils;
import com.merchant.drifting.R;
import com.merchant.drifting.mvp.model.entity.AuditingEntity;
import com.merchant.drifting.mvp.ui.activity.index.AddItemActivity;
import com.merchant.drifting.util.DateUtil;
import com.merchant.drifting.util.GlideUtil;
import com.merchant.drifting.util.SpannableUtil;
import com.merchant.drifting.util.TextUtil;

import java.util.List;

import butterknife.BindView;

public class AuditListPagerHolder extends BaseRecyclerHolder {

    @BindView(R.id.rl_content)
    RelativeLayout mRlContent;
    @BindView(R.id.tv_reason)
    TextView mTvReason;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_specifications)
    TextView mTvSpecification;
    @BindView(R.id.tv_status)
    TextView mTvStatus;
    @BindView(R.id.tv_application_time)
    TextView mTvApplicationTime;
    @BindView(R.id.rl_error)
    RelativeLayout mRlError;
    @BindView(R.id.tv_continue_apply)
    TextView mTvContinue;
    @BindView(R.id.iv_restaurant)
    ImageView mIvRestaurant;
    private SpannableStringBuilder passer;
    private ViewGroup.LayoutParams pp;
    private Context context;

    public AuditListPagerHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
    }

    public void setData(@NonNull List<AuditingEntity> listBeanList, int position) {
        GlideUtil.create().loadNormalPic(context,listBeanList.get(position).getSmall_image(),mIvRestaurant);
        TextUtil.setText(mTvTitle, listBeanList.get(position).getGoods_name());
        TextUtil.setText(mTvSpecification,listBeanList.get(position).getSku_code());
        TextUtil.setText(mTvApplicationTime, DateUtil.unxiToDateYMDHM(listBeanList.get(position).getApply_at_int() + ""));
        pp = mRlContent.getLayoutParams();
        if (listBeanList.get(position).getAudit() == 1 || listBeanList.get(position).getAudit() == 2) {
            pp.height = ArmsUtils.dip2px(context, 148);
        } else {
            pp.height = ArmsUtils.dip2px(context, 190);
        }
        mRlContent.setLayoutParams(pp);

        if (listBeanList.get(position).getAudit() == 2) {
            mTvStatus.setText("已通过");
            mRlError.setVisibility(View.GONE);
            mTvStatus.setTextColor(context.getColor(R.color.color_42c));
        } else if (listBeanList.get(position).getAudit() == 1) {
            mRlError.setVisibility(View.GONE);
            mTvStatus.setText("审核中");
            mTvStatus.setTextColor(context.getColor(R.color.color_42));
        } else {
            mRlError.setVisibility(View.VISIBLE);
            mTvStatus.setText("已驳回");
            mTvStatus.setTextColor(context.getColor(R.color.color_c2));
            passer = SpannableUtil.getBuilder(context, "原因： ").setForegroundColor(R.color.color_c2).append(listBeanList.get(position).getReason()).build();
            mTvReason.setText(passer);
        }

        mRlContent.setOnClickListener(v -> {
            AddItemActivity.start(context, 2,listBeanList.get(position).getExplore_id()+"",listBeanList.get(position).getSku_code(),false);
        });
    }
}

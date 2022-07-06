package com.merchant.drifting.mvp.ui.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jess.arms.base.BaseRecyclerHolder;
import com.merchant.drifting.R;
import com.merchant.drifting.mvp.model.entity.AvailableAllEntity;
import com.merchant.drifting.mvp.ui.activity.index.AddItemActivity;
import com.merchant.drifting.util.GlideUtil;
import com.merchant.drifting.util.TextUtil;

import java.util.List;

import butterknife.BindView;

public class ApplyGoodsHolder extends BaseRecyclerHolder {

    @BindView(R.id.rl_content)
    RelativeLayout mRlContent;
    @BindView(R.id.iv_pic)
    ImageView mIvPic;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_desc)
    TextView mTvDesc;


    private Context context;

    public ApplyGoodsHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
    }

    public void setData(@NonNull List<AvailableAllEntity.CupsBean> listBeanList, int position) {
        GlideUtil.create().loadNormalPic(context, listBeanList.get(position).getLarge_image(), mIvPic);

        TextUtil.setText(mTvName, listBeanList.get(position).getGoods_name());
        TextUtil.setText(mTvDesc, listBeanList.get(position).getIntro());
        mRlContent.setOnClickListener(v -> {
            AddItemActivity.start(context, 1,listBeanList.get(position).getExplore_id()+"",listBeanList.get(position).getGoods_code(),false);
        });
    }
}

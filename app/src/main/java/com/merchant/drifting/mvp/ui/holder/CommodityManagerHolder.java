package com.merchant.drifting.mvp.ui.holder;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jess.arms.base.BaseRecyclerHolder;
import com.merchant.drifting.R;
import com.merchant.drifting.mvp.model.entity.CommodityManagerEntity;
import com.merchant.drifting.mvp.model.entity.GoodsListEntity;
import com.merchant.drifting.mvp.ui.adapter.CommodityManagerAdapter;
import com.merchant.drifting.util.GlideUtil;
import com.merchant.drifting.util.TextUtil;


import java.util.List;

import butterknife.BindView;

public class CommodityManagerHolder extends BaseRecyclerHolder {
    @BindView(R.id.ll_content)
    LinearLayout mLlContent;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_intro)
    TextView mTvIntro;
    @BindView(R.id.sellect)
    CheckBox ckSellect;
    @BindView(R.id.iv_pic)
    ImageView mIvPic;
    private Context context;
    private CommodityManagerAdapter mAdapter;

    public CommodityManagerHolder(View itemView, CommodityManagerAdapter adapter) {
        super(itemView);
        context = itemView.getContext();
        this.mAdapter = adapter;
    }

    public void setData(@NonNull List<GoodsListEntity> listBeanList, int position) {
        GlideUtil.create().loadNormalPic(context, listBeanList.get(position).getSmall_image(), mIvPic);
        TextUtil.setText(mTvTitle, listBeanList.get(position).getGoods_name());
        TextUtil.setText(mTvPrice, "¥" + listBeanList.get(position).getPrice());
        TextUtil.setText(mTvIntro, listBeanList.get(position).getIntro());

        if (mAdapter.isIdDelete()) {
            ckSellect.setVisibility(View.VISIBLE);
        } else {
            ckSellect.setVisibility(View.INVISIBLE);
        }

        if (mAdapter.getSelectEntities() != null) {
            if (mAdapter.getSelectEntities().contains(listBeanList.get(position))) {
                ckSellect.setChecked(true);
            } else {
                ckSellect.setChecked(false);
            }
        }

        ckSellect.setOnClickListener(v -> {  //选择
            mAdapter.onItemCheckChange(listBeanList.get(position));
        });
    }
}

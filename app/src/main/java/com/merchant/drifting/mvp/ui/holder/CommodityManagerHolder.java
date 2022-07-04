package com.merchant.drifting.mvp.ui.holder;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.jess.arms.base.BaseRecyclerHolder;
import com.merchant.drifting.R;
import com.merchant.drifting.mvp.model.entity.CommodityManagerEntity;
import com.merchant.drifting.mvp.ui.adapter.CommodityManagerAdapter;


import java.util.List;

import butterknife.BindView;

public class CommodityManagerHolder extends BaseRecyclerHolder {
    @BindView(R.id.ll_content)
    LinearLayout mLlContent;
    @BindView(R.id.sellect)
    CheckBox ckSellect;
    private Context context;
    private CommodityManagerAdapter mAdapter;

    public CommodityManagerHolder(View itemView, CommodityManagerAdapter adapter) {
        super(itemView);
        context = itemView.getContext();
        this.mAdapter = adapter;
    }

    public void setData(@NonNull List<CommodityManagerEntity> listBeanList, int position) {

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

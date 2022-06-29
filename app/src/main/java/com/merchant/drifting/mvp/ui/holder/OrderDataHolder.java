package com.merchant.drifting.mvp.ui.holder;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jess.arms.base.BaseRecyclerHolder;
import com.merchant.drifting.R;
import com.merchant.drifting.mvp.model.entity.OrderDataEntity;
import com.merchant.drifting.view.ZzHorizontalProgressBar;


import java.util.List;

import butterknife.BindView;

public class OrderDataHolder extends BaseRecyclerHolder {

    @BindView(R.id.pb_bar)
    ZzHorizontalProgressBar progressBar;
    @BindView(R.id.tv_rank)
    TextView mTvRank;
    private Context context;

    public OrderDataHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
    }


    public void setData(@NonNull List<OrderDataEntity> listBeanList, int position) {
        if (position == 0) {
            mTvRank.setText("TOP1");
            mTvRank.setTextColor(context.getColor(R.color.color_c2));
            progressBar.setGradientColor(context.getColor(R.color.color_42), context.getColor(R.color.color_42));
        } else if (position == 1) {
            mTvRank.setText("TOP2");
            mTvRank.setTextColor(context.getColor(R.color.color_f9));
            progressBar.setGradientColor(context.getColor(R.color.color_f9), context.getColor(R.color.color_f9));
        } else if (position == 2) {
            mTvRank.setText("TOP3");
            mTvRank.setTextColor(context.getColor(R.color.color_80));
            progressBar.setGradientColor(context.getColor(R.color.color_80), context.getColor(R.color.color_80));
        } else if (position == 3) {
            mTvRank.setText("TOP4");
            mTvRank.setTextColor(context.getColor(R.color.color_17));
            progressBar.setGradientColor(context.getColor(R.color.color_cc_42), context.getColor(R.color.color_cc_42));
        } else if (position == 4) {
            mTvRank.setText("TOP5");
            mTvRank.setTextColor(context.getColor(R.color.color_17));
            progressBar.setGradientColor(context.getColor(R.color.color_42_99), context.getColor(R.color.color_42_99));
        }
        progressBar.setProgress(80);
    }
}

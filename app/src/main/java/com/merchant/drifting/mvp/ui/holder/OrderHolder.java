package com.merchant.drifting.mvp.ui.holder;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.jess.arms.base.BaseRecyclerHolder;
import com.merchant.drifting.R;
import com.merchant.drifting.mvp.model.entity.OrderEntity;
import com.merchant.drifting.mvp.ui.activity.index.OrderDetailActivity;

import java.util.List;

import butterknife.BindView;

public class OrderHolder extends BaseRecyclerHolder {
    @BindView(R.id.ll_content)
    LinearLayout mLlContent;

    private Context context;
    public OrderHolder(View itemView) {
        super(itemView);
        context=itemView.getContext();
    }

    public void setData(@NonNull List<OrderEntity> listBeanList, int position) {
        mLlContent.setOnClickListener(v -> {
            OrderDetailActivity.start(context,false);
        });
    }
}

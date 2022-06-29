package com.merchant.drifting.mvp.ui.holder;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jess.arms.base.BaseRecyclerHolder;
import com.merchant.drifting.R;
import com.merchant.drifting.mvp.model.entity.RecordEntity;
import com.merchant.drifting.util.SpannableUtil;


import java.util.List;

import butterknife.BindView;

public class RecordHolder extends BaseRecyclerHolder {

    @BindView(R.id.tv_reason)
    TextView mTvReason;

    private SpannableStringBuilder passer;

    private Context context;
    public RecordHolder(View itemView) {
        super(itemView);
        context=itemView.getContext();
    }


    public void setData(@NonNull List<RecordEntity> listBeanList, int position) {
        passer = SpannableUtil.getBuilder(context, "原因： ").setForegroundColor(R.color.color_c2).append("照片模糊").build();
        mTvReason.setText(passer);
    }
}

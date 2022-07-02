package com.merchant.drifting.mvp.ui.holder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jess.arms.base.BaseRecyclerHolder;
import com.merchant.drifting.R;
import com.merchant.drifting.data.entity.TransactionEntity;
import com.merchant.drifting.mvp.ui.activity.index.AccountBalanceActivity;
import com.merchant.drifting.mvp.ui.activity.index.CommodityManagementActivity;
import com.merchant.drifting.mvp.ui.activity.index.OrderDataActivity;
import com.merchant.drifting.mvp.ui.activity.index.StoreManagementActivity;
import com.merchant.drifting.util.TextUtil;

import java.util.List;

import butterknife.BindView;

public class TransactionHolder extends BaseRecyclerHolder {
    @BindView(R.id.iv_pic)
    ImageView mIvPic;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.ll_content)
    LinearLayout mLlContent;
    private Context context;

    public TransactionHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
    }

    public void setData(@NonNull List<TransactionEntity> listBeanList, int position) {
        mIvPic.setImageResource(listBeanList.get(position).getPic());
        TextUtil.setText(mTvTitle, listBeanList.get(position).getTitle());

        mLlContent.setOnClickListener(v -> {
            if (position == 0) {
                AccountBalanceActivity.start(context, false);
            }else if (position==1){
                CommodityManagementActivity.start(context, false);
            } else if (position == 2) {
                OrderDataActivity.start(context, false);
            } else if (position == 3) {
                StoreManagementActivity.start(context, false);
            }
        });

    }
}

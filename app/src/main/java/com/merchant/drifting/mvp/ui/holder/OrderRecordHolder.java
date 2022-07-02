package com.merchant.drifting.mvp.ui.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.jess.arms.base.BaseRecyclerHolder;
import com.merchant.drifting.R;
import com.merchant.drifting.mvp.model.entity.OrderRecordEntity;
import com.merchant.drifting.util.StringUtil;
import com.merchant.drifting.util.TextUtil;

import java.util.List;

import butterknife.BindView;

public class OrderRecordHolder extends BaseRecyclerHolder {
    @BindView(R.id.iv_pic)
    ImageView mIvPic;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_sales_volume)
    TextView mTvSalesVolume;
    @BindView(R.id.tv_price)
    TextView mTvPrice;

    private Context context;

    public OrderRecordHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
    }


    public void setData(@NonNull List<OrderRecordEntity> listBeanList, int position) {
        TextUtil.setText(mTvTitle, listBeanList.get(position).getSku_name());
        TextUtil.setText(mTvSalesVolume, "今日销售额：" + listBeanList.get(position).getSales_volume());
        TextUtil.setText(mTvPrice, "¥" + StringUtil.frontCDecimalValue(listBeanList.get(position).getPrice()));
    }
}

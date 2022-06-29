package com.merchant.drifting.mvp.ui.adapter;

import android.view.View;
import android.widget.LinearLayout;

import com.jess.arms.base.BaseRecyclerAdapter;
import com.jess.arms.base.BaseRecyclerHolder;
import com.merchant.drifting.R;
import com.merchant.drifting.data.entity.TransactionEntity;
import com.merchant.drifting.mvp.ui.holder.TransactionHolder;

import java.util.List;

import butterknife.BindView;

public class TransactionAdapter extends BaseRecyclerAdapter<TransactionEntity> {


    public TransactionAdapter(List<TransactionEntity> infos) {
        super(infos);
    }


    @Override
    public void getHolder(BaseRecyclerHolder holder, int position) {
        TransactionHolder transactionHolder = (TransactionHolder) holder;
        transactionHolder.setData(mDatas, position);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_transaction;
    }

    @Override
    public BaseRecyclerHolder getCreateViewHolder(View view, int viewType) {
        return new TransactionHolder(view);
    }


}

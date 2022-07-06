package com.merchant.drifting.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseRecyclerAdapter;
import com.jess.arms.base.BaseRecyclerHolder;
import com.merchant.drifting.R;
import com.merchant.drifting.mvp.model.entity.CommodityManagerEntity;
import com.merchant.drifting.mvp.model.entity.GoodsListEntity;
import com.merchant.drifting.mvp.ui.holder.CommodityManagerHolder;
import com.merchant.drifting.mvp.ui.holder.OrderHolder;
import com.merchant.drifting.mvp.ui.holder.RecordHolder;

import java.util.ArrayList;
import java.util.List;

public class CommodityManagerAdapter extends BaseRecyclerAdapter<GoodsListEntity> {
    private List<Object> selectEntities = new ArrayList<>();
    private int sellectCount = 0;
    private boolean idDelete;
    private SeletChangeListener mSeletChangeListener;


    public List<Object> getSelectEntities() {
        return selectEntities;
    }


    public CommodityManagerAdapter(List<GoodsListEntity> infos, SeletChangeListener seletChangeListener) {
        super(infos);
        this.mSeletChangeListener = seletChangeListener;
    }


    @Override
    public void getHolder(BaseRecyclerHolder holder, int position) {
        CommodityManagerHolder commodityManagerHolder = (CommodityManagerHolder) holder;
        commodityManagerHolder.setData(mDatas, position);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_commodity_manager;
    }

    @Override
    public BaseRecyclerHolder getCreateViewHolder(View view, int viewType) {
        return new CommodityManagerHolder(view, this);
    }


    /**
     * 选中全部
     */
    public void selectAll() {
        if (selectEntities.size() > 0) {
            selectEntities.clear();
        }
        for (int i = 0; i < mDatas.size(); i++) {
            Object object = mDatas.get(i);
            if (object != null) {
                selectEntities.add(object);
            }
        }
        sellectCount = selectEntities.size();
        mSeletChangeListener.onSeletChange(sellectCount);
        notifyDataSetChanged();
    }


    /**
     * 选中状态更改
     */
    public void onItemCheckChange(Object object) {
        if (object != null) {
            if (!selectEntities.contains(object)) {
                selectEntities.add(object);
            } else {
                selectEntities.remove(object);
            }
            sellectCount = selectEntities.size();
            mSeletChangeListener.onSeletChange(sellectCount);
            notifyDataSetChanged();
        }
    }

    /**
     * 清除所有选中状态
     */
    public void clearCheck() {
        selectEntities.clear();
        sellectCount = selectEntities.size();
        notifyDataSetChanged();
        mSeletChangeListener.onSeletChange(sellectCount);
    }

    /**
     * 隐藏显示选中状态
     *
     * @param idDelete
     */
    public void setIdDelete(boolean idDelete) {
        this.idDelete = idDelete;
        notifyDataSetChanged();
    }

    public boolean isIdDelete() {
        return idDelete;
    }

    /**
     * 选中监听
     */
    public interface SeletChangeListener {
        void onSeletChange(int sellectCount);
    }
}

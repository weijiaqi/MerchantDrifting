package com.jess.arms.base;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerViewAdapter 基类
 *
 * @author zhaod
 * @date 2018/12/26
 */

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerHolder> {
    protected List<T> mDatas;
    private OnXRecyclerItemClickListner onXRecyclerItemClickListner = null;//三方XRecyclerView点击事件
    private OnRecyclerItemClickListner onRecyclerItemClickListner = null;//RecyclerView点击事件
    private OnXRecyclerItemLongClickListner onXRecyclerItemLongClickListner = null;//三方XRecyclerView长点击事件
    private OnRecyclerItemLongClickListner onRecyclerItemLongClickListner = null;//RecyclerView长点击事件

    private OnTabSelectListener onTabSelectListener;
    private int mCurrentTab = 0;
    private int mPreSelectedTabIndex = -1;

    public BaseRecyclerAdapter() {
    }

    public BaseRecyclerAdapter(List<T> mDatas) {
        this.mDatas = mDatas;
    }

    @NonNull
    @Override
    public BaseRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(viewType), parent, false);
        BaseRecyclerHolder mHolder = new BaseRecyclerHolder(view);
        mHolder = getCreateViewHolder(view, viewType);
        BaseRecyclerHolder finalMHolder = mHolder;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = finalMHolder.getLayoutPosition();
                if (onRecyclerItemClickListner != null) {
                    onRecyclerItemClickListner.onItemClickListner(v, position);
                }
                if (onXRecyclerItemClickListner != null) {
                    onXRecyclerItemClickListner.onItemClickListner(v, position - 1);
                }
                if (onTabSelectListener != null) {
                    mPreSelectedTabIndex = position;
                    if (mCurrentTab == mPreSelectedTabIndex) {
                        onTabSelectListener.onTabReselect(position);
                    } else {
                        onTabSelectListener.onTabSelect(position);
                    }
                    mCurrentTab = mPreSelectedTabIndex;
                }
            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position = finalMHolder.getLayoutPosition();
                if (onRecyclerItemLongClickListner != null) {
                    onRecyclerItemLongClickListner.onItemLongClickListner(v, position,finalMHolder);
                }
                if (onXRecyclerItemLongClickListner != null) {
                    onXRecyclerItemLongClickListner.onItemLongClickListner(v, position - 1);
                }
                return false;
            }
        });

        return mHolder;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerHolder holder, int position) {
        getHolder(holder, position);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerHolder holder, int position, List<Object> payloads) {
        if (payloads.isEmpty()) {
            getHolder(holder, position);
        } else {
            getHolder(holder, position, payloads);
        }
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public List<T> getDatas() {
        return mDatas;
    }

    /**
     * 刷新数据
     *
     * @param datas
     */
    public void refresh(List<T> datas) {
        this.mDatas.clear();
        this.mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     *
     * @param mDatas
     */
    public void setData(List<T> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public void clearData() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     *
     * @param datas
     */
    public void addData(List<T> datas) {
        if (this.mDatas == null) {
            this.mDatas = new ArrayList<>();
        }
        this.mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 添加数据(局部刷新，局部刷新时必须重写getItemId方法，同时setHasStableIds(true))
     *
     * @param datas
     */
    public void addDataWithoutAnim(List<T> datas) {
        if (datas == null)
            return;
        int size = mDatas.size();
        this.mDatas.addAll(datas);
        notifyItemRangeChanged(size, datas.size());
    }

    /**
     * 删除列表
     *
     * @param position
     */
    public void remove(int position) {
        mDatas.remove(position);
        int internalPosition = position;
        notifyItemRemoved(internalPosition);
        notifyItemRangeChanged(internalPosition, mDatas.size() - internalPosition);
    }

    /**
     * 需要重写的方法
     *
     * @param holder
     * @param position
     */
    public abstract void getHolder(BaseRecyclerHolder holder, int position);

    public void getHolder(BaseRecyclerHolder holder, int position, List<Object> payloads) {
    }

    protected abstract int getLayoutId(int viewType);

    public abstract BaseRecyclerHolder getCreateViewHolder(View view, int viewType);

    /**
     * RecyclerView点击事件
     *
     * @param onItemClickListner
     */
    public void setRecyclerItemClickListner(OnRecyclerItemClickListner onItemClickListner) {
        this.onRecyclerItemClickListner = onItemClickListner;
    }

    public OnRecyclerItemClickListner getOnRecyclerItemClickListner() {
        return onRecyclerItemClickListner;
    }

    public interface OnRecyclerItemClickListner {
        void onItemClickListner(View v, int position);
    }

    /**
     * XRecyclerView点击事件
     *
     * @param onItemClickListner
     */
    public void setXRecyclerItemClickListner(OnXRecyclerItemClickListner onItemClickListner) {
        this.onXRecyclerItemClickListner = onItemClickListner;
    }

    public interface OnXRecyclerItemClickListner {
        void onItemClickListner(View v, int position);
    }

    /**
     * RecyclerView长点击事件
     *
     * @param onItemClickListner
     */
    public void setRecyclerItemLongClickListner(OnRecyclerItemLongClickListner onItemClickListner) {
        this.onRecyclerItemLongClickListner = onItemClickListner;
    }

    public interface OnRecyclerItemLongClickListner {
        void onItemLongClickListner(View v, int position, RecyclerView.ViewHolder view);
    }

    /**
     * XRecyclerView长点击事件
     *
     * @param onItemClickListner
     */
    public void setXRecyclerItemLongClickListner(OnXRecyclerItemLongClickListner onItemClickListner) {
        this.onXRecyclerItemLongClickListner = onItemClickListner;
    }

    public interface OnXRecyclerItemLongClickListner {
        void onItemLongClickListner(View v, int position);
    }

    public void setOnTabSelectListener(OnTabSelectListener onTabSelectListener) {
        this.onTabSelectListener = onTabSelectListener;
    }

    /**
     * item点击监听
     */
    public interface OnTabSelectListener {
        /**
         * @param position 首次选中索引
         */
        void onTabSelect(int position);

        /**
         * @param position 第二次之后选中索引
         */
        void onTabReselect(int position);
    }

}

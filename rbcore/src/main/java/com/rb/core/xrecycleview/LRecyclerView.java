package com.rb.core.xrecycleview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.star.core.R;


/**
 * 自定义RecyclerView上拉加载
 *
 * @author zhaod
 * @date 2019/1/3
 */
public class LRecyclerView extends RecyclerView {
    private Context mContext;
    private float mLastY = -1;
    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_FOOTER = -3;
    private boolean loadingMoreEnabled = true;
    private XRecyclerViewFooter mFooter;
    private Adapter mWrapAdapter;
    private LoadingListener mLoadingListener;

    public LRecyclerView(Context context) {
        this(context, null);
    }

    public LRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        mFooter = new XRecyclerViewFooter(context);
        mFooter.setVisibility(GONE);
    }

    /**
     * 无更多加载数据
     */
    public void loadEndLine(String endlineText) {
        mFooter.setEndlineText(endlineText);
        mFooter.setState(XRecyclerViewFooter.STATE_ENDLINE);
    }

    /**
     * 无更多加载数据
     */
    public void loadEndLine() {
        mFooter.setEndlineText(mContext.getResources().getString(R.string.not_more_data));
        mFooter.setState(XRecyclerViewFooter.STATE_ENDLINE);
    }

    /**
     * 上拉加载完成后的，隐藏上拉加载布局
     */
    public void loadMoreComplete() {
//        if (previousTotal < getLayoutManager().getItemCount()) {
        mFooter.setState(XRecyclerViewFooter.STATE_COMPLETE);
//        }
    }

    /**
     * 设置是否可以上拉
     *
     * @param enabled
     */
    public void setLoadingMoreEnabled(boolean enabled) {
        loadingMoreEnabled = enabled;
    }

    /**
     * 设置是否可以上拉
     *
     * @param enabled
     */
    public void setLoadingMoreEnabled(boolean enabled, boolean isShowFoot) {
        loadingMoreEnabled = enabled;
        if (mFooter == null) return;
        if (!enabled && !isShowFoot) {
            LinearLayout ll_footer = mFooter.findViewById(R.id.ll_footer);
            if (ll_footer != null) {
                ll_footer.setVisibility(GONE);
            }
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        mWrapAdapter = new WrapAdapter(mFooter, adapter);
        super.setAdapter(mWrapAdapter);
        adapter.registerAdapterDataObserver(mDataObserver);
    }

    /**
     * 活动监听  判断是否到底，用于加载
     */
    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        if (state == RecyclerView.SCROLL_STATE_IDLE && mLoadingListener != null && loadingMoreEnabled) {
            LayoutManager layoutManager = getLayoutManager();
            int lastVisibleItemPosition;  //最后可见的Item的position的值
            if (layoutManager instanceof GridLayoutManager) {   //网格布局的中lastVisibleItemPosition的取值
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {//瀑布流布局中lastVisibleItemPosition的取值
                int[] into = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(into);
                lastVisibleItemPosition = findMax(into);
            } else {   //剩下只有线性布局（listview）中lastVisibleItemPosition的取值
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            }
            if (layoutManager.getChildCount() > 0 && lastVisibleItemPosition >= layoutManager.getItemCount() - 1
                    && layoutManager.getItemCount() > layoutManager.getChildCount()) {

                // 避免重复多次上拉加载
                if (!isFooterLoading()) {
                    mFooter.setState(XRecyclerViewFooter.STATE_LOADING);
                    mLoadingListener.onLoadMore();
                }
            }
        }
    }

    /**
     * 是否正在加载状态
     */
    public boolean isFooterLoading() {
        return mFooter.getVisibility() != View.GONE;
    }

    /**
     * 监听手势活动  判断有没有到顶，用于刷新
     */
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (mLastY == -1) {
            mLastY = e.getRawY();
        }
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                mLastY = e.getRawY();
                break;
            default:
                mLastY = -1; // reset
                break;
        }
        return super.onTouchEvent(e);

    }

    /**
     * 瀑布流里面用到的计算公式
     *
     * @param lastPositions
     * @return
     */
    int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    /**
     * adapter数据观察者
     */
    private final AdapterDataObserver mDataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            mWrapAdapter.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            mWrapAdapter.notifyItemRangeChanged(positionStart, itemCount, payload);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            mWrapAdapter.notifyItemMoved(fromPosition, toPosition);
        }
    };

    /**
     * 设配器重组
     */
    private class WrapAdapter extends Adapter<ViewHolder> {

        private Adapter adapter;

        private XRecyclerViewFooter mFootView;

        public WrapAdapter(XRecyclerViewFooter footView, Adapter adapter) {
            this.adapter = adapter;
            this.mFootView = footView;
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            LayoutManager manager = recyclerView.getLayoutManager();
            if (manager instanceof GridLayoutManager) {
                final GridLayoutManager gridManager = ((GridLayoutManager) manager);
                gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return (isFooter(position)) ? gridManager.getSpanCount() : 1;
                    }
                });
            }
        }

        @Override
        public void onViewAttachedToWindow(ViewHolder holder) {
            super.onViewAttachedToWindow(holder);
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams
                    && isFooter(holder.getLayoutPosition())) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }

        public boolean isFooter(int position) {
            return position < getItemCount() && position >= getItemCount() - 1;
        }

        public int getFootersCount() {
            return 1;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_FOOTER) {
                return new SimpleViewHolder(mFootView);
            }
            return adapter.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            int adjPosition = position;
            int adapterCount;
            if (adapter != null) {
                adapterCount = adapter.getItemCount();
                if (adjPosition < adapterCount) {
                    adapter.onBindViewHolder(holder, adjPosition);
                    return;
                }
            }
        }

        @Override
        public int getItemCount() {
            if (adapter != null) {
                return getFootersCount() + adapter.getItemCount();
            } else {
                return getFootersCount();
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (isFooter(position)) {
                return TYPE_FOOTER;
            }
            int adapterCount;
            if (adapter != null) {
                adapterCount = adapter.getItemCount();
                if (position < adapterCount) {
                    return adapter.getItemViewType(position);
                }
            }
            return TYPE_NORMAL;
        }

        @Override
        public long getItemId(int position) {
            if (adapter != null) {
                int adapterCount = adapter.getItemCount();
                if (position < adapterCount) {
                    return adapter.getItemId(position);
                }
            }
            return -1;
        }

        @Override
        public void unregisterAdapterDataObserver(AdapterDataObserver observer) {
            if (adapter != null) {
                adapter.unregisterAdapterDataObserver(observer);
            }
        }

        @Override
        public void registerAdapterDataObserver(AdapterDataObserver observer) {
            if (adapter != null) {
                adapter.registerAdapterDataObserver(observer);
            }
        }

        private class SimpleViewHolder extends ViewHolder {
            public SimpleViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

    /**
     * 监听接口
     */
    public void setLoadingListener(LoadingListener listener) {
        mLoadingListener = listener;
    }

    public interface LoadingListener {

        void onLoadMore();
    }

}

package com.rb.core.xrecycleview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.rb.core.util.Utils;
import com.star.core.R;


import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView自定义上拉加载下拉刷新
 *
 * @author zhaod
 * @date 2018/12/10
 */
public class XRecyclerView extends RecyclerView {
    private static final String TAG = XRecyclerView.class.getSimpleName();
    private Context mContext;
    private ArrayList<View> mHeaderViews = new ArrayList<>();
    private XRecyclerViewHeader mHeader;
    private XRecyclerViewFooter mFooter;
    private Adapter mAdapter;//传入的Adapter
    private Adapter mWrapAdapter;//组合的Adapter
    private float mLastY = -1; //记录的Y坐标
    private static final float DRAG_RATE = 2;//阻力率
    private LoadingListener mLoadingListener;//滑动监听
    private ScrollListener mScrollListener;//item监听
    private ScrollIdleListener mScrollIdleListener;//item监听
    private offsetDistenceListener mDistenceListener;//头部高度监听
    private boolean pullRefreshEnabled = true;   //刷新状态
    private boolean loadingMoreEnabled = true;   //上拉状态

    private static final int TYPE_REFRESH_HEADER = -5; //添加刷新头
    private static final int TYPE_HEADER = -4;  //添加头部
    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_FOOTER = -3;//添加上拉加载布局
    private int previousTotal = 0;//  记录ITEM的条数
    private Handler mHandler = new Handler();
    private List<DelayRunnable> mListRunnable;
    private int delayTimes = 1000;
    private int mFirstY;
    private int mFirstItemPosition = -1, mLastItemPosition, mInitfirstItemPosition = 1, mEndfirstItemPosition = -1;
    private XRecyclerViewHeader.DistencecCallBack distencecCallBack;

    public XRecyclerView(Context context) {
        this(context, null);
    }

    public XRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        if (pullRefreshEnabled) {
            XRecyclerViewHeader xRecyclerViewHeader = new XRecyclerViewHeader(mContext);
            mHeaderViews.add(0, xRecyclerViewHeader);
            mHeader = xRecyclerViewHeader;
        }
        distencecCallBack = new XRecyclerViewHeader.DistencecCallBack() {
            @Override
            public void onDistenceChange(int distence) {
                if (mDistenceListener != null) {
                    mDistenceListener.offsetDistence(distence);
                }
            }
        };
        mHeader.setDistencecCallBack(distencecCallBack);
        mFooter = new XRecyclerViewFooter(mContext);
        mFooter.setVisibility(GONE);
    }

    public void setDistenceListener(offsetDistenceListener distenceListener) {
        this.mDistenceListener = distenceListener;
    }

    //添加头部文件的时候 判断有没有刷新头
    public void addHeaderView(View view) {
        if (pullRefreshEnabled && !(mHeaderViews.get(0) instanceof XRecyclerViewHeader)) {
            XRecyclerViewHeader xRecyclerViewHeader = new XRecyclerViewHeader(mContext);
            mHeaderViews.add(0, xRecyclerViewHeader);
            mHeader = xRecyclerViewHeader;
        }
        mHeader.setDistencecCallBack(distencecCallBack);
        mHeaderViews.add(view);
    }

    /**
     * 无更多加载数据
     */
    public void loadEndLine(String endlineText) {
        mFooter.setEndlineText(endlineText);
        mFooter.setState(XRecyclerViewFooter.STATE_ENDLINE);
        if (getLayoutManager() == null) return;
        previousTotal = getLayoutManager().getItemCount();
    }

    /**
     * 无更多加载数据
     */
    public void loadEndLine() {
        mFooter.setEndlineText(mContext.getResources().getString(R.string.not_more_data));
        mFooter.setState(XRecyclerViewFooter.STATE_ENDLINE);
        if (getLayoutManager() == null) return;
        previousTotal = getLayoutManager().getItemCount();
    }

    /**
     * 上拉加载完成后的，隐藏上拉加载布局
     */
    public void loadMoreComplete() {
//        if (previousTotal < getLayoutManager().getItemCount()) {
        mFooter.setState(XRecyclerViewFooter.STATE_COMPLETE);
//        }
        if (getLayoutManager() == null) return;
        previousTotal = getLayoutManager().getItemCount();
    }

    /**
     * 下拉刷新完成后的，隐藏下拉加载布局
     */
    public void refreshComplete() {
        mHeader.refreshComplate();
    }

    /**
     * 上拉和下拉刷新完成后的 ，隐藏加载布局
     */
    public void refreshEndComplete() {
        refreshComplete();
        loadMoreComplete();
    }

    /**
     * 设置是否可以刷新
     *
     * @param enabled
     */
    public void setPullRefreshEnabled(boolean enabled) {
        pullRefreshEnabled = enabled;
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

    /**
     * 设置是否显示footer
     */
    public void setShowFoot(boolean isShowFoot) {
        if (mFooter == null) return;
        LinearLayout ll_footer = mFooter.findViewById(R.id.ll_footer);
        if (isShowFoot) {
            if (ll_footer != null) {
                ll_footer.setVisibility(VISIBLE);
            }
        } else {
            if (ll_footer != null) {
                ll_footer.setVisibility(GONE);
            }
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        mAdapter = adapter;
        mWrapAdapter = new WrapAdapter(mHeaderViews, mFooter, adapter);
        super.setAdapter(mWrapAdapter);
        mAdapter.registerAdapterDataObserver(mDataObserver);
    }

    /**
     * 活动监听  判断是否到底，用于加载
     */
    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        if (state == RecyclerView.SCROLL_STATE_IDLE && mScrollIdleListener != null &&
                mEndfirstItemPosition != mInitfirstItemPosition) {
            mScrollIdleListener.onIdlePosition(mInitfirstItemPosition);
            mEndfirstItemPosition = mInitfirstItemPosition;
        }
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
            if (layoutManager.getChildCount() > 0
                    && lastVisibleItemPosition >= layoutManager.getItemCount() - 1 && layoutManager.getItemCount() > layoutManager.getChildCount()
                    && mHeader.getState() < XRecyclerViewHeader.STATE_REFRESHING) {

                // 避免重复多次上拉加载
                if (!isFooterLoading()) {
                    mFooter.setState(XRecyclerViewFooter.STATE_LOADING);
                    mLoadingListener.onLoadMore();
                }
            }
        }
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
            int lastItemPosition = linearManager.findLastVisibleItemPosition();
            mInitfirstItemPosition = linearManager.findFirstVisibleItemPosition();
            int visibleItemCount = linearManager.getChildCount();

            if (mFirstItemPosition < mInitfirstItemPosition) {
                mFirstItemPosition = mInitfirstItemPosition;
                mLastItemPosition = lastItemPosition;

                if (mScrollListener != null) {
                    mScrollListener.onPosition((mInitfirstItemPosition - 2));
                }
            } else if (mLastItemPosition > lastItemPosition) {
                mFirstItemPosition = mInitfirstItemPosition;
                mLastItemPosition = lastItemPosition;

                if (mScrollListener != null) {
                    mScrollListener.onPosition(lastItemPosition);
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
                final float deltaY = e.getRawY() - mLastY;
                mLastY = e.getRawY();
                if (isOnTop() && pullRefreshEnabled) {
                    mHeader.onMove(deltaY / DRAG_RATE);
                    if (mHeader.getVisiableHeight() > 0 && mHeader.getState() < XRecyclerViewHeader.STATE_REFRESHING) {
                        if (mDistenceListener != null) {
                            mDistenceListener.offsetDistence(mHeader.getVisiableHeight());
                        }
                        Log.i("getVisiableHeight", "getVisiableHeight = " + mHeader.getVisiableHeight());
                        Log.i("getVisiableHeight", " mHeader.getState() = " + mHeader.getState());
                        return false;
                    }
                }
                break;
            default:
                mLastY = -1; // reset
                if (isOnTop() && pullRefreshEnabled) {
                    if (mHeader.releaseAction()) {
                        if (mLoadingListener != null) {
                            finishRefresh(delayTimes);
                        }
                    }
                }
                break;
        }
        return super.onTouchEvent(e);

    }

    public void finishRefresh(int delayTime) {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                mLoadingListener.onRefresh();
                previousTotal = 0;
            }
        }, delayTime <= 0 ? 1 : delayTime);
    }

    public boolean postDelayed(Runnable runnable, long delayTime) {
        if (delayTime == 0) {
            new DelayRunnable(runnable, 0).run();
            return true;
        }
        if (mHandler == null) {
            mListRunnable = mListRunnable == null ? new ArrayList<DelayRunnable>() : mListRunnable;
            mListRunnable.add(new DelayRunnable(runnable, delayTime));
            return false;
        }
        return mHandler.postDelayed(new DelayRunnable(runnable, 0), delayTime);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mListRunnable != null) {
            for (DelayRunnable runnable : mListRunnable) {
                mHandler.removeCallbacks(runnable);
            }
            mListRunnable.clear();
            mListRunnable = null;
        }
    }

    /**
     * 判断是不是在顶部
     *
     * @return
     */
    private boolean isOnTop() {
        if (mHeaderViews == null || mHeaderViews.isEmpty()) {
            return false;
        }

        View view = mHeaderViews.get(0);
        return view.getParent() != null;
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

        private ArrayList<View> mHeaderViews;

        private XRecyclerViewFooter mFootView;

        private int headerPosition = 1;

        public WrapAdapter(ArrayList<View> headerViews, XRecyclerViewFooter footView, Adapter adapter) {
            this.adapter = adapter;
            this.mHeaderViews = headerViews;
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
                        return (isHeader(position) || isFooter(position))
                                ? gridManager.getSpanCount() : 1;
                    }
                });
            }
        }

        @Override
        public void onViewAttachedToWindow(ViewHolder holder) {
            super.onViewAttachedToWindow(holder);
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp != null
                    && lp instanceof StaggeredGridLayoutManager.LayoutParams
                    && (isHeader(holder.getLayoutPosition()) || isFooter(holder.getLayoutPosition()))) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }

        public boolean isHeader(int position) {
            return position >= 0 && position < mHeaderViews.size();
        }

        public boolean isFooter(int position) {
            return position < getItemCount() && position >= getItemCount() - 1;
        }

        public boolean isRefreshHeader(int position) {
            return position == 0;
        }

        public int getHeadersCount() {
            return mHeaderViews.size();
        }

        public int getFootersCount() {
            return 1;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_REFRESH_HEADER) {
                return new SimpleViewHolder(mHeaderViews.get(0));
            } else if (viewType == TYPE_HEADER) {
                return new SimpleViewHolder(mHeaderViews.get(headerPosition++));
            } else if (viewType == TYPE_FOOTER) {
                return new SimpleViewHolder(mFootView);
            }
            return adapter.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (isHeader(position)) {
                return;
            }
            int adjPosition = position - getHeadersCount();
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
        public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
            if (payloads.isEmpty()) {
                onBindViewHolder(holder, position);
            } else {
                if (isHeader(position)) {
                    return;
                }
                int adjPosition = position - getHeadersCount();
                int adapterCount;
                if (adapter != null) {
                    adapterCount = adapter.getItemCount();
                    if (adjPosition < adapterCount) {
                        adapter.onBindViewHolder(holder, adjPosition, payloads);
                        return;
                    }
                }
            }
        }

        @Override
        public int getItemCount() {
            if (adapter != null) {
                return getHeadersCount() + getFootersCount() + adapter.getItemCount();
            } else {
                return getHeadersCount() + getFootersCount();
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (isRefreshHeader(position)) {
                return TYPE_REFRESH_HEADER;
            }
            if (isHeader(position)) {
                return TYPE_HEADER;
            }
            if (isFooter(position)) {
                return TYPE_FOOTER;
            }
            int adjPosition = position - getHeadersCount();
            int adapterCount;
            if (adapter != null) {
                adapterCount = adapter.getItemCount();
                if (adjPosition < adapterCount) {
                    return adapter.getItemViewType(adjPosition);
                }
            }
            return TYPE_NORMAL;
        }

        @Override
        public long getItemId(int position) {
            if (adapter != null && position >= getHeadersCount()) {
                int adjPosition = position - getHeadersCount();
                int adapterCount = adapter.getItemCount();
                if (adjPosition < adapterCount) {
                    return adapter.getItemId(adjPosition);
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
     * 手动下拉刷新
     */
    public void manualHideView() {
        if (mHeader.getState() == XRecyclerViewHeader.STATE_REFRESHING) {
            return;
        }
        int containerHeight = Utils.dp2px(getContext(), 46);
        int animHeight = Utils.dp2px(getContext(), 50);
        startAnimator(mHeader.mContainer, animHeight, containerHeight);
        mHeader.setState(XRecyclerViewHeader.STATE_REFRESHING);
    }

    //手动上拉加载
    public void loadMore() {
        mFooter.setState(XRecyclerViewFooter.STATE_LOADING);
        mLoadingListener.onLoadMore();
    }

    private void startAnimator(final View view, int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.setDuration(300);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.getLayoutParams().height = (int) animation.getAnimatedValue();
                view.requestLayout();
            }
        });
        animator.start();
        finishRefresh(delayTimes);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) mFirstY = (int) e.getY();
        return super.onInterceptTouchEvent(e);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    public int getTouchPointY() {
        return mFirstY;
    }

    /**
     * 监听接口
     */
    public void setLoadingListener(LoadingListener listener) {
        mLoadingListener = listener;
    }

    public interface LoadingListener {

        void onRefresh();

        void onLoadMore();
    }

    public void setRefreshing(boolean refreshing) {
        if (refreshing && pullRefreshEnabled && mLoadingListener != null) {
            mHeader.setState(XRecyclerViewHeader.STATE_REFRESHING);
            mHeader.onMove(mHeader.getMeasuredHeight());
            finishRefresh(delayTimes);
        }
    }

    public void setScrollListener(ScrollListener listener) {
        mScrollListener = listener;
    }

    public interface ScrollListener {
        void onPosition(int position);
    }

    public void setScrollIdleListener(ScrollIdleListener listener) {
        mScrollIdleListener = listener;
    }

    public interface ScrollIdleListener {
        void onIdlePosition(int position);
    }

    public interface offsetDistenceListener {
        void offsetDistence(int distence);
    }
}


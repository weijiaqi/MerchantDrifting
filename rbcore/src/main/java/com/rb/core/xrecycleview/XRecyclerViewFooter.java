package com.rb.core.xrecycleview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.star.core.R;


/**
 * RecyclerView尾布局
 */
public class XRecyclerViewFooter extends LinearLayout {
    private LinearLayout mContainer;//布局指向
    private Context mContext;//上下文引用

    public final static int STATE_LOADING = 0; //正在加载
    public final static int STATE_COMPLETE = 1;  //加载完成
    public final static int STATE_ENDLINE = 2;  // 无更多加载数据

    private String mEndlineText = "";    // 无更多数据文本
    private ProgressBar mProgressBar;    // 正在刷新的图标
    private TextView mProgressText;    // 正在刷新的文案

    public XRecyclerViewFooter(Context context) {
        super(context);
        initView(context);
    }

    public XRecyclerViewFooter(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    public XRecyclerViewFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    //初始化
    private void initView(Context context) {
        mContext = context;
        mContainer = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.listview_footer, null);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 0);
        this.setLayoutParams(lp);
        this.setPadding(0, 0, 0, 0);
        addView(mContainer, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        setGravity(Gravity.CENTER);

        mProgressBar = findViewById(R.id.progress_par);
        mProgressText = findViewById(R.id.listview_foot_more);

        measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 设置无更多数据文本
     *
     * @param endlineText
     */
    public void setEndlineText(String endlineText) {
        this.mEndlineText = endlineText;
    }

    public void setState(int state) {
        switch (state) {
            case STATE_LOADING:
                mProgressBar.setVisibility(VISIBLE);
                mProgressText.setText(mContext.getResources().getString(R.string.loading));
                this.setVisibility(VISIBLE);
                break;
            case STATE_COMPLETE:
                mProgressBar.clearAnimation();
                mProgressBar.setVisibility(VISIBLE);
                this.setVisibility(GONE);
                break;
            case STATE_ENDLINE: {
                mProgressBar.clearAnimation();
                mProgressBar.setVisibility(GONE);
                mProgressText.setText(mEndlineText);
                break;
            }
            default:
                break;
        }
    }
}

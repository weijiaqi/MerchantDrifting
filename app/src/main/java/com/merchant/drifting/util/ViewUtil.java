package com.merchant.drifting.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.merchant.drifting.R;
import com.merchant.drifting.mvp.ui.dialog.LoadingDialog;

import java.util.Stack;

/**
 * dialog loading
 * <p>
 * 布局中加载loading
 * <p>
 * 动态添加无网络、无数据、404布局
 */

public class ViewUtil {
    private static ViewUtil mAddUtil;
    private LoadingDialog mLoadingDialog;
    private static Stack<View> mStack = new Stack<>();
    public static final int NOT_SERVER = 0;
    public static final int NOT_NETWORK = 1;
    public static final int NOT_DATA = 2;
    public static final int NOT_NULL = 3;
    public static final int HAS_DATA = 4;
    public static final int HAS_DELETED = 5;
    public static final int NOT_LOGIN = 6;
    public static final int MARKET_CLOSED = 7;
    public static final int SETTLE_IN_REVIEWING = 8;
    public static final int NOT_INIT = 9;


    private ViewUtil() {
    }

    public static ViewUtil create() {
        if (mAddUtil == null) {
            synchronized (ViewUtil.class) {
                if (mAddUtil == null) {
                    mAddUtil = new ViewUtil();
                }
            }
        }
        return mAddUtil;
    }

    /**
     * loading开启
     *
     * @param activity activity_release_scheme
     */
    public void show(Activity activity) {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(activity, R.style.loading_dialog);
            mLoadingDialog.show();
        } else {
            if (!mLoadingDialog.isShowing()) {
                mLoadingDialog.show();
            }
        }
    }

    /**
     * loading关闭
     */
    public void dismiss() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }

    /**
     * 布局loading
     *
     * @param context     context
     * @param frameLayout 容器
     */
    @SuppressLint("InflateParams")
    public void setAnimation(Context context, FrameLayout frameLayout) {
        if (frameLayout == null) return;
        if (mStack.size() > 0) {
            frameLayout.removeAllViews();
        }
        View inflate = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.layout_loading, null);
        RelativeLayout relativeLayout = inflate.findViewById(R.id.rl_totle);
        frameLayout.addView(relativeLayout);
        mStack.push(relativeLayout);
    }

    /**
     * @param context     context
     * @param frameLayout 容器
     * @param type        NOT_SERVER 404    NOT_NETWORK 无网络
     *                    HAS_DELETED 内容被删除
     *                    NOT_DATA 无数据   NOT_NULL 容器移除
     */
    @SuppressLint("InflateParams")
    public void setView(Context context, FrameLayout frameLayout, int type) {
        if (frameLayout == null) return;
        if (mStack.size() > 0) {
            frameLayout.removeAllViews();
        }
        if (type == NOT_NULL) {
            frameLayout.setVisibility(View.GONE);
        } else {
            View inflate = LayoutInflater.from(context).inflate(R.layout.layout_not_data, null);
            LinearLayout linearLayout = inflate.findViewById(R.id.ll_retry);
            ImageView imageView = inflate.findViewById(R.id.iv_show);
            TextView textView = inflate.findViewById(R.id.tv_content);

            if (type == NOT_SERVER) {
                imageView.setImageResource(R.drawable.icon_not_network);
                textView.setText("暂无内容");
            } else if (type == NOT_NETWORK) {
                imageView.setImageResource(R.drawable.icon_not_network);
                textView.setText("网络连接失败");
            } else if (type == HAS_DELETED) {
                imageView.setImageResource(R.drawable.icon_not_data);
                textView.setText("此内容已被删除，若有疑问请联系官方");
            }  else {
                imageView.setImageResource(R.drawable.icon_not_data);
                textView.setText("暂无内容");
            }
            frameLayout.addView(linearLayout);
            frameLayout.setVisibility(View.VISIBLE);
            mStack.push(linearLayout);
        }
    }

    /**
     * 自定义显示内容
     *
     * @param context     context
     * @param frameLayout 容器
     * @param drawable    图片
     * @param content     内容
     */
    public void setView(Context context, FrameLayout frameLayout, int drawable, String content,float size) {
        if (frameLayout == null) return;
        if (mStack.size() > 0) {
            frameLayout.removeAllViews();
        }
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_not_data, null);
        LinearLayout linearLayout = inflate.findViewById(R.id.ll_retry);
        ImageView imageView = inflate.findViewById(R.id.iv_show);
        TextView textView = inflate.findViewById(R.id.tv_content);

        imageView.setImageResource(drawable);
        textView.setText(content);
        textView.setTextSize(size);
        frameLayout.addView(inflate);
        frameLayout.setVisibility(View.VISIBLE);
        mStack.push(inflate);
    }



    /**
     * 容器移除
     *
     * @param frameLayout 容器
     */
    public void setView(FrameLayout frameLayout) {
        if (frameLayout == null) return;
        frameLayout.setVisibility(View.GONE);
        if (mStack.size() > 0) {
            frameLayout.removeAllViews();
        }
    }

}

package com.jess.arms.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;


import com.jess.arms.R;

import java.util.List;


/**
 * Dialog基类
 *
 * @author
 * @date
 */

public abstract class BaseDialog extends Dialog {
    protected OnClickCallback onClickCallback;
    protected OnContentClickCallback onContentClickCallback;
    protected OnTypeClickCallback onTypeClickCallback;
    protected OnMoreClickCallback onMoreClickCallback;
    public BaseDialog(@NonNull Context context) {
        super(context, R.style.base_dialog);
    }

    public BaseDialog(Context context, int dialogStyle) {
        super(context, dialogStyle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        Window window = this.getWindow();
        getWindows(window);
        if (window != null) {
            WindowManager windowManager = window.getWindowManager();
            Display display = windowManager.getDefaultDisplay();
            WindowManager.LayoutParams p = window.getAttributes();
            p.width = (int) (display.getWidth() * getDialogWith());
        }

        initView();
        initDatas();
        initEvents();
    }

    /**
     * 获取布局文件
     *
     * @return
     */
    protected abstract int getContentView();

    /**
     * 获取Window
     *
     * @param window
     */
    protected void getWindows(Window window) {
    }

    /**
     * 初始化view
     */
    protected void initView() {
    }

    /**
     * 初始化数据
     */
    protected void initDatas() {
    }

    /**
     * 监听
     */
    protected void initEvents() {
    }

    /**
     * dialog宽度
     *
     * @return 若布局中定义宽度，默认写1.0f
     */
    protected abstract float getDialogWith();

    /**
     * 点击回调
     * <p>
     * type类型判断
     */
    public interface OnClickCallback {
        void onClickType(int type);
    }

    public void setOnClickCallback(OnClickCallback onClickCallback) {
        this.onClickCallback = onClickCallback;
    }

    /**
     * 点击回调内容
     */

    public interface OnTypeClickCallback {
        void onTypeClick(int type);
    }

    public interface OnContentClickCallback {
        void onContetClick(String content);
    }

    public interface OnMoreClickCallback {
        void onMoreClick(int type, List<Object> list);
    }


    public void setOnTypeClickCallback(OnTypeClickCallback onTypeClickCallback) {
        this.onTypeClickCallback = onTypeClickCallback;
    }

    public void setOnContentClickCallback(OnContentClickCallback onContentClickCallback) {
        this.onContentClickCallback = onContentClickCallback;
    }

    public void setOnMoreClickCallback(OnMoreClickCallback onMoreClickCallback) {
        this.onMoreClickCallback = onMoreClickCallback;
    }
}

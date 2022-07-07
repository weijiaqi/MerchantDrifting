package com.merchant.drifting.view.customWebview;

/**
 * @Description: 加载进度条
 * @Author: wjq
 * @CreateDate: 2021/9/1 15:11
 */
public interface WebLoadingListener {
    /**
     * 加载错误
     */
    void onLoadError();

    /**
     * 开始加载
     */
    void onLoadStart();

    /**
     * 加载结束
     */
    void onLoadFinish();

    /**
     * 进度条变化时调用
     *
     * @param newProgress 进度0-100
     */
    void startProgress(int newProgress);

}

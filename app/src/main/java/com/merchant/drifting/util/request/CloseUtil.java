package com.merchant.drifting.util.request;

import android.util.SparseArray;

import java.io.Closeable;
import java.io.IOException;

import io.reactivex.disposables.Disposable;

/**
 * 关闭工具类
 *
 * @author wjq
 * @date 2021/8/27
 */

public class CloseUtil {
    private CloseUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 取消订阅事件
     */
    public static void unDisposable(Disposable... disposables) {
        if (disposables == null) return;
        for (Disposable disposable : disposables) {
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
            }
        }
    }

    /**
     * 取消订阅事件
     */
    public static void unSubscribe(SparseArray<Disposable> disposables) {
        if (disposables == null) return;
        for (int index = 0; index < disposables.size(); index++) {
            int key = disposables.keyAt(0);
            Disposable disposable = disposables.get(key);
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
            }
        }
    }

    /**
     * 取消订阅事件（清空集合）
     */
    public static void unSubscribeList(SparseArray<Disposable> disposables) {
        if (disposables == null) return;
        for (int index = 0; index < disposables.size(); index++) {
            int key = disposables.keyAt(0);
            Disposable disposable = disposables.get(key);
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
            }
        }
        disposables.clear();
    }

    /**
     * 关闭io等操作
     *
     * @param closeables 可关闭的Closeable
     */
    public static void close(Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

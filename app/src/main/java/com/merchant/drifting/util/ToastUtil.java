package com.merchant.drifting.util;


import android.view.Gravity;
import com.hjq.toast.ToastUtils;
import com.merchant.drifting.R;

/**
 * @Description: Toast输出
 * @Author: wjq
 * @CreateDate: 2022/2/15 11:09
 */

public class ToastUtil {

    /**
     * 普通中间Toast
     */
    public static void showToast(String content) {
        ToastUtils.setView(R.layout.toast_layout_center);
        ToastUtils.setGravity(Gravity.CENTER);
        ToastUtils.show(content);
    }

    /**
     * 普通中间Toast
     */
    public static void showBottomToast(String content) {
        ToastUtils.setView(R.layout.toast_layout_center);
        ToastUtils.setGravity(Gravity.BOTTOM);
        ToastUtils.show(content);
    }


}

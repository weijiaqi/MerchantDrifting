package com.merchant.drifting.util;

/**
 * 防止重复点击
 *
 * @author wjq
 * @date 2021/8/27
 */

public class ClickUtil {
    private static long lastClickTime = 0;
    private static long DIFF = 500;
    private static int lastButtonId = -1;

    public static boolean isFastClick() {
        return isFastClick(-1, DIFF);
    }

    public static boolean isFastClick(int buttonId) {
        return isFastClick(buttonId, DIFF);
    }

    public static boolean isFastClick(int buttonId, long diff) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (lastButtonId == buttonId && lastClickTime > 0 && timeD < diff) {
            return true;
        }
        lastClickTime = time;
        lastButtonId = buttonId;
        return false;
    }
}

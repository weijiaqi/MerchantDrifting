package com.rb.core.xrecycleview;

/**
 * @author zhaod
 * @date 2019/1/22
 */

public class DelayRunnable implements Runnable {
    public long delayTime;
    private Runnable runnable = null;
    public DelayRunnable(Runnable runnable, long delayTime) {
        this.runnable = runnable;
        this.delayTime = delayTime;
    }
    @Override
    public void run() {
        try {
            if (runnable != null) {
                runnable.run();
                runnable = null;
            }
        } catch (Throwable e) {
            if (!(e instanceof NoClassDefFoundError)) {
                e.printStackTrace();
            }
        }
    }
}

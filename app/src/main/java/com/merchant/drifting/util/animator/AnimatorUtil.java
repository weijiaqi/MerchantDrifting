package com.merchant.drifting.util.animator;

import static android.view.View.SCALE_X;
import static android.view.View.SCALE_Y;
import static android.view.View.TRANSLATION_Y;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class AnimatorUtil {

    /**
     * @Description: 上下抖动
     * @Author : WeiJiaQI
     * @Time : 2022/6/16 18:13
     */
    public static void floatAnim(View view, int delay) {
        ObjectAnimator translationYAnim = ObjectAnimator.ofFloat(view, TRANSLATION_Y, -10.0f, 10.0f, -10.0f);
        translationYAnim.setDuration(delay);
        translationYAnim.setRepeatCount(ValueAnimator.INFINITE);
        translationYAnim.start();
    }

    /**
     * 属性动画
     * 缩放
     */
    public static void ZoomAnim(View view, int delay) {
        // 将一个TextView沿垂直方向先从原大小（1f）放大到5倍大小（5f），然后再变回原大小。
        ObjectAnimator translationX = ObjectAnimator.ofFloat(view, SCALE_X, 1f, 0.9f, 1f);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(view, SCALE_Y, 1f, 0.9f, 1f);
        translationX.setRepeatCount(ValueAnimator.INFINITE);
        translationY.setRepeatCount(ValueAnimator.INFINITE);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(translationX).with(translationY);
        animatorSet.setDuration(delay);
        animatorSet.start();
    }

}

package com.merchant.drifting.view;


import android.view.View;

import androidx.viewpager.widget.ViewPager;



public class ScaleInTransformer implements ViewPager.PageTransformer {
    private static final float DEFAULT_MIN_SCALE = 0.9f;
    private float mMinScale = DEFAULT_MIN_SCALE;//view缩小值
    public static final float DEFAULT_CENTER = 0.5f;
    private int pageWidth;
    private int pageHeight;

    @Override
    public void transformPage(View view, float position) {

        pageWidth = view.getWidth();
        pageHeight = view.getHeight();

        view.setPivotY(pageHeight / 2);
        view.setPivotX(pageWidth / 2);
      if (position < -1.0f) {
            // [-Infinity,-1)
            // view移动到最左边，在屏幕之外
            handleInvisiblePage(view, position);
        } else if (position < 0.0f) {
            // [-1,0]
            // view移动到左边
            handleLeftPage(view, position);
        } else if (position <= 1.0f) {
            // view移动到右边
            handleRightPage(view, position);
        } else {
            // (1,+Infinity]
            //  view移动到右边，在屏幕之外

            view.setPivotX(0);
            view.setPivotY((float) (pageHeight*DEFAULT_CENTER));
            view.setScaleX(mMinScale);
            view.setScaleY(mMinScale);
        }
    }

    public void handleInvisiblePage(View view, float position) {

        view.setScaleX(mMinScale);
        view.setScaleY(mMinScale);
        view.setPivotX(pageWidth);
        view.setPivotY((float) (pageHeight*DEFAULT_CENTER));
    }

    public void handleLeftPage(View view, float position) {

        float scaleFactor = (1 + position) * (1 - mMinScale) + mMinScale;
        view.setScaleX(scaleFactor);
        view.setScaleY(scaleFactor);

        view.setPivotX(pageWidth * (DEFAULT_CENTER + (DEFAULT_CENTER * -position)));
        view.setPivotY((float) (pageHeight*DEFAULT_CENTER ));
    }

    public void handleRightPage(View view, float position) {

        float scaleFactor = (1 - position) * (1 - mMinScale) + mMinScale;
        view.setScaleX(scaleFactor);
        view.setScaleY(scaleFactor);
        view.setPivotX(pageWidth * ((1 - position) * DEFAULT_CENTER));
        view.setPivotY((float) (pageHeight*DEFAULT_CENTER));
    }
}
package com.merchant.drifting.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;

import com.merchant.drifting.R;

/**
 * @Description:
 * @Author: wjq
 * @CreateDate: 2022/2/16 17:46
 */
@SuppressLint("DrawAllocation")
public class RatioImageView extends AppCompatImageView {
    private Context mContext;
    private float mWidth;
    private float mHeight;
    private float mAspectRatio;
    private float mCornerRadus;
    private float leftTopRadius;
    private float rightTopRadius;
    private float rightBottomRadius;
    private float leftBottomRadius;

    public RatioImageView(Context context) {
        this(context, null);
        init();
    }

    public RatioImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public RatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        mContext = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RatioImageView);
        mAspectRatio = ta.getFloat(R.styleable.RatioImageView_aspect_ratio, mAspectRatio);
        mCornerRadus = ta.getDimension(R.styleable.RatioImageView_corner_radius, dp2px(mCornerRadus));
        leftTopRadius = ta.getDimension(R.styleable.RatioImageView_left_top_radius, dp2px(leftTopRadius));
        rightTopRadius = ta.getDimension(R.styleable.RatioImageView_right_top_radius, dp2px(rightTopRadius));
        leftBottomRadius = ta.getDimension(R.styleable.RatioImageView_left_bottom_radius, dp2px(leftBottomRadius));
        rightBottomRadius = ta.getDimension(R.styleable.RatioImageView_right_bottom_radius, dp2px(rightBottomRadius));

        if (leftTopRadius == 0f) {
            leftTopRadius = mCornerRadus;
        }
        if (rightTopRadius == 0f) {
            rightTopRadius = mCornerRadus;
        }
        if (rightBottomRadius == 0f) {
            rightBottomRadius = mCornerRadus;
        }
        if (leftBottomRadius == 0f) {
            leftBottomRadius = mCornerRadus;
        }
        ta.recycle();
    }

    private void init() {
        if (Build.VERSION.SDK_INT < 18) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mAspectRatio > 0) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = (int) (width / mAspectRatio);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth = getWidth();
        mHeight = getHeight();
    }

    /**
     * 设置宽高比例
     *
     * @param aspectRatio
     */
    public void setAspectRatio(float aspectRatio) {
        mAspectRatio = aspectRatio;
        requestLayout();
    }

    /**
     * 设置圆角
     *
     * @param radius
     */
    public void setCornerRadius(float radius) {
        mCornerRadus = radius;
        invalidate();
    }

    /**
     * 设置左上圆角
     *
     * @param radius
     */
    public void setLeftTopRadius(float radius) {
        leftTopRadius = radius;
        invalidate();
    }

    /**
     * 设置右上圆角
     *
     * @param radius
     */
    public void setRightTopRadius(float radius) {
        rightTopRadius = radius;
        invalidate();
    }

    /**
     * 设置坐下圆角
     *
     * @param radius
     */
    public void setLeftBottomRadius(float radius) {
        leftBottomRadius = radius;
        invalidate();
    }

    /**
     * 设置右下圆角
     *
     * @param radius
     */
    public void setRightBottomRadius(float radius) {
        rightBottomRadius = radius;
        invalidate();
    }

    /**
     * 画图
     *
     * @param canvas
     */
    protected void onDraw(Canvas canvas) {
        float maxLeft = Math.max(leftTopRadius, leftBottomRadius);
        float maxRight = Math.max(rightTopRadius, rightBottomRadius);
        float minWidth = maxLeft + maxRight;
        float maxTop = Math.max(leftTopRadius, rightTopRadius);
        float maxBottom = Math.max(leftBottomRadius, rightBottomRadius);
        float minHeight = maxTop + maxBottom;
        if (mWidth >= minWidth && mHeight > minHeight) {
            Path path = new Path();
            path.moveTo(leftTopRadius, 0);
            path.lineTo(mWidth - rightTopRadius, 0);
            path.quadTo(mWidth, 0, mWidth, rightTopRadius);

            path.lineTo(mWidth, mHeight - rightBottomRadius);
            path.quadTo(mWidth, mHeight, mWidth - rightBottomRadius, mHeight);

            path.lineTo(leftBottomRadius, mHeight);
            path.quadTo(0, mHeight, 0, mHeight - leftBottomRadius);

            path.lineTo(0, leftTopRadius);
            path.quadTo(0, 0, leftTopRadius, 0);

            canvas.clipPath(path);
        }

        super.onDraw(canvas);
    }

    /**
     * dp 转 px
     *
     * @param dpValue
     * @return
     */
    private int dp2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}


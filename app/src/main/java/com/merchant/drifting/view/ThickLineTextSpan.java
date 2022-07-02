package com.merchant.drifting.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.style.ReplacementSpan;
import android.util.Log;

/**
 * Created by yang on 2019/09/18.
 */
public class ThickLineTextSpan extends ReplacementSpan {
    private static final String TAG = ThickLineTextSpan.class.getSimpleName();
    private Paint mTextPaint;
    private int mSize;
    private Context mContext;
    private int textSize;



    public ThickLineTextSpan(Context context) {
        this.mContext = context;
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        textSize = Math.round(16 * context.getResources().getDisplayMetrics().scaledDensity);
        mTextPaint.setTextSize(textSize);
        mTextPaint.setColor(Color.parseColor("#172B4D"));
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTypeface(Typeface.DEFAULT_BOLD);


    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        mSize = (int) mTextPaint.measureText(text, start, end);
        return mSize;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        drawLine(canvas, x, y);
        Log.e(TAG, "文本" + text);
        RectF rect = createRect(x, y, paint);
        drawText(canvas, text, start, end, rect);
    }

    private void drawLine(Canvas canvas, float x, int y) {
        Paint linePaint = new Paint();
        linePaint.setColor(Color.parseColor("#606EEB"));
        linePaint.setStrokeWidth(Math.round(3 * mContext.getResources().getDisplayMetrics().scaledDensity));
        canvas.drawLine(x, y, x + mSize, y, linePaint);
    }

    private RectF createRect(float x, int y, Paint paint) {
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        float strokeWidth = paint.getStrokeWidth();
        float left = x + strokeWidth + 0.5f;
        int top = y + fontMetrics.ascent;
        float right = x + mSize + strokeWidth + 0.5f;
        int bottom = y + fontMetrics.descent;
        return new RectF(left, top, right, bottom);
    }

    private void drawText(Canvas canvas, CharSequence text, int start, int end, RectF rect) {
        Paint.FontMetricsInt tagFontMetrics = mTextPaint.getFontMetricsInt();
        float textCenterX = (rect.right - rect.left) / 2;
        float rectCenterY = rect.bottom - (rect.bottom - rect.top) / 2;
        float tagBaseLineY = rectCenterY + (tagFontMetrics.descent - tagFontMetrics.ascent) / 2f - tagFontMetrics.descent;
        String tag = text.subSequence(start, end).toString();

        canvas.drawText(tag, textCenterX, tagBaseLineY, mTextPaint);
    }
}

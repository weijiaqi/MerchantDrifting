package com.merchant.drifting.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.merchant.drifting.R;

public class FontTextView extends AppCompatTextView {

    private final int PangMen = 1;



    public FontTextView(Context context) {
        super(context);
    }

    public FontTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FontTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //获取参数
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.FontTextView, defStyleAttr, 0);

        int fontType = a.getInt(R.styleable.FontTextView_fontType, 1);

        String fontPath = null;
        switch (fontType) {
            case PangMen:
                fontPath = "fonts/PangMenZhengDao.ttf";
                break;
            default:
        }
        //设置字体
        if (!TextUtils.isEmpty(fontPath)) {
            Typeface typeFace = Typeface.createFromAsset(getContext().getAssets(), fontPath);
            setTypeface(typeFace);

        }
    }
}

package com.merchant.drifting.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

import com.merchant.drifting.R;

public class ScreenUtil {
    public static void setFullScreen(Context context, boolean full) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (full) {
                if (Build.VERSION.SDK_INT >= 21) {
                    View decorView = activity.getWindow().getDecorView();
                    decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                    activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
                } else {
                    activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                }
            } else {
                //隐藏
                if (Build.VERSION.SDK_INT >= 21) {
                    View decorView = activity.getWindow().getDecorView();
                    decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE | View.SYSTEM_UI_FLAG_VISIBLE);
                    activity.getWindow().setStatusBarColor(ContextCompat.getColor(context, R.color.transparent));
                } else {
                    activity.getWindow().setFlags(View.SYSTEM_UI_FLAG_VISIBLE, View.SYSTEM_UI_FLAG_VISIBLE);
                }
            }
        }
    }
}

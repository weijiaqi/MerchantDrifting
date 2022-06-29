package com.merchant.drifting.mvp.ui.listener;

import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.merchant.drifting.R;
import com.merchant.drifting.app.application.MerchantDriftingApplication;
import com.rb.core.tab.view.indicator.Indicator;
import com.rb.core.tab.view.utils.ColorGradient;


/**
 * @Description:
 * @Author: wjq
 * @CreateDate: 2022/2/15 10:41
 */
public class OnTabTransitionTextListener implements Indicator.OnTransitionListener {

    private int unSelectColor = ContextCompat.getColor(MerchantDriftingApplication.getContext(), R.color.color_97);
    private int selectColor = ContextCompat.getColor(MerchantDriftingApplication.getContext(), R.color.color_42);
    private ColorGradient gradient = new ColorGradient(unSelectColor, selectColor, 100);

    @Override
    public void onTransition(View view, int position, float selectPercent) {
        TextView selectTextView = view.findViewById(R.id.tv_tab_title);
        if (gradient != null) {
            selectTextView.setTextColor(gradient.getColor((int) (selectPercent * 100)));
        }
    }
}

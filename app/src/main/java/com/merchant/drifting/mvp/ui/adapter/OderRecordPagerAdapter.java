package com.merchant.drifting.mvp.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;

import com.hjq.shape.view.ShapeTextView;
import com.merchant.drifting.R;
import com.merchant.drifting.app.application.MerchantDriftingApplication;
import com.merchant.drifting.mvp.ui.fragment.OrderRecordFragment;
import com.rb.core.tab.view.indicator.IndicatorViewPager;



public class OderRecordPagerAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {
    private String[] weeks = {"今日", "近7日", "近30日"};

    private Context mContext;
    public OderRecordPagerAdapter(FragmentManager fm,Context context) {
        super(fm);
        this.mContext=context;
    }

    @Override
    public int getCount() {
        return weeks.length;
    }

    @Override
    public Fragment getFragmentForPage(int position) {
        return OrderRecordFragment.newInstance(position);
    }

    @Override
    public PagerAdapter getPagerAdapter() {
        return super.getPagerAdapter();
    }

    @SuppressLint("RtlHardcoded")
    @Override
    public View getViewForTab(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = LayoutInflater.from(MerchantDriftingApplication.getContext()).inflate(R.layout.layout_record_view, container, false);
        }
        ShapeTextView tv_tab_top_title = (ShapeTextView) convertView;
        tv_tab_top_title.setText(weeks[position]);

        if (position == 0) {
            tv_tab_top_title.getShapeDrawableBuilder().setSolidColor(mContext.getColor(R.color.color_42)).intoBackground();
        } else {
            tv_tab_top_title.getShapeDrawableBuilder().setSolidColor(mContext.getColor(R.color.color_d4)).intoBackground();
        }
        return convertView;
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }


}


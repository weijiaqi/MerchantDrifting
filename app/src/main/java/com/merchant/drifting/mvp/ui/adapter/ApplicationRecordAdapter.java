package com.merchant.drifting.mvp.ui.adapter;

import android.graphics.Paint;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;

import com.jess.arms.utils.ArmsUtils;
import com.luck.picture.lib.utils.DensityUtil;
import com.merchant.drifting.R;
import com.merchant.drifting.app.application.MerchantDriftingApplication;
import com.merchant.drifting.data.entity.ApplicationRecordEntity;
import com.merchant.drifting.mvp.ui.fragment.RecordFragment;
import com.rb.core.tab.view.indicator.IndicatorViewPager;

import java.util.ArrayList;
import java.util.List;

public class ApplicationRecordAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

    private List<ApplicationRecordEntity> mChannelDatas = new ArrayList<>();//频道标题信息


    public ApplicationRecordAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return mChannelDatas.size();
    }

    @Override
    public View getViewForTab(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = LayoutInflater.from(MerchantDriftingApplication.getContext()).inflate(R.layout.layout_tab_top, container, false);
        }
        TextView tv_tab_top_title = (TextView) convertView;
        tv_tab_top_title.setText(mChannelDatas.get(position).getTitle());
        int witdh = getTextWidth(tv_tab_top_title);
        int padding = ArmsUtils.dip2px(MerchantDriftingApplication.getContext(), 10);
        //因为wrap的布局 字体大小变化会导致textView大小变化产生抖动，这里通过设置textView宽度就避免抖动现象
        tv_tab_top_title.setWidth((int) (witdh * 1.06f) + padding);
        return convertView;
    }

    @Override
    public Fragment getFragmentForPage(int position) {
        return RecordFragment.newInstance(mChannelDatas.get(position).getType());
    }

    @Override
    public PagerAdapter getPagerAdapter() {
        return super.getPagerAdapter();
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    private int getTextWidth(TextView textView) {
        if (textView == null) {
            return 0;
        }
        Rect bounds = new Rect();
        String text = textView.getText().toString();
        Paint paint = textView.getPaint();
        paint.getTextBounds(text, 0, text.length(), bounds);
        int width = bounds.left + bounds.width();
        return width;
    }

    public void setData(List<ApplicationRecordEntity> mTabTitle) {
        this.mChannelDatas = mTabTitle;
        notifyDataSetChanged();
    }
}

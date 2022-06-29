package com.merchant.drifting.mvp.ui.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;

import com.merchant.drifting.R;
import com.rb.core.tab.view.indicator.IndicatorViewPager;

import java.util.List;

public class HomeTabAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter{
    private Context mContext;
    private String[] titleList;
    private int[] iconList;
    private List<Fragment> fragmentList;
    private SparseArray<TextView> msgViewMap;
    private SparseArray<TextView> titleViewMap;

    public HomeTabAdapter(Context context, FragmentManager fragmentManager, String[] titleList, int[] iconList, List<Fragment> fragmentList) {
        super(fragmentManager);
        this.mContext = context;
        this.titleList = titleList;
        this.iconList = iconList;
        this.fragmentList = fragmentList;
        msgViewMap = new SparseArray<>();
        titleViewMap = new SparseArray<>();
    }

    @Override
    public int getCount() {
        return titleList.length;
    }

    @Override
    public View getViewForTab(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_home_tab_bottom, container, false);
        }
        ImageView iv_tab_icon = convertView.findViewById(R.id.iv_tab_icon);
        TextView tv_tab_title = convertView.findViewById(R.id.tv_tab_title);
        TextView tv_num = convertView.findViewById(R.id.tv_num);
        if (msgViewMap != null) {
            msgViewMap.put(position, tv_num);
        }
        if (titleViewMap != null) {
            titleViewMap.put(position, tv_tab_title);
        }
        iv_tab_icon.setImageResource(iconList[position]);
        tv_tab_title.setText(titleList[position]);

        return convertView;
    }

    @Override
    public Fragment getFragmentForPage(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    public TextView getMsgView(int position) {
        if (msgViewMap == null) {
            return null;
        }
        return msgViewMap.get(position);
    }

    public TextView getTitleView(int position) {
        if (titleViewMap == null) {
            return null;
        }
        return titleViewMap.get(position);
    }
}

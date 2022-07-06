package com.merchant.drifting.mvp.ui.activity.index;

import android.annotation.SuppressLint;
import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.hjq.shape.view.ShapeTextView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.merchant.drifting.R;
import com.merchant.drifting.di.component.DaggerOrderDataComponent;
import com.merchant.drifting.mvp.contract.OrderDataContract;
import com.merchant.drifting.mvp.model.entity.OrderDataEntity;
import com.merchant.drifting.mvp.model.entity.ShopStaticOrderEntity;
import com.merchant.drifting.mvp.presenter.OrderDataPresenter;
import com.merchant.drifting.mvp.ui.adapter.OrderDataAdapter;
import com.merchant.drifting.storageinfo.Preferences;
import com.merchant.drifting.util.ClickUtil;
import com.merchant.drifting.util.SpannableUtil;
import com.merchant.drifting.util.StringUtil;
import com.merchant.drifting.view.MyMarkerView;
import com.merchant.drifting.view.ThickLineTextSpan;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/06/27 14:08
 *
 * @author 订单数据
 * module name is OrderDataActivity
 */
public class OrderDataActivity extends BaseActivity<OrderDataPresenter> implements OrderDataContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.tv_order_num)
    TextView mTvOrderNum;
    @BindView(R.id.tv_order_trend)
    TextView mTvOrderTrend;
    @BindView(R.id.tv_order_rank)
    TextView mTvOrderRank;
    @BindView(R.id.rcy_rank)
    RecyclerView mRcyRank;
    @BindView(R.id.tv_bar)
    TextView mTvBar;
    @BindView(R.id.tv_today_order_num)
    TextView mTvTodayNum;
    @BindView(R.id.tv_week_order_num)
    TextView mTvWeekNum;
    @BindView(R.id.lineChart1)
    LineChart chart;
    @BindView(R.id.tv_today_up_down)
    ShapeTextView mTvTodayUpDown;
    @BindView(R.id.tv_week_up_down)
    ShapeTextView mTvWeekUpDown;
    @BindView(R.id.tv_turnover)
    TextView mTvtrunover;
    @BindView(R.id.tv_week)
    TextView mTvWeek;
    private SpannableStringBuilder passer;
    private OrderDataAdapter orderDataAdapter;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, OrderDataActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerOrderDataComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_order_data; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        setStatusBarHeight(mTvBar);
        mToolbarTitle.setText("订单数据");

        initListener();
    }

    @SuppressLint("ResourceType")
    public void initListener() {
        initTextSpan(mTvOrderTrend, "订单实时走势 ");
        initTextSpan(mTvOrderRank, "茶饮销售排行 ");
        mRcyRank.setHasFixedSize(true);
        mRcyRank.setLayoutManager(new LinearLayoutManager(this));
        orderDataAdapter = new OrderDataAdapter(new ArrayList<>());
        mRcyRank.setAdapter(orderDataAdapter);

        if (mPresenter != null) {
            mPresenter.statisticorder(Preferences.getShopId());
        }


    }


    @SuppressLint("ResourceType")
    @Override
    public void OnOrderDataSuccess(ShopStaticOrderEntity entity) {
        if (entity != null) {
            passer = SpannableUtil.getBuilder(this, StringUtil.frontCommaValue(entity.getToday_total())).append("单").setTextSize(17).build();
            mTvOrderNum.setText(passer);
            mTvTodayNum.setText(StringUtil.frontCommaValue(entity.getToday_total()));
            mTvWeekNum.setText(StringUtil.frontCommaValue(entity.getThis_week_total()));
            mTvtrunover.setText("今日营业额：¥" + StringUtil.frontCommaValue(entity.getTurnover()));
            mTvWeek.setText("本周营业额：¥" + StringUtil.frontCommaValue(entity.getThis_week_turnover()));
            if (entity.getToday_ratio() >= 0) {
                mTvTodayUpDown.getShapeDrawableBuilder().setSolidColor(getColor(R.color.color_c2_19)).intoBackground();
                mTvTodayUpDown.getTextColorBuilder().setTextColor(getColor(R.color.color_c2)).intoTextColor();
                mTvTodayUpDown.setText("+" + entity.getToday_ratio() * 100 + "%");
            } else {
                mTvTodayUpDown.getShapeDrawableBuilder().setSolidColor(getColor(R.color.color_42_19)).intoBackground();
                mTvTodayUpDown.getTextColorBuilder().setTextColor(getColor(R.color.color_42c)).intoTextColor();
                mTvTodayUpDown.setText(entity.getToday_ratio() * 100 + "%");
            }
            if (entity.getThis_week_ratio() >=0) {
                mTvWeekUpDown.getShapeDrawableBuilder().setSolidColor(getColor(R.color.color_c2_19)).intoBackground();
                mTvWeekUpDown.getTextColorBuilder().setTextColor(getColor(R.color.color_c2)).intoTextColor();
                mTvWeekUpDown.setText("+" + entity.getThis_week_ratio() * 100 + "%");
            } else {
                mTvWeekUpDown.getShapeDrawableBuilder().setSolidColor(getColor(R.color.color_42_19)).intoBackground();
                mTvWeekUpDown.getTextColorBuilder().setTextColor(getColor(R.color.color_42c)).intoTextColor();
                mTvWeekUpDown.setText(entity.getThis_week_ratio() * 100 + "%");
            }

            List<ShopStaticOrderEntity.TrendingBean> list = entity.getTrending();

            initChart(chart, list);

            List<ShopStaticOrderEntity.RankingBean> rankingBeanList = entity.getRanking();
            orderDataAdapter.setData(rankingBeanList);
        }
    }


    private XAxis xAxis;                //X轴
    private YAxis leftYAxis;            //左侧Y轴
    private YAxis rightYaxis;           //右侧Y轴


    public void initChart(LineChart lineChart, List<ShopStaticOrderEntity.TrendingBean> trendingBeanList) {
        /***图表设置***/

        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

            }

            @Override
            public void onNothingSelected() {

            }
        });
        //是否展示网格线
        lineChart.setDrawGridBackground(false);
        lineChart.getDescription().setEnabled(false);
        //是否显示边界
        lineChart.setDrawBorders(false);
        //设置是否可以缩放
        lineChart.setScaleEnabled(true);
        lineChart.setBackgroundColor(Color.WHITE);
        //是否可以拖动
        lineChart.setDragEnabled(true);
        //是否有触摸事件
        lineChart.setTouchEnabled(true);


        /***XY轴的设置***/
        xAxis = lineChart.getXAxis();
        leftYAxis = lineChart.getAxisLeft();
        rightYaxis = lineChart.getAxisRight();
        //去掉右侧Y轴
        rightYaxis.setEnabled(false);


        //X轴设置显示位置在底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);  //显示X轴线
        xAxis.setGranularity(1f);
        xAxis.setAxisMinimum(00);
        xAxis.setAxisMaximum(trendingBeanList.size() - 1);
        xAxis.setLabelCount(10, false);
        leftYAxis.setAxisMinimum(0);
        leftYAxis.setGranularity(1f);
        leftYAxis.setDrawGridLines(false);//显示Y轴线
        leftYAxis.setDrawAxisLine(false);
        /***折线图例 标签 设置***/

        Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Bold.ttf");


        Legend l = lineChart.getLegend();
        l.setTypeface(tf);
        l.setTextColor(getColor(R.color.color_17));
        l.setForm(Legend.LegendForm.LINE);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);

        l.setDrawInside(false);

        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
        mv.setChartView(lineChart); // For bounds control
        lineChart.setMarker(mv); // Set the marker to the chart


        setLineChartData(lineChart,trendingBeanList);

    }


    public void setLineChartData(LineChart lineChart, List<ShopStaticOrderEntity.TrendingBean> dataList) {
        List<Entry> valsComp1  = new ArrayList<>();
        List<Entry> valsComp2 = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            float data = Float.parseFloat(dataList.get(i).getSales_volume());
            /**
             * 在此可查看 Entry构造方法，可发现 可传入数值 Entry(float x, float y)
             * 也可传入Drawable， Entry(float x, float y, Drawable icon) 可在XY轴交点 设置Drawable图像展示
             */
            Entry entry = new Entry(i, data);
            valsComp1.add(entry);
        }

        for (int i = 0; i < dataList.size(); i++) {
            float data = Float.parseFloat(dataList.get(i).getYesterday_sales_volume());
            /**
             * 在此可查看 Entry构造方法，可发现 可传入数值 Entry(float x, float y)
             * 也可传入Drawable， Entry(float x, float y, Drawable icon) 可在XY轴交点 设置Drawable图像展示
             */
            Entry entry = new Entry(i, data);
            valsComp2.add(entry);
        }


        LineDataSet setComp1 = new LineDataSet(valsComp1, "今日订单");
        setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);
        setComp1.setColor(getResources().getColor(R.color.color_f9));
        setComp1.setDrawCircles(false);
        setComp1.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        LineDataSet setComp2 = new LineDataSet(valsComp2, "昨日订单");
        setComp2.setAxisDependency(YAxis.AxisDependency.LEFT);
        setComp2.setDrawCircles(true);
        setComp2.setColor(getResources().getColor(R.color.color_42));
        setComp2.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);

        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(setComp1);
        dataSets.add(setComp2);
        LineData lineData = new LineData(dataSets);
        lineChart.setData(lineData);
        lineChart.invalidate();

    }



    /**
     * 曲线初始化设置 一个LineDataSet 代表一条曲线
     *
     * @param lineDataSet 线条
     * @param color       线条颜色
     * @param mode
     */
    private void initLineDataSet(LineDataSet lineDataSet, int color, LineDataSet.Mode mode) {
        lineDataSet.setColor(color);
        lineDataSet.setCircleColor(color);
        lineDataSet.setLineWidth(1f);
        lineDataSet.setCircleRadius(3f);
        //设置曲线值的圆点是实心还是空心
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setValueTextSize(10f);
        //设置折线图填充
        lineDataSet.setDrawFilled(false);
        lineDataSet.setFormLineWidth(1f);
        lineDataSet.setFormSize(15.f);
        lineDataSet.setMode(mode);
        lineDataSet.setCubicIntensity(0.2f);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawValues(false);
    }


    @Override
    public void onNetError() {

    }

    public Activity getActivity() {
        return this;
    }

    @OnClick({R.id.toolbar_back})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
            }
        }
    }


    private void initTextSpan(TextView textView, String textContent) {
        SpannableStringBuilder mSpannableStringBuilder = new SpannableStringBuilder(textContent);
        ThickLineTextSpan mThickLineTextSpan = new ThickLineTextSpan(this);
        mSpannableStringBuilder.setSpan(mThickLineTextSpan, 0, textContent.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(mSpannableStringBuilder);
    }

    @Override
    public void showMessage(@NonNull String message) {

    }
}
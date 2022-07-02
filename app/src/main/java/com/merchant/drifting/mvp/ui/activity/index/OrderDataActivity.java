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
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.merchant.drifting.R;
import com.merchant.drifting.di.component.DaggerOrderDataComponent;
import com.merchant.drifting.mvp.contract.OrderDataContract;
import com.merchant.drifting.mvp.model.entity.OrderDataEntity;
import com.merchant.drifting.mvp.presenter.OrderDataPresenter;
import com.merchant.drifting.mvp.ui.adapter.OrderDataAdapter;
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
        passer = SpannableUtil.getBuilder(this, StringUtil.frontCommaValue(8076)).append("单").setTextSize(17).build();
        mTvOrderNum.setText(passer);
        mTvTodayNum.setText(StringUtil.frontCommaValue(8078));
        mTvWeekNum.setText(StringUtil.frontCommaValue(18262));


        initChart(chart);


        mRcyRank.setHasFixedSize(true);
        mRcyRank.setLayoutManager(new LinearLayoutManager(this));
        orderDataAdapter = new OrderDataAdapter(new ArrayList<>());
        mRcyRank.setAdapter(orderDataAdapter);
        orderDataAdapter.setData(getData());
    }


    private XAxis xAxis;                //X轴
    private YAxis leftYAxis;            //左侧Y轴
    private YAxis rightYaxis;           //右侧Y轴
    private Legend legend;              //图例


    public void initChart(LineChart lineChart) {
        /***图表设置***/
        //是否展示网格线
        lineChart.setDrawGridBackground(false);
        //设置是否可以缩放
        lineChart.setScaleEnabled(false);

        Description description = new Description();
//        description.setText("需要展示的内容");
        description.setEnabled(false);
        lineChart.setDescription(description);

        lineChart.setBackgroundColor(Color.WHITE);
        //是否显示边界
        lineChart.setDrawBorders(false);
        //是否可以拖动
        lineChart.setDragEnabled(false);
        //是否有触摸事件
        lineChart.setTouchEnabled(true);
        //设置XY轴动画效果
        lineChart.animateY(2500);
        lineChart.animateX(1500);


        /***XY轴的设置***/
        xAxis = lineChart.getXAxis();
        leftYAxis = lineChart.getAxisLeft();
        rightYaxis = lineChart.getAxisRight();
        //去掉右侧Y轴
        rightYaxis.setEnabled(false);


        //X轴设置显示位置在底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        List<String> dataList = new ArrayList<>();
        dataList.add("10");
        dataList.add("12");
        dataList.add("14");
        dataList.add("16");
        dataList.add("18");
        dataList.add("20");
        dataList.add("22");
        dataList.add("24");

        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String tradeDate = dataList.get((int) value % dataList.size()).toString();
                return tradeDate;
            }
        });


        xAxis.setLabelCount(8, true);

        leftYAxis.setAxisMinimum(0);
        leftYAxis.setAxisMaximum(100);
        leftYAxis.setLabelCount(6, true);

        /***折线图例 标签 设置***/

        Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Bold.ttf");

        legend = lineChart.getLegend();
        //设置显示类型，LINE CIRCLE SQUARE EMPTY 等等 多种方式，查看LegendForm 即可
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(12f);
        legend.setTextColor(getColor(R.color.color_17));
        legend.setTypeface(tf);
        //显示位置 左下方
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //是否绘制在图表里面
        legend.setDrawInside(false);

        List<String> list = new ArrayList<>();
        list.add("20");
        list.add("20");
        list.add("30");
        list.add("40");
        list.add("50");
        list.add("60");
        list.add("70");
        list.add("80");
        List<String> list2 = new ArrayList<>();
        list2.add("40");
        list2.add("20");
        list2.add("20");
        list2.add("60");
        list2.add("30");
        list2.add("50");
        list2.add("30");
        list2.add("50");
        showLineChart(list, "今日订单", getColor(R.color.color_f9));
        addLine(list2, "昨日订单", getColor(R.color.color_42));
        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
        mv.setChartView(lineChart); // For bounds control
        lineChart.setMarker(mv); // Set the marker to the chart
    }


    /**
     * 展示曲线
     *
     * @param dataList 数据集合
     * @param name     曲线名称
     * @param color    曲线颜色
     */
    public void showLineChart(List<String> dataList, String name, int color) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            float data = Float.parseFloat(dataList.get(i));
            /**
             * 在此可查看 Entry构造方法，可发现 可传入数值 Entry(float x, float y)
             * 也可传入Drawable， Entry(float x, float y, Drawable icon) 可在XY轴交点 设置Drawable图像展示
             */
            Entry entry = new Entry(i, data);
            entries.add(entry);
        }
        // 每一个LineDataSet代表一条线
        LineDataSet lineDataSet = new LineDataSet(entries, name);
        initLineDataSet(lineDataSet, color, LineDataSet.Mode.LINEAR);
        LineData lineData = new LineData(lineDataSet);
        chart.setData(lineData);

    }

    /**
     * 添加曲线
     */
    private void addLine(List<String> dataList, String name, int color) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            float data = Float.parseFloat(dataList.get(i));
            /**
             * 在此可查看 Entry构造方法，可发现 可传入数值 Entry(float x, float y)
             * 也可传入Drawable， Entry(float x, float y, Drawable icon) 可在XY轴交点 设置Drawable图像展示
             */
            Entry entry = new Entry(i, (float) data);
            entries.add(entry);
        }
        // 每一个LineDataSet代表一条线
        LineDataSet lineDataSet = new LineDataSet(entries, name);
        initLineDataSet(lineDataSet, color, LineDataSet.Mode.LINEAR);
        chart.getLineData().addDataSet(lineDataSet);
        chart.invalidate();
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


    public List<OrderDataEntity> getData() {
        List<OrderDataEntity> list = new ArrayList<>();
        list.add(new OrderDataEntity("1"));
        list.add(new OrderDataEntity("2"));
        list.add(new OrderDataEntity("3"));
        list.add(new OrderDataEntity("4"));
        list.add(new OrderDataEntity("5"));
        return list;
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
package com.merchant.drifting.mvp.ui.fragment;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.merchant.drifting.R;
import com.merchant.drifting.data.entity.TransactionEntity;
import com.merchant.drifting.di.component.DaggerIndexComponent;
import com.merchant.drifting.mvp.contract.IndexContract;
import com.merchant.drifting.mvp.model.entity.OrderRecordEntity;
import com.merchant.drifting.mvp.presenter.IndexPresenter;
import com.merchant.drifting.mvp.ui.activity.index.SwitchMerchantsActivity;
import com.merchant.drifting.mvp.ui.activity.merchant.NewsActivity;
import com.merchant.drifting.mvp.ui.activity.user.OpenShopActivity;
import com.merchant.drifting.mvp.ui.adapter.OrderRecordAdapter;
import com.merchant.drifting.mvp.ui.adapter.TransactionAdapter;
import com.merchant.drifting.util.ClickUtil;
import com.merchant.drifting.util.StringUtil;
import com.merchant.drifting.view.ThickLineTextSpan;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/06/24 12:03
 *
 * @author 首页
 * module name is IndexFragment
 */
public class IndexFragment extends BaseFragment<IndexPresenter> implements IndexContract.View {
    @BindView(R.id.tv_bar)
    TextView mTvBar;
    @BindView(R.id.rcy_transaction)
    RecyclerView mRcyTransaction;
    @BindView(R.id.rcy_order_record)
    RecyclerView mRcyOrderRecord;
    @BindView(R.id.tv_transaction)
    TextView mTvTransaction;
    @BindView(R.id.tv_order_record)
    TextView mTvOrderRecord;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    private TransactionAdapter transactionAdapter;
    private OrderRecordAdapter orderRecordAdapter;

    public static IndexFragment newInstance() {
        IndexFragment fragment = new IndexFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerIndexComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_index, container, false);
    }

    /**
     * 在 onActivityCreate()时调用
     */
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        setStatusBarHeight(mTvBar);
        initListener();

    }

    @Override
    public void setData(@Nullable Object data) {

    }

    private void initListener() {
        mTvPrice.setText("¥ "+StringUtil.frontCDecimalValue(205324));

        initTextSpan(mTvTransaction, "交易功能 ");
        initTextSpan(mTvOrderRecord, "订单记录 ");

        mRcyTransaction.setLayoutManager(new GridLayoutManager(mContext, 4));
        mRcyOrderRecord.setLayoutManager(new LinearLayoutManager(mContext));


        transactionAdapter = new TransactionAdapter(new ArrayList<>());
        mRcyTransaction.setAdapter(transactionAdapter);
        transactionAdapter.setData(getData());

        orderRecordAdapter = new OrderRecordAdapter(new ArrayList<>());
        mRcyOrderRecord.setAdapter(orderRecordAdapter);
        orderRecordAdapter.setData(getData2());

    }


    public List<TransactionEntity> getData() {
        List<TransactionEntity> list = new ArrayList<>();
        list.add(new TransactionEntity(R.drawable.balance, "账户余额"));
        list.add(new TransactionEntity(R.drawable.shop, "商品管理"));
        list.add(new TransactionEntity(R.drawable.order_data, "订单数据"));
        list.add(new TransactionEntity(R.drawable.shop_manager, "店铺管理"));
        return list;
    }


    public List<OrderRecordEntity> getData2() {
        List<OrderRecordEntity> list = new ArrayList<>();
        list.add(new OrderRecordEntity("1"));
        list.add(new OrderRecordEntity("2"));
        list.add(new OrderRecordEntity("3"));
        list.add(new OrderRecordEntity("4"));
        return list;
    }


    private void initTextSpan(TextView textView, String textContent) {
        SpannableStringBuilder mSpannableStringBuilder = new SpannableStringBuilder(textContent);
        ThickLineTextSpan mThickLineTextSpan = new ThickLineTextSpan(mContext);
        mSpannableStringBuilder.setSpan(mThickLineTextSpan, 0, textContent.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(mSpannableStringBuilder);
    }


    public Fragment getFragment() {
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @OnClick({R.id.iv_message,R.id.tv_switch_merchants})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.iv_message:
                    NewsActivity.start(mContext, false);
                    break;
                case R.id.tv_switch_merchants:  //切换商家
                    SwitchMerchantsActivity.start(mContext,false);
                    break;
            }
        }
    }


}
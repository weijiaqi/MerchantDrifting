package com.merchant.drifting.mvp.ui.fragment;

import static android.app.Activity.RESULT_OK;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.activity.CaptureActivity;
import com.hjq.shape.view.ShapeTextView;
import com.jess.arms.base.BaseDialog;
import com.jess.arms.base.BaseEntity;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.merchant.drifting.R;
import com.merchant.drifting.app.MDConstant;
import com.merchant.drifting.data.entity.TransactionEntity;
import com.merchant.drifting.data.event.GoodsOnOffEvent;
import com.merchant.drifting.data.event.LogInEvent;
import com.merchant.drifting.data.event.MessageReadEvent;
import com.merchant.drifting.data.event.RankingEvent;
import com.merchant.drifting.di.component.DaggerIndexComponent;
import com.merchant.drifting.mvp.contract.IndexContract;
import com.merchant.drifting.mvp.model.entity.MessageUnreadEntity;
import com.merchant.drifting.mvp.model.entity.TodayOrderEntity;
import com.merchant.drifting.mvp.model.entity.WriteOffEntity;
import com.merchant.drifting.mvp.presenter.IndexPresenter;
import com.merchant.drifting.mvp.ui.activity.index.SwitchMerchantsActivity;
import com.merchant.drifting.mvp.ui.activity.merchant.NewsActivity;
import com.merchant.drifting.mvp.ui.adapter.OderRecordPagerAdapter;
import com.merchant.drifting.mvp.ui.adapter.TransactionAdapter;
import com.merchant.drifting.mvp.ui.dialog.VerificationDialog;
import com.merchant.drifting.storageinfo.Preferences;
import com.merchant.drifting.util.ClickUtil;
import com.merchant.drifting.util.StringUtil;
import com.merchant.drifting.util.ToastUtil;
import com.merchant.drifting.util.callback.BaseDataCallBack;
import com.merchant.drifting.util.request.RequestUtil;
import com.merchant.drifting.view.ThickLineTextSpan;
import com.rb.core.tab.view.indicator.IndicatorViewPager;
import com.rb.core.tab.view.indicator.ScrollIndicatorView;
import com.rb.core.tab.view.indicator.transition.OnTransitionTextListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/06/24 12:03
 *
 * @author ??????
 * module name is IndexFragment
 */
public class IndexFragment extends BaseFragment<IndexPresenter> implements IndexContract.View {
    @BindView(R.id.tv_bar)
    TextView mTvBar;
    @BindView(R.id.iv_message)
    ImageView mIvMessage;
    @BindView(R.id.rcy_transaction)
    RecyclerView mRcyTransaction;
    @BindView(R.id.tv_transaction)
    TextView mTvTransaction;
    @BindView(R.id.tv_order_record)
    TextView mTvOrderRecord;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_today_order_num)
    TextView mTvTodayOrderNum;
    @BindView(R.id.tv_turnover)
    TextView mTvTrunOver;
    @BindView(R.id.tv_switch_merchants)
    TextView mTvSwitch;

    @BindView(R.id.indicator_tablayout)
    ScrollIndicatorView mIndicatorTablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    private VerificationDialog verificationDialog;
    private TransactionAdapter transactionAdapter;
    private IndicatorViewPager mIndicatorViewPager;
    private OderRecordPagerAdapter oderRecordPagerAdapter;

    public static IndexFragment newInstance() {
        IndexFragment fragment = new IndexFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerIndexComponent //??????????????????,?????????????????????
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
     * ??? onActivityCreate()?????????
     */
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(false);
        setStatusBarHeight(mTvBar);
        mTvSwitch.setText(Preferences.getShopName());
        initListener();
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    private void initListener() {
        RequestUtil.create().messageunread(entity -> {
            if (entity.getCode() == 200) {
                if (entity.getData().getSys_msg() == 0 && entity.getData().getOrder_msg() == 0) {
                    mIvMessage.setImageResource(R.drawable.unread);
                } else {
                    mIvMessage.setImageResource(R.drawable.unread_message);
                }
            }
        });
        initTextSpan(mTvTransaction, "???????????? ");
        initTextSpan(mTvOrderRecord, "???????????? ");
        mRcyTransaction.setLayoutManager(new GridLayoutManager(mContext, 4));
        transactionAdapter = new TransactionAdapter(new ArrayList<>());
        mRcyTransaction.setAdapter(transactionAdapter);
        transactionAdapter.setData(getData());
        oderRecordPagerAdapter = new OderRecordPagerAdapter(getChildFragmentManager(), mContext);
        mIndicatorTablayout.setOnTransitionListener(new OnTransitionTextListener().setValueFromRes(getActivity(),
                R.color.white, R.color.white, R.dimen.tab_message_nor_size, R.dimen.tab_message_nor_size));
        mIndicatorViewPager = new IndicatorViewPager(mIndicatorTablayout, viewpager);
        mIndicatorViewPager.setAdapter(oderRecordPagerAdapter);
        mIndicatorViewPager.setOnIndicatorPageChangeListener(new IndicatorViewPager.OnIndicatorPageChangeListener() {
            @Override
            public void onIndicatorPageChange(int preItem, int currentItem) {
                setTextStytle(currentItem, true);
                setTextStytle(preItem, false);
            }
        });
        getTotal();
    }

    public void getTotal() {
        if (mPresenter != null) {
            mPresenter.statistictoday(Preferences.getShopId());
        }
    }


    public List<TransactionEntity> getData() {
        List<TransactionEntity> list = new ArrayList<>();
        list.add(new TransactionEntity(R.drawable.balance, "????????????"));
        list.add(new TransactionEntity(R.drawable.shop, "????????????"));
        list.add(new TransactionEntity(R.drawable.order_data, "????????????"));
        list.add(new TransactionEntity(R.drawable.shop_manager, "????????????"));
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


    @OnClick({R.id.iv_message, R.id.tv_switch_merchants, R.id.iv_scan})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.iv_message:
                    NewsActivity.start(mContext, false);
                    break;
                case R.id.tv_switch_merchants:  //????????????
                    SwitchMerchantsActivity.start(mContext, 2, false);
                    break;
                case R.id.iv_scan:  //?????????
                    if (mPresenter != null) {
                        mPresenter.startQrCode(mContext);
                    }
                    break;

            }
        }
    }


    /**
     * ??????????????????
     *
     * @param position
     */
    private void setTextStytle(int position, boolean selected) {
        View view = mIndicatorTablayout.getItemView(position);
        ShapeTextView mTvTitle = (ShapeTextView) view;
        if (selected) {
            mTvTitle.getShapeDrawableBuilder().setSolidColor(mContext.getColor(R.color.color_42)).intoBackground();
        } else {
            mTvTitle.getShapeDrawableBuilder().setSolidColor(mContext.getColor(R.color.color_d4)).intoBackground();
        }
    }


    @Override
    public void OnTodayOrderSuccess(TodayOrderEntity entity) {
        if (entity != null) {
            mTvPrice.setText("?? " + StringUtil.frontCDecimalValue(entity.getBalance()));
            mTvTodayOrderNum.setText("??????????????????" + entity.getOrder_num() + "???");
            mTvTrunOver.setText("??????????????????" + StringUtil.frontCDecimalValue(entity.getTurnover()) + "???");
        }
    }


    @Override
    public void PermissionVoiceSuccess() {
        // ???????????????
        Intent intent = new Intent(mContext, CaptureActivity.class);
        startActivityForResult(intent, MDConstant.REQ_QR_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MDConstant.REQ_QR_CODE && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String token = bundle.getString(MDConstant.INTENT_EXTRA_KEY_QR_SCAN);
            if (mPresenter != null) {
                mPresenter.shopwriteOff(token, Preferences.getShopId());
            }
        }
    }

    @Override
    public void OnShopWriteOff(WriteOffEntity entity) {
        if (entity != null) {
            verificationDialog = new VerificationDialog(mContext, entity);
            verificationDialog.setCancelable(false);
            verificationDialog.show();
            verificationDialog.setOnClickCallback(type -> {
                if (type == VerificationDialog.SELECT_FINISH) {
                    getTotal();
                    EventBus.getDefault().post(new RankingEvent());
                }
            });
        }
    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtil.showToast(message);
    }


    @Override
    public void onNetError() {

    }

    /**
     * ??????Event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void MessageReadEvent(MessageReadEvent editEvent) {
        if (editEvent != null) {
            mIvMessage.setImageResource(R.drawable.unread);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void LoginEvent(LogInEvent logInEvent) {
        if (logInEvent != null) {
            initListener();
        }
    }
}
package com.merchant.drifting.mvp.ui.activity.index;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import com.merchant.drifting.R;
import com.merchant.drifting.di.component.DaggerOrderDetailComponent;
import com.merchant.drifting.mvp.contract.OrderDetailContract;
import com.merchant.drifting.mvp.model.entity.WriteOffDetailEntity;
import com.merchant.drifting.mvp.presenter.OrderDetailPresenter;
import com.merchant.drifting.storageinfo.Preferences;
import com.merchant.drifting.util.ClickUtil;
import com.merchant.drifting.util.GlideUtil;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/06/28 17:29
 *
 * @author 订单详情
 * module name is OrderDetailActivity
 */
public class OrderDetailActivity extends BaseActivity<OrderDetailPresenter> implements OrderDetailContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.iv_pic)
    ImageView mIvPic;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_desc)
    TextView mTvDesc;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_status)
    TextView mTvStatus;
    @BindView(R.id.tv_nick_name)
    TextView mTvNickName;
    @BindView(R.id.tv_online_payment)
    TextView mTvOnlinePayment;
    @BindView(R.id.tv_order_no)
    TextView mTvOrderNo;

    @BindView(R.id.tv_store_no)
    TextView mTvStoreNo;
    private static final String EXRA_WRITE_OFF_ID = "exra_write_off_id";

    private int exra_write_off_id;

    public static void start(Context context, int write_off_id, boolean closePage) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra(EXRA_WRITE_OFF_ID, write_off_id);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerOrderDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_order_detail; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(false);
        mToolbarTitle.setText("订单详情");
        if (getIntent() != null) {
            exra_write_off_id = getIntent().getIntExtra(EXRA_WRITE_OFF_ID, 0);
        }
        initListener();
    }

    public void initListener() {
        if (mPresenter != null) {
            mPresenter.writeOffDetail(exra_write_off_id, Preferences.getShopId());
        }
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

    @Override
    public void OnWriteOffDetailSuccess(WriteOffDetailEntity entity) {
        if (entity != null) {
            GlideUtil.create().loadNormalPic(this, entity.getSmall_image(), mIvPic);
            mTvTitle.setText(entity.getSku_name());
            mTvDesc.setText(entity.getIntro());
            mTvPrice.setText("¥" + entity.getMoney());
            mTvStoreNo.setText(entity.getOrder_sub_sn());
            mTvOrderNo.setText(entity.getOrder_sub_sn());
            mTvNickName.setText(entity.getUser_name());
            mTvOnlinePayment.setText("¥" + entity.getMoney());
            if (entity.getStatus() == 0) {
                mTvStatus.setText("未核销");
            } else {
                mTvStatus.setText("已核销");
            }
        }
    }

    @Override
    public void onNetError() {

    }

    public Activity getActivity() {
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {

    }
}
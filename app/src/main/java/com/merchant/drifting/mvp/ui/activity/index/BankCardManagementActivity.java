package com.merchant.drifting.mvp.ui.activity.index;


import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import com.merchant.drifting.R;
import com.merchant.drifting.data.event.BankCardEvent;
import com.merchant.drifting.data.event.GoodsOnOffEvent;
import com.merchant.drifting.di.component.DaggerBankCardManagementComponent;
import com.merchant.drifting.mvp.contract.BankCardManagementContract;
import com.merchant.drifting.mvp.model.entity.BankListEntity;
import com.merchant.drifting.mvp.presenter.BankCardManagementPresenter;
import com.merchant.drifting.storageinfo.Preferences;
import com.merchant.drifting.util.ClickUtil;
import com.merchant.drifting.util.ToastUtil;
import com.merchant.drifting.util.VerifyUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/06/29 11:47
 *
 * @author 银行卡管理
 * module name is BankCardManagementActivity
 */
public class BankCardManagementActivity extends BaseActivity<BankCardManagementPresenter> implements BankCardManagementContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.iv_add_bank)
    ImageView mIvAddBank;
    @BindView(R.id.rl_bottom)
    RelativeLayout mRlBottom;
    @BindView(R.id.tv_card)
    TextView mTvCard;
    @BindView(R.id.tv_name)
    TextView mTvName;

    private String bankid;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, BankCardManagementActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerBankCardManagementComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_bank_card_management; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(false);
        mToolbarTitle.setText("银行卡管理");
        initListener();
    }

    public void initListener() {
        if (mPresenter != null) {
            mPresenter.banklist(Preferences.getShopId());
        }

    }

    @OnClick({R.id.toolbar_back, R.id.iv_add_bank, R.id.tv_unbind})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.iv_add_bank:  //添加银行卡
                    AddBankCardActivity.start(this, false);
                    break;
                case R.id.tv_unbind:  //解绑
                    if (mPresenter != null) {
                        mPresenter.unbind(Preferences.getShopId(), bankid);
                    }
                    break;
            }
        }
    }

    @Override
    public void OnBankListSuccess(List<BankListEntity> entity) {
        if (entity.size() > 0) {
            mIvAddBank.setVisibility(View.GONE);
            mRlBottom.setVisibility(View.VISIBLE);
            bankid = entity.get(0).getBank_card_id() + "";
            mTvCard.setText(VerifyUtil.hideCardNo(entity.get(0).getCard_no()));
            mTvName.setText(entity.get(0).getName());
        } else {
            mIvAddBank.setVisibility(View.VISIBLE);
            mRlBottom.setVisibility(View.GONE);
        }
    }

    @Override
    public void OnBankUnbind() {
        showMessage("解绑成功");
        initListener();
    }

    @Override
    public void onNetError() {

    }

    public Activity getActivity() {
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {
        ToastUtil.showToast(message);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void BankCardEvent(BankCardEvent editEvent) {
        if (editEvent != null) {
            initListener();
        }
    }
}
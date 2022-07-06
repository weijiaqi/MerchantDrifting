package com.merchant.drifting.mvp.ui.activity.index;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import com.merchant.drifting.R;
import com.merchant.drifting.data.event.BankCardEvent;
import com.merchant.drifting.di.component.DaggerAddBankCardActivityComponent;
import com.merchant.drifting.mvp.contract.AddBankCardActivityContract;
import com.merchant.drifting.mvp.presenter.AddBankCardActivityPresenter;
import com.merchant.drifting.storageinfo.Preferences;
import com.merchant.drifting.util.ClickUtil;
import com.merchant.drifting.util.StringUtil;
import com.merchant.drifting.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/06/30 10:18
 *
 * @author 添加银行卡
 * module name is AddBankCardActivityActivity
 */
public class AddBankCardActivity extends BaseActivity<AddBankCardActivityPresenter> implements AddBankCardActivityContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.tv_get_code)
    TextView mTvGetCode;
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_card)
    EditText mEtCard;
    @BindView(R.id.et_bank_deposit)
    EditText mEtBankDeposit;
    @BindView(R.id.et_sub_branch)
    EditText mEtSubBranch;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.et_code)
    EditText mEtCode;
    private CountDownTimer timer;

    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, AddBankCardActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAddBankCardActivityComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_add_bank_card; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(false);
        mToolbarTitle.setText("添加银行卡");
        mTvPhone.setText(Preferences.getPhone());
        initListener();
    }

    public void initListener() {
        mTvGetCode.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        mTvGetCode.getPaint().setAntiAlias(true);//抗锯齿
    }

    @OnClick({R.id.toolbar_back, R.id.tv_verification, R.id.tv_get_code})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.tv_get_code:  //获取验证码
                    if (mPresenter != null) {
                        mPresenter.getCodeData(Preferences.getPhone(), 3);
                    }
                    break;
                case R.id.tv_verification:
                    if (StringUtil.isEmpty(mEtName.getText().toString())) {
                        showMessage("请输入姓名");
                        return;
                    }
                    if (StringUtil.isEmpty(mEtCard.getText().toString())) {
                        showMessage("请输入银行卡号");
                        return;
                    }
                    if (StringUtil.isEmpty(mEtBankDeposit.getText().toString())) {
                        showMessage("请输入开户行");
                        return;
                    }
                    if (StringUtil.isEmpty(mEtSubBranch.getText().toString())) {
                        showMessage("请输入开户支行");
                        return;
                    }
                    if (StringUtil.isEmpty(mEtCode.getText().toString())) {
                        showMessage("请输入验证码");
                        return;
                    }
                    if (mPresenter != null) {
                        mPresenter.bankadd(Preferences.getShopId(),mEtName.getText().toString(),mEtBankDeposit.getText().toString(), mEtSubBranch.getText().toString(),  mEtCard.getText().toString(), mTvPhone.getText().toString(), mEtCode.getText().toString());
                    }
                    break;
            }
        }
    }

    @Override
    public void OnBankAddSuccess() {
        finish();
        EventBus.getDefault().post(new BankCardEvent());
    }

    @Override
    public void onCodeSuccess() {
        startCountDown();
    }


    /**
     * 开始倒计时
     */
    private void startCountDown() {
        if (timer == null) {
            timer = new CountDownTimer(60 * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    //剩余秒数
                    int surplusSeconds = (int) (millisUntilFinished / 1000);
                    mTvGetCode.setEnabled(false);
                    mTvGetCode.setText(surplusSeconds + "");
                }

                @Override
                public void onFinish() {
                    mTvGetCode.setEnabled(true);
                    mTvGetCode.setText("重新获取");
                    cleanCountDown();
                }
            }.start();
        }
    }

    /**
     * 结束倒计时
     */
    public void cleanCountDown() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cleanCountDown();
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
}
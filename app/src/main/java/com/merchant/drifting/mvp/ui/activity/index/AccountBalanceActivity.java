package com.merchant.drifting.mvp.ui.activity.index;
import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import com.merchant.drifting.R;
import com.merchant.drifting.di.component.DaggerAccountBalanceComponent;
import com.merchant.drifting.mvp.contract.AccountBalanceContract;
import com.merchant.drifting.mvp.presenter.AccountBalancePresenter;
import com.merchant.drifting.util.ClickUtil;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/06/29 10:56
 * @author 账户余额
 * module name is AccountBalanceActivity
 */
public class AccountBalanceActivity extends BaseActivity<AccountBalancePresenter> implements AccountBalanceContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    public static void start(Context context, boolean closePage) {
        Intent intent = new Intent(context, AccountBalanceActivity.class);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAccountBalanceComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState){
        return R.layout.activity_account_balance; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(false);
        mToolbarTitle.setText("账户余额");
        initListener();
    }

    public void initListener() {

    }


    @OnClick({R.id.toolbar_back,R.id.rl_bank,R.id.rl_running_records})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.rl_bank:
                    BankCardManagementActivity.start(this,false);
                    break;
                case R.id.rl_running_records:  //流水记录
                    RunningRecordsActivity.start(this,false);
                    break;
            }
        }
    }

    public Activity getActivity(){
        return this;
    }

    @Override
    public void showMessage(@NonNull String message) {

    }
}
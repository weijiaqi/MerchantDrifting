package com.merchant.drifting.mvp.ui.activity.index;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.BaseRecyclerAdapter;
import com.jess.arms.di.component.AppComponent;

import com.merchant.drifting.R;
import com.merchant.drifting.di.component.DaggerSwitchMerchantsComponent;
import com.merchant.drifting.mvp.contract.SwitchMerchantsContract;
import com.merchant.drifting.mvp.model.entity.MerchantsEntity;
import com.merchant.drifting.mvp.model.entity.ShopListEntity;
import com.merchant.drifting.mvp.presenter.SwitchMerchantsPresenter;
import com.merchant.drifting.mvp.ui.activity.user.ApplicationMaterialsActivity;
import com.merchant.drifting.mvp.ui.adapter.SwitchMerchantsAdapter;
import com.merchant.drifting.storageinfo.Preferences;
import com.merchant.drifting.util.ClickUtil;
import com.merchant.drifting.util.GlideUtil;
import com.merchant.drifting.view.ThickLineTextSpan;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/06/27 10:35
 *
 * @author 商家中心
 * module name is SwitchMerchantsActivity
 */
public class SwitchMerchantsActivity extends BaseActivity<SwitchMerchantsPresenter> implements SwitchMerchantsContract.View {
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.iv_right_word)
    TextView mTvRightWord;
    @BindView(R.id.tv_shop_name)
    TextView mTvShopName;
    @BindView(R.id.rcy_shop)
    RecyclerView mRcyShop;
    @BindView(R.id.tv_bar)
    TextView mTvBar;
    @BindView(R.id.iv_no_shop)
    ImageView mIvNoShop;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.iv_photo)
    ImageView mIvPhoto;

    private SwitchMerchantsAdapter adapter;
    private int type;
    private static final String EXRA_TYPE = "exra_type";

    public static void start(Context context, int type, boolean closePage) {
        Intent intent = new Intent(context, SwitchMerchantsActivity.class);
        intent.putExtra(EXRA_TYPE, type);
        context.startActivity(intent);
        if (closePage) ((Activity) context).finish();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSwitchMerchantsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_switch_merchants; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBar(true);
        setStatusBarHeight(mTvBar);
        mToolbarTitle.setText("商家中心");
        mTvRightWord.setText("申请记录");
        mTvRightWord.setVisibility(View.VISIBLE);
        if (getIntent() != null) {
            type = getIntent().getIntExtra(EXRA_TYPE, 0);
        }
        initListener();
    }

    public void initListener() {
        mTvName.setText(Preferences.getNickName());
        mTvPhone.setText(Preferences.getPhone());
        GlideUtil.create().loadFileSkipCirclePic(this, Preferences.getPhoto(), mIvPhoto);
        initTextSpan(mTvShopName, "选择店铺开始营业 ");
        if (type == 1) {
            mIvNoShop.setVisibility(View.VISIBLE);
            mRcyShop.setVisibility(View.GONE);
        } else {
            mRcyShop.setLayoutManager(new LinearLayoutManager(this));
            adapter = new SwitchMerchantsAdapter(new ArrayList<>());
            mRcyShop.setAdapter(adapter);
            getData();
            adapter.setRecyclerItemClickListner((v, position) -> finish());
        }
    }

    public void getData() {
        if (mPresenter != null) {
            mPresenter.shoplist();
        }
    }

    @Override
    public void OnShopListSuccess(List<ShopListEntity> entity) {
        if (entity != null && entity.size() > 0) {
            adapter.setData(entity);
        }
    }

    @Override
    public void onNetError() {

    }

    public Activity getActivity() {
        return this;
    }

    private void initTextSpan(TextView textView, String textContent) {
        SpannableStringBuilder mSpannableStringBuilder = new SpannableStringBuilder(textContent);
        ThickLineTextSpan mThickLineTextSpan = new ThickLineTextSpan(this);
        mSpannableStringBuilder.setSpan(mThickLineTextSpan, 0, textContent.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(mSpannableStringBuilder);
    }

    @OnClick({R.id.toolbar_back, R.id.iv_right_word, R.id.iv_no_shop})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.toolbar_back:
                    finish();
                    break;
                case R.id.iv_right_word:
                    ApplicationRecordActivity.start(this, false);
                    break;
                case R.id.iv_no_shop:  //提交资料
                    ApplicationMaterialsActivity.start(this, 1,"",false);
                    break;
            }
        }
    }

    @Override
    public void showMessage(@NonNull String message) {

    }
}
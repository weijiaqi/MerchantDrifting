package com.merchant.drifting.mvp.ui.fragment;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;

import com.merchant.drifting.R;
import com.merchant.drifting.data.event.LogInEvent;
import com.merchant.drifting.di.component.DaggerMineComponent;
import com.merchant.drifting.mvp.contract.MineContract;
import com.merchant.drifting.mvp.model.entity.ShopInfoEntity;
import com.merchant.drifting.mvp.presenter.MinePresenter;
import com.merchant.drifting.mvp.ui.activity.index.StoreManagementActivity;
import com.merchant.drifting.mvp.ui.activity.user.ApplicationMaterialsActivity;
import com.merchant.drifting.mvp.ui.activity.user.SetUpActivity;
import com.merchant.drifting.storageinfo.Preferences;
import com.merchant.drifting.util.ClickUtil;
import com.merchant.drifting.util.GlideUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created on 2022/06/24 12:12
 *
 * @author 我的
 * module name is MineFragment
 */
public class MineFragment extends BaseFragment<MinePresenter> implements MineContract.View {

    @BindView(R.id.toolbar_back)
    RelativeLayout mToobarBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.tv_bar)
    TextView mTvBar;
    @BindView(R.id.iv_right)
    ImageView mIvRight;
    @BindView(R.id.iv_head)
    ImageView mIvHead;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;

    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMineComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

    /**
     * 在 onActivityCreate()时调用
     */
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setStatusBarHeight(mTvBar);
        mToobarBack.setVisibility(View.GONE);
        mToolbarTitle.setText("我的");
        mIvRight.setVisibility(View.VISIBLE);
        mIvRight.setImageResource(R.drawable.seeting);
        initListener();
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    private void initListener() {
        if (mPresenter != null) {
            mPresenter.shopinfo(Preferences.getShopId());
        }
    }

    @OnClick({R.id.iv_right, R.id.rl_store_management, R.id.rl_qualification_information})
    public void onClick(View view) {
        if (!ClickUtil.isFastClick(view.getId())) {
            switch (view.getId()) {
                case R.id.iv_right:
                    SetUpActivity.start(mContext, false);
                    break;
                case R.id.rl_store_management:
                    StoreManagementActivity.start(mContext, false);
                    break;
                case R.id.rl_qualification_information:
                    ApplicationMaterialsActivity.start(mContext, 2, Preferences.getShopId(), false);
                    break;
            }
        }
    }


    @Override
    public void OnShopInfoSuccess(ShopInfoEntity entity) {
        if (entity != null) {
            GlideUtil.create().loadHeadCirclePic(mContext, entity.getImage(), mIvHead);
            mTvPhone.setText("+86 " + entity.getMobile());
            mTvName.setText(entity.getShop_name());
        }
    }

    @Override
    public void onNetError() {

    }

    public Fragment getFragment() {
        return this;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void LoginEvent(LogInEvent logInEvent) {
        if (logInEvent != null) {
            initListener();
        }
    }

    @Override
    public void showMessage(@NonNull String message) {

    }
}
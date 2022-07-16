package com.merchant.drifting.mvp.presenter;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.FragmentActivity;

import com.google.zxing.activity.CaptureActivity;
import com.jess.arms.base.BaseEntity;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.jess.arms.utils.PermissionUtil;
import com.jess.arms.utils.RxLifecycleUtils;
import com.merchant.drifting.mvp.contract.IndexContract;
import com.merchant.drifting.mvp.model.entity.ShopApplyLogEntity;
import com.merchant.drifting.mvp.model.entity.TodayOrderEntity;
import com.merchant.drifting.mvp.model.entity.WriteOffEntity;
import com.merchant.drifting.util.PermissionDialog;
import com.merchant.drifting.util.ViewUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/06/24 11:46
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class IndexPresenter extends BasePresenter<IndexContract.Model, IndexContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public IndexPresenter(IndexContract.Model model, IndexContract.View rootView) {
        super(model, rootView);
    }

    /**
     *余额、今日订单量、营业额（首页）
     */
    public void statistictoday(String shop_id) {
        mModel.statistictoday(shop_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<TodayOrderEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<TodayOrderEntity> baseEntity) {
                        if (mRootView != null) {
                            if (baseEntity.getCode() == 200) {
                               mRootView.OnTodayOrderSuccess(baseEntity.getData());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        if (mRootView != null) {
                            mRootView.onNetError();
                        }
                    }
                });
    }



    /**
     *核销
     */
    public void shopwriteOff(String token,String shop_id) {
        mModel.shopwriteOff(token,shop_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<WriteOffEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<WriteOffEntity> baseEntity) {
                        if (mRootView != null) {
                            if (baseEntity.getCode() == 200) {
                                mRootView.OnShopWriteOff(baseEntity.getData());
                            }else {
                                mRootView.showMessage(baseEntity.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        if (mRootView != null) {
                            mRootView.onNetError();
                        }
                    }
                });
    }

    public void startQrCode(Context context) {
        PermissionUtil.launchCamera(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                mRootView.PermissionVoiceSuccess();
            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {

            }

            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                PermissionDialog.create().showDialog((Activity) context, permissions);
            }
        }, new RxPermissions((FragmentActivity) context), mErrorHandler);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
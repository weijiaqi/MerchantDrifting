package com.merchant.drifting.mvp.presenter;
import android.app.Application;
import android.text.TextUtils;

import com.jess.arms.base.BaseEntity;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.jess.arms.utils.RxLifecycleUtils;
import com.merchant.drifting.mvp.contract.AddBankCardActivityContract;
import com.merchant.drifting.mvp.model.entity.ShopStaticOrderEntity;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/06/30 10:18
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class AddBankCardActivityPresenter extends BasePresenter<AddBankCardActivityContract.Model, AddBankCardActivityContract.View>{
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public AddBankCardActivityPresenter (AddBankCardActivityContract.Model model, AddBankCardActivityContract.View rootView) {
        super(model, rootView);
    }


    /**
     * 获取验证码
     */
    public void getCodeData(  String mobile, int status) {
        mModel.getCode(mobile, status).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity baseEntity) {
                        if (mRootView != null) {
                            mRootView.hideLoading();
                            if (baseEntity.getCode() == 200) {
                                mRootView.onCodeSuccess();
                            } else {
                                if (!TextUtils.isEmpty(baseEntity.getMsg())) {
                                    mRootView.showMessage(baseEntity.getMsg());
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        if (mRootView != null) {
                            mRootView.hideLoading();
                            mRootView.onNetError();
                        }
                    }
                });
    }


    /**
     *添加银行卡
     */
    public void bankadd( String shopid, String name,String bank_name,String branch_name,String card_no,String mobile,String code) {
        mModel.bankadd(shopid,name,bank_name,branch_name,card_no,mobile,code).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity baseEntity) {
                        if (mRootView != null) {
                            if (baseEntity.getCode() == 200) {
                                mRootView.OnBankAddSuccess();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
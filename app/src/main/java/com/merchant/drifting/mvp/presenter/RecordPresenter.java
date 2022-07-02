package com.merchant.drifting.mvp.presenter;

import android.app.Application;

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

import com.jess.arms.utils.RxLifecycleUtils;
import com.merchant.drifting.mvp.contract.RecordContract;
import com.merchant.drifting.mvp.model.entity.ShopApplyLogEntity;
import com.merchant.drifting.mvp.model.entity.ShopListEntity;
import com.merchant.drifting.util.ViewUtil;

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
public class RecordPresenter extends BasePresenter<RecordContract.Model, RecordContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public RecordPresenter(RecordContract.Model model, RecordContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 申请记录
     */
    public void shopapplyLog(int status) {
        if (mRootView != null) {
            mRootView.onloadStart();
        }
        mModel.shopapplyLog(status).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<List<ShopApplyLogEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<List<ShopApplyLogEntity>> baseEntity) {
                        if (mRootView != null) {
                            if (baseEntity.getCode() == 200) {
                                if (baseEntity.getData() == null || baseEntity.getData().size() == 0) {
                                    mRootView.loadState(ViewUtil.NOT_DATA);
                                } else {
                                    mRootView.loadState(ViewUtil.HAS_DATA);
                                }
                                mRootView.OnApplyLog(baseEntity.getData());
                            } else {
                                mRootView.loadState(ViewUtil.NOT_SERVER);
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
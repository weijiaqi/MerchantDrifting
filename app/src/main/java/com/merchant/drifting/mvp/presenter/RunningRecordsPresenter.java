package com.merchant.drifting.mvp.presenter;

import android.app.Application;

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
import com.merchant.drifting.mvp.contract.RunningRecordsContract;
import com.merchant.drifting.mvp.model.entity.BusinessBillEntity;
import com.merchant.drifting.util.ViewUtil;

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
public class RunningRecordsPresenter extends BasePresenter<RunningRecordsContract.Model, RunningRecordsContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public RunningRecordsPresenter(RunningRecordsContract.Model model, RunningRecordsContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 流水记录
     */
    public void businessbill( String shopid, int search_type, String date, int page, int limit,boolean loadType) {
        if (mRootView != null) {
            mRootView.onloadStart();
        }
        mModel.businessbill(shopid,search_type, date,page, limit).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<BusinessBillEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<BusinessBillEntity> baseEntity) {
                        if (mRootView != null) {
                            if (baseEntity.getCode() == 200) {
                                if (baseEntity.getData() == null || baseEntity.getData().getList().size()==0) {
                                    mRootView.loadState(ViewUtil.NOT_DATA);
                                    mRootView.loadFinish(loadType, true);
                                } else {
                                    mRootView.loadState(ViewUtil.HAS_DATA);
                                    mRootView.loadFinish(loadType, false);
                                }
                                mRootView.onRunningRecordSuccess(baseEntity.getData(), loadType);
                            } else {
                                mRootView.loadState(ViewUtil.NOT_SERVER);
                                mRootView.loadFinish(loadType, false);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
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
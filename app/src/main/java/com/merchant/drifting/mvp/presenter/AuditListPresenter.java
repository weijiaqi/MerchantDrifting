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
import com.merchant.drifting.mvp.contract.AuditListContract;
import com.merchant.drifting.mvp.model.entity.AuditingEntity;
import com.merchant.drifting.util.ViewUtil;

import java.util.List;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/07/06 09:47
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class AuditListPresenter extends BasePresenter<AuditListContract.Model, AuditListContract.View>{
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public AuditListPresenter(AuditListContract.Model model, AuditListContract.View rootView) {
        super(model, rootView);
    }


    /**
     * 审核列表
     */
    public void auditing(String shop_id,   int status) {
        if (mRootView != null) {
            mRootView.onloadStart();
        }
        mModel.auditing(shop_id,status).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<List<AuditingEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<List<AuditingEntity>> baseEntity) {
                        if (mRootView != null) {
                            if (baseEntity.getCode() == 200) {
                                if (baseEntity.getData() == null || baseEntity.getData().size() == 0) {
                                    mRootView.loadState(ViewUtil.NOT_DATA);
                                } else {
                                    mRootView.loadState(ViewUtil.HAS_DATA);
                                }
                                mRootView.OnAuditingSuccess(baseEntity.getData());
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
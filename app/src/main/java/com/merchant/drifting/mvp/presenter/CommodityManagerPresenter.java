package com.merchant.drifting.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;

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
import com.merchant.drifting.mvp.contract.CommodityManagerContract;
import com.merchant.drifting.mvp.model.entity.AuditingEntity;
import com.merchant.drifting.mvp.model.entity.GoodsListEntity;
import com.merchant.drifting.mvp.ui.adapter.CommodityManagerAdapter;
import com.merchant.drifting.storageinfo.Preferences;
import com.merchant.drifting.util.ViewUtil;
import com.merchant.drifting.util.callback.BaseDataCallBack;
import com.merchant.drifting.util.request.RequestUtil;

import java.util.List;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/07/01 11:13
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class CommodityManagerPresenter extends BasePresenter<CommodityManagerContract.Model, CommodityManagerContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public CommodityManagerPresenter(CommodityManagerContract.Model model, CommodityManagerContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 审核列表
     */
    public void goodslist(String shop_id, int status) {
        if (mRootView != null) {
            mRootView.onloadStart();
        }
        mModel.goodslist(shop_id, status).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<List<GoodsListEntity>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<List<GoodsListEntity>> baseEntity) {
                        if (mRootView != null) {
                            if (baseEntity.getCode() == 200) {
                                if (baseEntity.getData() == null || baseEntity.getData().size() == 0) {
                                    mRootView.loadState(ViewUtil.NOT_DATA);
                                } else {
                                    mRootView.loadState(ViewUtil.HAS_DATA);
                                }
                                mRootView.OnGoodsListSuccess(baseEntity.getData());
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


    /**
     * 上下架
     *
     * @param
     */
    public void deleteCollect(CommodityManagerAdapter commodityManagerAdapter, int type) {
        List<Object> checkList = commodityManagerAdapter.getSelectEntities();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < checkList.size(); i++) {
            GoodsListEntity videoEntity = (GoodsListEntity) checkList.get(i);
            if (i != checkList.size() - 1) {
                buffer.append(videoEntity.getSku_code() + ",");
            } else {
                buffer.append(videoEntity.getSku_code() + "");
            }
        }
        if (TextUtils.isEmpty(buffer.toString())) {
            return;
        }

        RequestUtil.create().goodsshelf(Preferences.getShopId(), type==0?1:0, buffer.toString(), entity -> {
            if (mRootView != null) {
                if (entity != null) {
                    if (entity.getCode() == 200) {
                        mRootView.OnShelfSuccess();
                    } else {
                        if (!TextUtils.isEmpty(entity.getMsg())) {
                            mRootView.showMessage(entity.getMsg());
                        }
                    }
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
package com.merchant.drifting.util.request;

import android.util.SparseArray;

import com.jess.arms.base.BaseEntity;
import com.jess.arms.base.BaseObserver;
import com.merchant.drifting.app.api.ApiProxy;
import com.merchant.drifting.mvp.model.entity.HaveShopEntity;
import com.merchant.drifting.util.callback.BaseDataCallBack;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;

public class RequestUtil {

    private static RequestUtil mRequestUtil;
    private static SparseArray<Disposable> mDisposables = new SparseArray<>();
    private static int mRequestCount = 0;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static RequestUtil create() {
        if (mRequestUtil == null) {
            synchronized (RequestUtil.class) {
                if (mRequestUtil == null) {
                    mRequestUtil = new RequestUtil();
                }
            }
        }
        return mRequestUtil;
    }



    /**
     * 商家是否有店铺
     *
     * @param callBack
     */
    public void haveshop(BaseDataCallBack<HaveShopEntity> callBack) {
        ApiProxy.getApiService().haveshop()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseObserver<BaseEntity<HaveShopEntity>>() {

                    @Override
                    public void onSubscribe(Disposable disposable) {
                        mDisposables.put(mRequestCount++, disposable);
                    }

                    @Override
                    public void onNext(BaseEntity<HaveShopEntity> entity) {
                        if (callBack != null) {
                            callBack.getData(entity);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (callBack != null) {
                            callBack.getData(null);
                        }
                    }
                });
    }

    /**
     * 取消订阅
     */
    public void disDispose() {
        CloseUtil.unSubscribeList(mDisposables);
    }

}

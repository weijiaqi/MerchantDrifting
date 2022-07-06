package com.merchant.drifting.util.request;

import android.util.SparseArray;

import com.jess.arms.base.BaseEntity;
import com.jess.arms.base.BaseObserver;
import com.merchant.drifting.app.api.ApiProxy;
import com.merchant.drifting.mvp.model.entity.BusinessBalanceEntity;
import com.merchant.drifting.mvp.model.entity.HaveShopEntity;
import com.merchant.drifting.mvp.model.entity.MessageUnreadEntity;
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
     * 未读消息
     *
     * @param callBack
     */
    public void messageunread(BaseDataCallBack<MessageUnreadEntity> callBack) {
        ApiProxy.getApiService().messageunread()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseObserver<BaseEntity<MessageUnreadEntity>>() {

                    @Override
                    public void onSubscribe(Disposable disposable) {
                        mDisposables.put(mRequestCount++, disposable);
                    }

                    @Override
                    public void onNext(BaseEntity<MessageUnreadEntity> entity) {
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
     * 全部标记已读
     *
     * @param callBack
     */
    public void markread(BaseDataCallBack callBack) {
        ApiProxy.getApiService().markread()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseObserver<BaseEntity>() {

                    @Override
                    public void onSubscribe(Disposable disposable) {
                        mDisposables.put(mRequestCount++, disposable);
                    }

                    @Override
                    public void onNext(BaseEntity entity) {
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
     * 我的余额
     *
     * @param callBack
     */
    public void balance( String shopid,  BaseDataCallBack<BusinessBalanceEntity> callBack) {
        ApiProxy.getApiService().balance(shopid)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseObserver<BaseEntity<BusinessBalanceEntity>>() {

                    @Override
                    public void onSubscribe(Disposable disposable) {
                        mDisposables.put(mRequestCount++, disposable);
                    }

                    @Override
                    public void onNext(BaseEntity<BusinessBalanceEntity> entity) {
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
     * 商品上下架
     *
     * @param callBack
     */
    public void goodsshelf(String shop_id,int selling,String sku_codes,  BaseDataCallBack callBack) {
        ApiProxy.getApiService().goodsshelf(shop_id,selling,sku_codes)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseObserver<BaseEntity>() {

                    @Override
                    public void onSubscribe(Disposable disposable) {
                        mDisposables.put(mRequestCount++, disposable);
                    }

                    @Override
                    public void onNext(BaseEntity entity) {
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

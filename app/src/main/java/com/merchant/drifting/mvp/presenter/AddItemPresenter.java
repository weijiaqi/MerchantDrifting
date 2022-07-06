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
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import javax.inject.Inject;

import com.jess.arms.utils.RxLifecycleUtils;
import com.merchant.drifting.mvp.contract.AddItemContract;
import com.merchant.drifting.mvp.model.entity.GoodsInfoEntity;
import com.merchant.drifting.mvp.model.entity.InfoEditEntity;

import java.io.File;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/07/05 10:46
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class AddItemPresenter extends BasePresenter<AddItemContract.Model, AddItemContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public AddItemPresenter(AddItemContract.Model model, AddItemContract.View rootView) {
        super(model, rootView);
    }


    //添加商品
    public void applyForEdit(String explore_id, String goods_code, String intro, String name, String specs, String price, String shop_id, String sweet, String temperature, String ingredient, File goods_image) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), goods_image);
        MultipartBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("explore_id", explore_id)
                .addFormDataPart("goods_code", goods_code)
                .addFormDataPart("intro", intro)
                .addFormDataPart("name", name)
                .addFormDataPart("specs", specs)
                .addFormDataPart("price", price)
                .addFormDataPart("shop_id", shop_id)
                .addFormDataPart("sweet", sweet)
                .addFormDataPart("temperature", temperature)
                .addFormDataPart("ingredient", ingredient)
                .addFormDataPart("goods_image", goods_image.getName(), requestBody)
                .build();

        mModel.goodsadd(multipartBody).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity entity) {
                        if (mRootView != null) {
                            mRootView.hideLoading();
                            if (entity.getCode() == 200) {
                                mRootView.OnGoodsAddSuccess();
                            } else {
                                mRootView.showMessage(entity.getMsg());
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


    //编辑商品
    public void goodsedit(String sku_code, String intro, String name, String specs, String price, String shop_id, String sweet, String temperature, String ingredient, String goods_image) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), new File(goods_image));
        MultipartBody.Builder multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("sku_code", sku_code)
                .addFormDataPart("intro", intro)
                .addFormDataPart("name", name)
                .addFormDataPart("specs", specs)
                .addFormDataPart("price", price)
                .addFormDataPart("shop_id", shop_id)
                .addFormDataPart("sweet", sweet)
                .addFormDataPart("temperature", temperature)
                .addFormDataPart("ingredient", ingredient);

        if (!goods_image.startsWith("http")) {
            multipartBody.addFormDataPart("goods_image", new File(goods_image).getName(), requestBody);
        }
        MultipartBody body = multipartBody.build();
        mModel.goodsedit(body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity entity) {
                        if (mRootView != null) {
                            mRootView.hideLoading();
                            if (entity.getCode() == 200) {
                                mRootView.OnGoodsEditSuccess();
                            } else {
                                mRootView.showMessage(entity.getMsg());
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
     * 编辑商品
     */
    public void goodsinfo(String shop_id, String sku_code) {
        mModel.goodsinfo(shop_id, sku_code).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<GoodsInfoEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<GoodsInfoEntity> baseEntity) {
                        if (mRootView != null) {
                            if (baseEntity.getCode() == 200) {
                                mRootView.OnGoodsInfoSuccess(baseEntity.getData());
                            } else {
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
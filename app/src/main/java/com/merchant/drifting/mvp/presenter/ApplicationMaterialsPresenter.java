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
import com.merchant.drifting.data.entity.ApplicationMaterialsEntity;
import com.merchant.drifting.mvp.contract.ApplicationMaterialsContract;
import com.merchant.drifting.mvp.model.entity.InfoEditEntity;
import com.merchant.drifting.mvp.model.entity.ShopListEntity;

import java.io.File;
import java.util.List;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/06/23 13:21
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class ApplicationMaterialsPresenter extends BasePresenter<ApplicationMaterialsContract.Model, ApplicationMaterialsContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public ApplicationMaterialsPresenter(ApplicationMaterialsContract.Model model, ApplicationMaterialsContract.View rootView) {
        super(model, rootView);
    }


    /**
     * 提交资料
     */

    public void shopapply(String shop_name, String mobile, String contact_name, String location, String address, File facade_image, File interiror_image, String corporation, int gender, int certificate_type, String certificate_no, File certificate_image1, File certificate_image2, File business_license, File permit, String lng, String lat) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), facade_image);
        RequestBody requestBody2 = RequestBody.create(MediaType.parse("image/*"), interiror_image);
        RequestBody requestBody3 = RequestBody.create(MediaType.parse("image/*"), certificate_image1);
        RequestBody requestBody4 = RequestBody.create(MediaType.parse("image/*"), certificate_image2);
        RequestBody requestBody5 = RequestBody.create(MediaType.parse("image/*"), business_license);
        RequestBody requestBody6 = RequestBody.create(MediaType.parse("image/*"), permit);
        MultipartBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("shop_name", shop_name)
                .addFormDataPart("mobile", mobile)
                .addFormDataPart("contact_name", contact_name)
                .addFormDataPart("location", location)
                .addFormDataPart("address", address)
                .addFormDataPart("facade_image", facade_image.getName(), requestBody)
                .addFormDataPart("interior_image", interiror_image.getName(), requestBody2)
                .addFormDataPart("certificate_image1", certificate_image1.getName(), requestBody3)
                .addFormDataPart("certificate_image2", certificate_image2.getName(), requestBody4)
                .addFormDataPart("business_license", business_license.getName(), requestBody5)
                .addFormDataPart("permit", permit.getName(), requestBody6)
                .addFormDataPart("corporation", corporation)
                .addFormDataPart("gender", gender + "")
                .addFormDataPart("certificate_type", certificate_type + "")
                .addFormDataPart("certificate_no", certificate_no)
                .addFormDataPart("lng", lng)
                .addFormDataPart("lat", lat)
                .build();

        mModel.shopapply(multipartBody).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<ApplicationMaterialsEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<ApplicationMaterialsEntity> entity) {
                        if (mRootView != null) {
                            mRootView.hideLoading();
                            if (entity.getCode() == 200) {
                                mRootView.OnShopApplySuccess(entity.getData());
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


    //编辑店铺资料
    public void applyForEdit(String shop_id, String shop_name, String mobile, String contact_name, String location, String address, String facade_image, String interiror_image, String corporation, int gender, int certificate_type, String certificate_no, String certificate_image1, String certificate_image2, String business_license, String permit, String lng, String lat) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"),   new File(facade_image));
        RequestBody requestBody2 = RequestBody.create(MediaType.parse("image/*"),  new File(interiror_image));
        RequestBody requestBody3 = RequestBody.create(MediaType.parse("image/*"), new File(certificate_image1));
        RequestBody requestBody4 = RequestBody.create(MediaType.parse("image/*"),  new File(certificate_image2));
        RequestBody requestBody5 = RequestBody.create(MediaType.parse("image/*"),  new File(business_license) );
        RequestBody requestBody6 = RequestBody.create(MediaType.parse("image/*"), new File(permit) );

        MultipartBody.Builder multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("shop_id", shop_id)
                .addFormDataPart("shop_name", shop_name)
                .addFormDataPart("mobile", mobile)
                .addFormDataPart("contact_name", contact_name)
                .addFormDataPart("location", location)
                .addFormDataPart("address", address)
                .addFormDataPart("corporation", corporation)
                .addFormDataPart("gender", gender + "")
                .addFormDataPart("certificate_type", certificate_type + "")
                .addFormDataPart("certificate_no", certificate_no)
                .addFormDataPart("lng", lng)
                .addFormDataPart("lat", lat);

        if (!facade_image.startsWith("http")) {
            multipartBody.addFormDataPart("facade_image", new File(facade_image).getName(), requestBody);
        }
        if (!interiror_image.startsWith("http")) {
            multipartBody.addFormDataPart("interiror_image", new File(interiror_image).getName(), requestBody2);
        }
        if (!interiror_image.startsWith("http")) {
            multipartBody.addFormDataPart("certificate_image1", new File(certificate_image1).getName(), requestBody3);
        }
        if (!interiror_image.startsWith("http")) {
            multipartBody.addFormDataPart("certificate_image2", new File(certificate_image2).getName(), requestBody4);
        }
        if (!interiror_image.startsWith("http")) {
            multipartBody.addFormDataPart("business_license", new File(business_license).getName(), requestBody5);
        }
        if (!interiror_image.startsWith("http")) {
            multipartBody.addFormDataPart("permit", new File(business_license).getName(), requestBody6);
        }
        MultipartBody body = multipartBody.build();
        mModel.applyForEdit(body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity entity) {
                        if (mRootView != null) {
                            mRootView.hideLoading();
                            if (entity.getCode() == 200) {
                                mRootView.OnShopapplyForEditSuccess();
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
     * 读取店铺信息（编辑时使用）
     */
    public void infoForEdit(String shop_id) {
        mModel.infoForEdit(shop_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<InfoEditEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<InfoEditEntity> baseEntity) {
                        if (mRootView != null) {
                            if (baseEntity.getCode() == 200) {
                                mRootView.OnInfoEditSuccess(baseEntity.getData());
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
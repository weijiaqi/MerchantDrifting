package com.merchant.drifting.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.base.BaseEntity;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.merchant.drifting.app.api.ApiService;
import com.merchant.drifting.mvp.contract.AddBankCardActivityContract;

import io.reactivex.Observable;

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
public class AddBankCardActivityModel extends BaseModel implements AddBankCardActivityContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public AddBankCardActivityModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseEntity> bankadd(String shopid, String name, String bank_name, String branch_name, String card_no, String mobile, String code) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).bankadd(shopid,name,bank_name,branch_name,card_no,mobile,code);
    }

    @Override
    public Observable<BaseEntity> getCode(String mobile, int status) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).verificationcode(mobile,status);
    }
}
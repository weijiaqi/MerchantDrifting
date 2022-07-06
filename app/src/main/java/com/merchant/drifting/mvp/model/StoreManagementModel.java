package com.merchant.drifting.mvp.model;
import android.app.Application;
import com.google.gson.Gson;
import com.jess.arms.base.BaseEntity;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.jess.arms.di.scope.ActivityScope;
import javax.inject.Inject;

import com.merchant.drifting.app.api.ApiService;
import com.merchant.drifting.mvp.contract.StoreManagementContract;
import com.merchant.drifting.mvp.model.entity.ShopInfoEntity;

import io.reactivex.Observable;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/06/28 15:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class StoreManagementModel extends BaseModel implements StoreManagementContract.Model{
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public StoreManagementModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseEntity<ShopInfoEntity>> shopinfo(String shopid) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).shopinfo(shopid);
    }

    @Override
    public Observable<BaseEntity> setOpeningTime(String shop_id ,String opening,String opening_end) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).setOpeningTime(shop_id,opening,opening_end);
    }
}
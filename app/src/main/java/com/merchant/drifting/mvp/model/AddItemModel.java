package com.merchant.drifting.mvp.model;
import android.app.Application;
import com.google.gson.Gson;
import com.jess.arms.base.BaseEntity;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.jess.arms.di.scope.ActivityScope;
import javax.inject.Inject;

import com.merchant.drifting.app.api.ApiService;
import com.merchant.drifting.mvp.contract.AddItemContract;
import com.merchant.drifting.mvp.model.entity.GoodsInfoEntity;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

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
public class AddItemModel extends BaseModel implements AddItemContract.Model{
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public AddItemModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseEntity> goodsadd(MultipartBody shortVoice) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).goodsadd(shortVoice);
    }

    @Override
    public Observable<BaseEntity<GoodsInfoEntity>> goodsinfo(String shop_id, String sku_code) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).goodsinfo(shop_id,sku_code);
    }

    @Override
    public Observable<BaseEntity> goodsedit(MultipartBody shortVoice) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).goodsedit(shortVoice);
    }
}
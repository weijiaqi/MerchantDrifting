package com.merchant.drifting.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.base.BaseEntity;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import com.merchant.drifting.app.api.ApiService;
import com.merchant.drifting.mvp.contract.IndexContract;
import com.merchant.drifting.mvp.model.entity.TodayOrderEntity;

import io.reactivex.Observable;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/06/24 11:46
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class IndexModel extends BaseModel implements IndexContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public IndexModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseEntity<TodayOrderEntity>> statistictoday(String shop_id) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).statistictoday(shop_id);
    }
}
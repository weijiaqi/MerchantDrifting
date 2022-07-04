package com.merchant.drifting.mvp.model;
import android.app.Application;
import com.google.gson.Gson;
import com.jess.arms.base.BaseEntity;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.jess.arms.di.scope.ActivityScope;
import javax.inject.Inject;

import com.merchant.drifting.app.api.ApiService;
import com.merchant.drifting.data.entity.ApplicationMaterialsEntity;
import com.merchant.drifting.mvp.contract.ApplicationMaterialsContract;
import com.merchant.drifting.mvp.model.entity.InfoEditEntity;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

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
public class ApplicationMaterialsModel extends BaseModel implements ApplicationMaterialsContract.Model{
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public ApplicationMaterialsModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseEntity<ApplicationMaterialsEntity>> shopapply(MultipartBody body) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).shopapply(body);
    }

    @Override
    public Observable<BaseEntity<InfoEditEntity>> infoForEdit(String shop_id) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).infoForEdit(shop_id);
    }

    @Override
    public Observable<BaseEntity> applyForEdit(MultipartBody shortVoice) {
        return mRepositoryManager.obtainRetrofitService(ApiService.class).applyForEdit(shortVoice);
    }
}
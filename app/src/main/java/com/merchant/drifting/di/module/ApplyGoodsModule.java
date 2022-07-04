package com.merchant.drifting.di.module;
import com.jess.arms.di.scope.ActivityScope;
import dagger.Binds;
import dagger.Module;
import com.merchant.drifting.mvp.contract.ApplyGoodsContract;
import com.merchant.drifting.mvp.model.ApplyGoodsModel;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/07/02 18:12
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
 //构建ApplyGoodsModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
public abstract class ApplyGoodsModule {

    @Binds
    abstract ApplyGoodsContract.Model bindApplyGoodsModel(ApplyGoodsModel model);
}
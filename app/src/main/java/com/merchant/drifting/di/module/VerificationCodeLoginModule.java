package com.merchant.drifting.di.module;
import com.jess.arms.di.scope.ActivityScope;
import dagger.Binds;
import dagger.Module;
import com.merchant.drifting.mvp.contract.VerificationCodeLoginContract;
import com.merchant.drifting.mvp.model.VerificationCodeLoginModel;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/06/20 10:24
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
 //构建VerificationCodeLoginModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
public abstract class VerificationCodeLoginModule {

    @Binds
    abstract VerificationCodeLoginContract.Model bindVerificationCodeLoginModel(VerificationCodeLoginModel model);
}
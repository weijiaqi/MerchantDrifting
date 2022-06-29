package com.merchant.drifting.di.component;
import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;
import com.merchant.drifting.di.module.VerificationCodeModule;

import com.jess.arms.di.scope.ActivityScope;
import com.merchant.drifting.mvp.contract.VerificationCodeContract;
import com.merchant.drifting.mvp.ui.activity.login.VerificationCodeActivity;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/06/16 18:34
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = VerificationCodeModule.class,dependencies = AppComponent.class)
public interface VerificationCodeComponent {

    void inject(VerificationCodeActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(VerificationCodeContract.View view);

        Builder appComponent(AppComponent appComponent);

        VerificationCodeComponent build();
    }
}
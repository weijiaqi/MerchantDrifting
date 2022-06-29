package com.merchant.drifting.di.component;
import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;
import com.merchant.drifting.di.module.NewsModule;

import com.jess.arms.di.scope.ActivityScope;
import com.merchant.drifting.mvp.contract.NewsContract;
import com.merchant.drifting.mvp.contract.VerificationCodeContract;
import com.merchant.drifting.mvp.ui.activity.merchant.NewsActivity;

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
@ActivityScope
@Component(modules = NewsModule.class,dependencies = AppComponent.class)
public interface NewsComponent {

    void inject(NewsActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(NewsContract.View view);

        Builder appComponent(AppComponent appComponent);

        NewsComponent build();
    }
}
package com.merchant.drifting.di.component;
import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;
import com.merchant.drifting.di.module.ApplicationMaterialsModule;

import com.jess.arms.di.scope.ActivityScope;
import com.merchant.drifting.mvp.contract.ApplicationMaterialsContract;
import com.merchant.drifting.mvp.contract.NewsContract;
import com.merchant.drifting.mvp.ui.activity.user.ApplicationMaterialsActivity;

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
@Component(modules = ApplicationMaterialsModule.class,dependencies = AppComponent.class)
public interface ApplicationMaterialsComponent {

    void inject(ApplicationMaterialsActivity activity);


    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(ApplicationMaterialsContract.View view);

        Builder appComponent(AppComponent appComponent);

        ApplicationMaterialsComponent build();
    }
}
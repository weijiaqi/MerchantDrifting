package com.merchant.drifting.di.component;
import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;
import com.merchant.drifting.di.module.StoreManagementModule;

import com.jess.arms.di.scope.ActivityScope;
import com.merchant.drifting.mvp.contract.RecordContract;
import com.merchant.drifting.mvp.contract.StoreManagementContract;
import com.merchant.drifting.mvp.ui.activity.index.StoreManagementActivity;

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
@Component(modules = StoreManagementModule.class,dependencies = AppComponent.class)
public interface StoreManagementComponent {

    void inject(StoreManagementActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(StoreManagementContract.View view);

        Builder appComponent(AppComponent appComponent);

        StoreManagementComponent build();
    }
}
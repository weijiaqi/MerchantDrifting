package com.merchant.drifting.di.component;
import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;
import com.merchant.drifting.di.module.SystemNotificationModule;

import com.jess.arms.di.scope.ActivityScope;
import com.merchant.drifting.mvp.contract.SystemNotificationContract;
import com.merchant.drifting.mvp.ui.activity.index.SystemNotificationActivity;

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
@ActivityScope
@Component(modules = SystemNotificationModule.class,dependencies = AppComponent.class)
public interface SystemNotificationComponent {

    void inject(SystemNotificationActivity activity);


    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(SystemNotificationContract.View view);

        Builder appComponent(AppComponent appComponent);

        SystemNotificationComponent build();
    }
}
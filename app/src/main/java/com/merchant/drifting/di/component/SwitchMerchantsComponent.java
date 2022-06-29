package com.merchant.drifting.di.component;
import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;
import com.merchant.drifting.di.module.SwitchMerchantsModule;

import com.jess.arms.di.scope.ActivityScope;
import com.merchant.drifting.mvp.contract.SwitchMerchantsContract;
import com.merchant.drifting.mvp.ui.activity.index.SwitchMerchantsActivity;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/06/27 10:35
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = SwitchMerchantsModule.class,dependencies = AppComponent.class)
public interface SwitchMerchantsComponent {

    void inject(SwitchMerchantsActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(SwitchMerchantsContract.View view);

        Builder appComponent(AppComponent appComponent);

        SwitchMerchantsComponent build();
    }
}
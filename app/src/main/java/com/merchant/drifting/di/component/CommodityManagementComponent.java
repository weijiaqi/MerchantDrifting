package com.merchant.drifting.di.component;
import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;
import com.merchant.drifting.di.module.CommodityManagementModule;

import com.jess.arms.di.scope.ActivityScope;
import com.merchant.drifting.mvp.contract.CommodityManagementContract;
import com.merchant.drifting.mvp.contract.SwitchMerchantsContract;
import com.merchant.drifting.mvp.ui.activity.index.CommodityManagementActivity;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/07/01 11:13
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = CommodityManagementModule.class,dependencies = AppComponent.class)
public interface CommodityManagementComponent {

    void inject(CommodityManagementActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(CommodityManagementContract.View view);

        Builder appComponent(AppComponent appComponent);

        CommodityManagementComponent build();
    }
}
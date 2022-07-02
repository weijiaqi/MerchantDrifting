package com.merchant.drifting.di.component;

import com.jess.arms.di.component.AppComponent;

import dagger.BindsInstance;
import dagger.Component;
import com.merchant.drifting.module.CommodityManagerModule;
import com.jess.arms.di.scope.FragmentScope;
import com.merchant.drifting.mvp.contract.CommodityManagementContract;
import com.merchant.drifting.mvp.contract.CommodityManagerContract;
import com.merchant.drifting.mvp.ui.fragment.CommodityManagerFragment;

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
@FragmentScope
@Component(modules = CommodityManagerModule.class, dependencies = AppComponent.class)
public interface CommodityManagerComponent {

    void inject(CommodityManagerFragment fragment);


    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(CommodityManagerContract.View view);

        Builder appComponent(AppComponent appComponent);

        CommodityManagerComponent build();
    }
}
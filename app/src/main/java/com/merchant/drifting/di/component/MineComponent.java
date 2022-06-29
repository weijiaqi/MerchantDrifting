package com.merchant.drifting.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;
import com.merchant.drifting.di.module.MineModule;

import com.jess.arms.di.scope.FragmentScope;
import com.merchant.drifting.mvp.contract.MineContract;
import com.merchant.drifting.mvp.ui.fragment.MineFragment;

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
@FragmentScope
@Component(modules = MineModule.class, dependencies = AppComponent.class)
public interface MineComponent {

    void inject(MineFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(MineContract.View view);

        Builder appComponent(AppComponent appComponent);

        MineComponent build();
    }
}
package com.merchant.drifting.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;
import com.merchant.drifting.di.module.IndexModule;

import com.jess.arms.di.scope.FragmentScope;
import com.merchant.drifting.mvp.contract.IndexContract;

import com.merchant.drifting.mvp.ui.fragment.IndexFragment;


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
@Component(modules = IndexModule.class, dependencies = AppComponent.class)
public interface IndexComponent {

    void inject(IndexFragment fragment);


    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(IndexContract.View view);

        Builder appComponent(AppComponent appComponent);

        IndexComponent build();
    }
}
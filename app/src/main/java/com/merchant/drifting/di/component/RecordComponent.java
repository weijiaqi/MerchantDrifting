package com.merchant.drifting.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;
import com.merchant.drifting.di.module.RecordModule;

import com.jess.arms.di.scope.FragmentScope;
import com.merchant.drifting.mvp.contract.IndexContract;
import com.merchant.drifting.mvp.contract.RecordContract;
import com.merchant.drifting.mvp.ui.fragment.RecordFragment;

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
@Component(modules = RecordModule.class, dependencies = AppComponent.class)
public interface RecordComponent {

    void inject(RecordFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(RecordContract.View view);

        Builder appComponent(AppComponent appComponent);

        RecordComponent build();
    }
}
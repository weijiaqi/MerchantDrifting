package com.merchant.drifting.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;
import com.merchant.drifting.di.module.OrderRecordModule;

import com.jess.arms.di.scope.FragmentScope;

import com.merchant.drifting.mvp.contract.OrderRecordContract;
import com.merchant.drifting.mvp.ui.fragment.OrderRecordFragment;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/06/29 10:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = OrderRecordModule.class, dependencies = AppComponent.class)
public interface OrderRecordComponent {

    void inject(OrderRecordFragment fragment);


    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(OrderRecordContract.View view);

        Builder appComponent(AppComponent appComponent);

        OrderRecordComponent build();
    }
}
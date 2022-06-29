package com.merchant.drifting.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;
import com.merchant.drifting.di.module.OrderDetailModule;

import com.jess.arms.di.scope.ActivityScope;
import com.merchant.drifting.mvp.contract.OrderDetailContract;
import com.merchant.drifting.mvp.contract.RecordContract;
import com.merchant.drifting.mvp.ui.activity.index.OrderDetailActivity;

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
@Component(modules = OrderDetailModule.class, dependencies = AppComponent.class)
public interface OrderDetailComponent {

    void inject(OrderDetailActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(OrderDetailContract.View view);

        Builder appComponent(AppComponent appComponent);

        OrderDetailComponent build();
    }
}
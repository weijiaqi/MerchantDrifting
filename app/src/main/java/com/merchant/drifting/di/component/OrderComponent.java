package com.merchant.drifting.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;
import com.merchant.drifting.di.module.OrderModule;
import com.jess.arms.di.scope.FragmentScope;
import com.merchant.drifting.mvp.contract.OrderContract;
import com.merchant.drifting.mvp.ui.fragment.OrderFragment;

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
@Component(modules = OrderModule.class, dependencies = AppComponent.class)
public interface OrderComponent {
    void inject(OrderFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(OrderContract.View view);

        Builder appComponent(AppComponent appComponent);

        OrderComponent build();
    }
}
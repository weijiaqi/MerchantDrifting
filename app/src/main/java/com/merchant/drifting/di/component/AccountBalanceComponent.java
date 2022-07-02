package com.merchant.drifting.di.component;
import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;
import com.merchant.drifting.di.module.AccountBalanceModule;

import com.jess.arms.di.scope.ActivityScope;
import com.merchant.drifting.mvp.contract.AccountBalanceContract;
import com.merchant.drifting.mvp.contract.ApplicationMaterialsContract;
import com.merchant.drifting.mvp.ui.activity.index.AccountBalanceActivity;

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
@ActivityScope
@Component(modules = AccountBalanceModule.class,dependencies = AppComponent.class)
public interface AccountBalanceComponent {

    void inject(AccountBalanceActivity activity);


    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(AccountBalanceContract.View view);

        Builder appComponent(AppComponent appComponent);

        AccountBalanceComponent build();
    }
}
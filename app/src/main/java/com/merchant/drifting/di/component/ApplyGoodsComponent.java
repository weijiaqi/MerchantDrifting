package com.merchant.drifting.di.component;
import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;
import com.merchant.drifting.di.module.ApplyGoodsModule;

import com.jess.arms.di.scope.ActivityScope;
import com.merchant.drifting.mvp.contract.ApplicationRecordContract;
import com.merchant.drifting.mvp.contract.ApplyGoodsContract;
import com.merchant.drifting.mvp.ui.activity.index.ApplyGoodsActivity;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/07/02 18:12
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = ApplyGoodsModule.class,dependencies = AppComponent.class)
public interface ApplyGoodsComponent {

    void inject(ApplyGoodsActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(ApplyGoodsContract.View view);

        Builder appComponent(AppComponent appComponent);

        ApplyGoodsComponent build();
    }
}
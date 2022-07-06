package com.merchant.drifting.di.component;
import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;
import com.merchant.drifting.di.module.AddItemModule;

import com.jess.arms.di.scope.ActivityScope;
import com.merchant.drifting.mvp.contract.AddItemContract;
import com.merchant.drifting.mvp.contract.ApplicationMaterialsContract;
import com.merchant.drifting.mvp.ui.activity.index.AddItemActivity;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/07/05 10:46
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = AddItemModule.class,dependencies = AppComponent.class)
public interface AddItemComponent {

    void inject(AddItemActivity activity);


    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(AddItemContract.View view);

        Builder appComponent(AppComponent appComponent);

        AddItemComponent build();
    }
}
package com.merchant.drifting.di.component;
import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;
import com.merchant.drifting.di.module.AuditListModule;

import com.jess.arms.di.scope.ActivityScope;
import com.merchant.drifting.mvp.contract.AddItemContract;
import com.merchant.drifting.mvp.contract.AuditListContract;
import com.merchant.drifting.mvp.ui.activity.index.AuditListActivity;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/07/06 09:47
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = AuditListModule.class,dependencies = AppComponent.class)
public interface AuditListComponent {

    void inject(AuditListActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(AuditListContract.View view);

        Builder appComponent(AppComponent appComponent);

        AuditListComponent build();
    }
}
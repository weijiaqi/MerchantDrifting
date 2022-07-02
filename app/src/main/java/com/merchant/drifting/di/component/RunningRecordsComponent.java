package com.merchant.drifting.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;
import com.merchant.drifting.di.module.RunningRecordsModule;

import com.jess.arms.di.scope.ActivityScope;
import com.merchant.drifting.mvp.contract.OrderRecordContract;
import com.merchant.drifting.mvp.contract.RunningRecordsContract;
import com.merchant.drifting.mvp.ui.activity.index.RunningRecordsActivity;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/06/30 10:18
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = RunningRecordsModule.class, dependencies = AppComponent.class)
public interface RunningRecordsComponent {

    void inject(RunningRecordsActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(RunningRecordsContract.View view);

        Builder appComponent(AppComponent appComponent);

        RunningRecordsComponent build();
    }
}
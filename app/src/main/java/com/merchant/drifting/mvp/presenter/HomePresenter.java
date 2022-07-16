package com.merchant.drifting.mvp.presenter;

import android.app.Activity;
import android.app.Application;

import com.jess.arms.base.BaseEntity;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.jess.arms.utils.RxLifecycleUtils;
import com.merchant.drifting.mvp.contract.HomeContract;
import com.merchant.drifting.mvp.model.entity.VersionUpdateEntity;
import com.merchant.drifting.mvp.ui.dialog.VersionUpdateDialog;
import com.merchant.drifting.util.StringUtil;
import com.merchant.drifting.util.ToastUtil;

import java.util.Timer;
import java.util.TimerTask;

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
@ActivityScope
public class HomePresenter extends BasePresenter<HomeContract.Model, HomeContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private static boolean isExit;
    @Inject
    public HomePresenter(HomeContract.Model model, HomeContract.View rootView) {
        super(model, rootView);
    }


    /**
     * 版本更新
     *
     * @param activity this
     */
    public void getVersionInfo(Activity activity) {
        mModel.checkVersion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseEntity<VersionUpdateEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseEntity<VersionUpdateEntity> data) {
                        if (mRootView != null && data.getData() != null) {
                            if (StringUtil.compareVersions(data.getData().getVersion(), StringUtil.getVersion(activity))) {
                                showVersionDialog(activity, data.getData());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }
                });
    }



    /**
     * 版本更新dialog
     *
     * @param activity
     */
    public void showVersionDialog(Activity activity, VersionUpdateEntity data) {
        VersionUpdateDialog versionUpdateDialog = new VersionUpdateDialog(activity, data.getUrl() + "?" + System.currentTimeMillis(), data.getStatus(), data.getMessage(), data.getVersion());
        versionUpdateDialog.show();
    }




    /**
     * 调用双击退出函数
     */
    public void exitBy2Click() {
        Timer tExit;
        if (!isExit) {
            isExit = true;
            ToastUtil.showToast("再按一次退出");
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 2000);
        } else {
            mRootView.finishSuccess();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
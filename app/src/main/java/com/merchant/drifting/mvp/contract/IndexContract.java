package com.merchant.drifting.mvp.contract;

import androidx.fragment.app.Fragment;

import com.jess.arms.base.BaseEntity;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.merchant.drifting.data.entity.ApplicationMaterialsEntity;
import com.merchant.drifting.mvp.model.entity.TodayOrderEntity;
import com.merchant.drifting.mvp.model.entity.WriteOffEntity;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

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
public interface IndexContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void OnTodayOrderSuccess(TodayOrderEntity entity);

        void OnShopWriteOff(WriteOffEntity writeOffEntity);
        void onNetError();

        void PermissionVoiceSuccess();

        Fragment getFragment();

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseEntity<TodayOrderEntity>> statistictoday(String shop_id);
        Observable<BaseEntity<WriteOffEntity>> shopwriteOff(String token, String shop_id);
    }
}
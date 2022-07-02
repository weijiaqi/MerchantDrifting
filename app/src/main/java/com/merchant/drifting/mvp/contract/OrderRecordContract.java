package com.merchant.drifting.mvp.contract;

import androidx.fragment.app.Fragment;


import com.jess.arms.base.BaseEntity;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.merchant.drifting.mvp.model.entity.OrderRecordEntity;
import com.merchant.drifting.mvp.model.entity.ShopApplyLogEntity;

import java.util.List;

import io.reactivex.Observable;

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
public interface OrderRecordContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void onloadStart();

        void loadState(int dataState);

        void OnSalesRanking(List<OrderRecordEntity> list);

        void onNetError();

        Fragment getFragment();

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseEntity<List<OrderRecordEntity>>> salesranking(String shop_id, int days);
    }
}
package com.merchant.drifting.util.callback;


import com.jess.arms.base.BaseEntity;

/**
 * data为返回数据基类
 *
 * @author zhaod
 * @date 2019/1/2
 */

public interface BaseDataCallBack<T> {
    void getData(BaseEntity<T> entity);
}

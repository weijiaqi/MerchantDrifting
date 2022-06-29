package com.jess.arms.base;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Observer基类
 *
 * @author zhaod
 * @date 2018/12/29
 */

public abstract class BaseObserver<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    @Override
    public void onComplete() {

    }
}

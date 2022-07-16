package com.merchant.drifting.util.downloadutil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * 进度传递/主线程事件
 */
public class ThreadProxy {
    private static volatile ThreadProxy mDefaultInstance;
    private final Subject<Object> rxBus = PublishSubject.create().toSerialized();

    private ThreadProxy() {
    }

    public static ThreadProxy create() {
        if (mDefaultInstance == null) {
            synchronized (ThreadProxy.class) {
                if (mDefaultInstance == null) {
                    mDefaultInstance = new ThreadProxy();
                }
            }
        }
        return mDefaultInstance;
    }

    public void send(Object object) {
        rxBus.onNext(object);
    }

    public Observable<Object> toObservable() {
        return rxBus;
    }

    /**
     * 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
     *
     * @param eventType 事件类型
     * @param <T>
     * @return
     */
    public <T> Observable<T> toObservable(Class<T> eventType) {
        return rxBus.ofType(eventType);
    }

    /**
     * 订阅事件并将事件放在主线程上处理
     *
     * @param consumer
     */
    public static void subscribe(Consumer consumer) {
        Observable.just(0)
                .compose(new ObservableTransformer<Object, Object>() {
                    @Override
                    public ObservableSource<Object> apply(Observable<Object> upstream) {
                        return upstream.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread());
                    }
                })
                .subscribe(consumer);
    }

    /**
     * 延迟订阅事件并将事件放在主线程上处理
     *
     * @param delay 延迟时长
     */
    public static void subscribe(Consumer consumer, long delay) {
        Observable.just(0)
                .delay(delay, TimeUnit.MILLISECONDS)
                .compose(new ObservableTransformer<Object, Object>() {
                    @Override
                    public ObservableSource<Object> apply(Observable<Object> upstream) {
                        return upstream.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread());
                    }
                })
                .subscribe(consumer);
    }

}

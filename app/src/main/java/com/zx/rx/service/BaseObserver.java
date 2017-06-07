package com.zx.rx.service;
import com.zx.rx.module.BodyResponse;

import rx.Observer;

/**
 * Created by zx on 2017/6/7.
 */

public abstract class BaseObserver<T> implements Observer<T> {
    public BaseObserver(){
    }
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        onError(RetrofitClient.ErrorHandler.handle(e));
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    public abstract void onSuccess(T t);
    public abstract void onError(BodyResponse resetBody);
}

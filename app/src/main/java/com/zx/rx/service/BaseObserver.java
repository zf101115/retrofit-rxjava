package com.zx.rx.service;
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

    }

}

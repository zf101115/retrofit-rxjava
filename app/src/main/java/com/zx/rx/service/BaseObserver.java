package com.zx.rx.service;
import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.zx.rx.module.BodyResponse;

import org.json.JSONException;


import retrofit2.adapter.rxjava.HttpException;
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
        if (e instanceof HttpException)
        onError(handle(e));
        else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException)
        onError(new BodyResponse(1,"解析错误"));
        else
        onError(new BodyResponse(-1,"无网络"));

    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    public abstract void onSuccess(T t);
    public abstract void onError(BodyResponse resetBody);

        public static BodyResponse handle(Throwable throwable) {
            if (throwable instanceof HttpException) {
                HttpException error = (HttpException) throwable;
                if (error!=null){
                    if (error.code()==401||error.code()==403){
                        return new BodyResponse(error.code(),"未认证");
                    }else if (error.code()>=400 && error.code()<500){
                        return new BodyResponse(error.code(),"访问数据出错");
                    }else if (error.code()>=500 && error.code() <600){
                        return new BodyResponse(error.code(),"无法访问");
                    }
                }
            } else {
                throwable.printStackTrace();
            }
            return null;
        }
}

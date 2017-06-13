package com.zx.rx.service;


import android.content.Context;
import android.net.ParseException;
import android.util.Log;
import android.view.Display;
import android.widget.Toast;

import com.google.gson.JsonParseException;
import com.zx.rx.module.BodyResponse;

import org.json.JSONException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Created by zx on 2017/6/7.
 */

public abstract class BaseObserver<T> implements Observer<T> {
    private Context mContext;
    private static Disposable disposable;
    public BaseObserver(Context mContext){
        this.mContext = mContext;
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
    }

    public static void checkDispose(){
        if (!disposable.isDisposed()){
            disposable.dispose();
        }
    }
    @Override
    public void onError(Throwable e) {
        BodyResponse bodyResponse;
        if (e instanceof HttpException)
            bodyResponse = handle(e);
        else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException)
            bodyResponse = new BodyResponse(1,"解析错误");
        else
            bodyResponse = new BodyResponse(-1,"无网络");
        Toast.makeText(mContext, bodyResponse.getMessage()+"", Toast.LENGTH_SHORT).show();
        onError(bodyResponse);
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
            if (error != null) {
                if (error.code() == 401 || error.code() == 403) {
                    return new BodyResponse(error.code(), "未认证");
                } else if (error.code() >= 400 && error.code() < 500) {
                    return new BodyResponse(error.code(), "访问数据出错");
                } else if (error.code() >= 500 && error.code() < 600) {
                    return new BodyResponse(error.code(), "无法访问");
                }
            }
        } else {
            throwable.printStackTrace();
        }
        return null;
    }
}

package com.zx.rx.service;
import android.content.Context;
import android.net.ParseException;
import android.widget.Toast;

import com.google.gson.JsonParseException;
import com.zx.rx.activity.AreaListActivity;
import com.zx.rx.module.BodyResponse;

import org.json.JSONException;


import retrofit2.adapter.rxjava.HttpException;
import rx.Observer;


/**
 * Created by zx on 2017/6/7.
 */

public abstract class BaseObserver<T> implements Observer<T> {
    private Context mContext;
    public BaseObserver(Context mContext){
        this.mContext = mContext;
    }
    @Override
    public void onCompleted() {}
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

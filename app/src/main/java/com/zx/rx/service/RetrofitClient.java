package com.zx.rx.service;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zx.rx.Config;
import com.zx.rx.module.BodyResponse;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zx on 2017/4/20.
 */

public class RetrofitClient {
    private static long DEFAULTE_TIMEOUT = 30;

    public static Retrofit getRetrofit() {

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:sss")
                .create();
        /**
         * 设置log级别
         *     NONE：不记录
         *     BASIC:请求/响应行
         *     HEADERS：请求/响应行 + 头
         *     BODY 请求/响应行 + 头 + 体
         */
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(DEFAULTE_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true).build();

        return new Retrofit.Builder()
                .client(client)
                .baseUrl(Config.API_HOST)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

    }




    public static class ErrorHandler {

        public static BodyResponse handle(Throwable throwable) {
            if (throwable instanceof HttpException) {
                HttpException error = (HttpException) throwable;
                try {
                    return new Gson().fromJson(error.response().errorBody().string(),
                            BodyResponse.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                throwable.printStackTrace();
            }
            return null;
        }
    }

}

package com.zx.rx.service;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zx.rx.Config;
import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zx on 2017/4/20.
 */

public class RetrofitClient {
    private static long DEFAULTE_TIMEOUT = 30;
    private Retrofit retrofit;

    private RetrofitClient() {

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
        logging.setLevel(HttpLoggingInterceptor.Level.NONE);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
//                .connectTimeout(DEFAULTE_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true).build();

         retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(Config.API_HOST)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

    }


    private static class SingletonHolder{
        private static RetrofitClient retrofitClient = null;

        private SingletonHolder(){}
        private static RetrofitClient getInstance(){
            if (retrofitClient==null){
                synchronized (RetrofitClient.class){
                    if (retrofitClient==null){
                        retrofitClient = new RetrofitClient();
                    }
                }
            }
            return retrofitClient;
        }
    }
    /**
     * 获取RetrofitClient
     * @return
     */
    public static RetrofitClient getInstance(){
        return SingletonHolder.getInstance();
    }
    /**
     * 获取对应的Service
     * @param service Service 的 class
     * @param <T>
     * @return
     */
    public <T> T create(Class<T> service){
        return retrofit.create(service);
    }



}

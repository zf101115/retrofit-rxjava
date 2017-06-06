package com.zx.rx;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zx on 2017/4/20.
 */

public class NetBase {
    private static long DEFAULTE_TIMEOUT = 30;

    public static Retrofit getRetrofit() {

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:sss")
                .create();

        return new Retrofit.Builder()
                .client(client)
                .baseUrl(Config.API_HOST)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    static OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new LogInterceptor())
            .connectTimeout(DEFAULTE_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true).build();

    private static class LogInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Log.e("LogUtils--> ", "request:" + request.toString());
            okhttp3.Response response = chain.proceed(chain.request());
            okhttp3.MediaType mediaType = response.body().contentType();
            String content = response.body().string();
            Log.e("LogUtils--> ", "response body:" + content);
            if (response.body() != null) {
                ResponseBody body = ResponseBody.create(mediaType, content);
                return response.newBuilder().body(body).build();
            } else {
                return response;
            }
        }
    }

}

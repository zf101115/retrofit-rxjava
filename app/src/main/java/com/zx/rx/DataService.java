package com.zx.rx;
import retrofit2.adapter.rxjava.Result;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by zx on 2017/4/20.
 */

public class DataService extends NetBase{


   private static ApiInterface api;

    public ApiInterface getDataApi() {
        if (api == null) {
            api = getRetrofit().create(ApiInterface.class);
        }
        return api;
    }



    public interface ApiInterface{

        @GET("basil2style")
        Observable<User> getString();
    }
}

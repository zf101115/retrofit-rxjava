package com.zx.rx.service;
import com.zx.rx.module.Area;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by zx on 2017/4/20.
 */

public class AreaService extends RetrofitClient {


   private static ApiInterface api;

    public static ApiInterface getDataApi() {
        if (api == null) {
            api = getRetrofit().create(ApiInterface.class);
        }
        return api;
    }



    public interface ApiInterface{

        @POST("news/api/areas")
        Observable<List<Area>> getAreas();
    }
}

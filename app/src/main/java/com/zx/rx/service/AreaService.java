package com.zx.rx.service;

import com.zx.rx.module.Area;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by zx on 2017/4/20.
 */

public interface AreaService {

    @GET("news/api/areas")
    Observable<List<Area>> getAreas();

    @GET("news/api/areas")
    Call<List<Area>> getReAreas();

    @GET("area/cityList/v2/{parentId}")
    Observable<List<Area>> getCityArea(@Path("parentId") Integer parentId);

}
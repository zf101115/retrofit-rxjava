package com.zx.rx.service;

import com.zx.rx.module.Area;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by zx on 2017/4/20.
 */

 public interface AreaService {

        @GET("news/api/areas")
        Observable<List<Area>> getAreas();
 }
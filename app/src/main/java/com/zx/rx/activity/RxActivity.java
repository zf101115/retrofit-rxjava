package com.zx.rx.activity;

import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.zx.rx.R;
import com.zx.rx.module.Area;
import com.zx.rx.module.BodyResponse;
import com.zx.rx.service.AreaService;
import com.zx.rx.service.BaseObserver;
import com.zx.rx.service.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by zx on 2017/6/8.
 */

public class RxActivity extends AppCompatActivity {

    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_rxjava);
        ButterKnife.bind(this);

    }
    @OnClick(R.id.button)
    public void click(){
//        httpclick();
        incache();
    }

    List<Area> list;

    private void incache() {
        list = new ArrayList<>();
        list.add(new Area(100, "火星"));
//        for (int i=0;i<=2;i++){
//
        final int[] i = {0};
            Observable.just("caCheKey","caCheKey","caCheKey")//cache key
                    .map(new Func1<String, String>() {
                        @Override
                        public String call(String s) {
                            Log.e("zx===",
                                    Looper.myLooper() == Looper.getMainLooper() ? "主线程" : "子线程");
                            return "cache.getAsString(s)";//josn String
                        }
                    }).flatMap(new Func1<String, Observable<List<Area>>>() {
                @Override
                public Observable<List<Area>> call(String s) {
                    if (null!= s ||!"".equals(s)) {
                        //网络请求
                        return RetrofitClient.getInstance().create(AreaService.class).getAreas();
                    } else {
                        //Gosn转换 gson.fromJson(s, type)
                        return Observable.just(list);
                    }
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<List<Area>>(RxActivity.this) {
                        @Override
                        public void onSuccess(List<Area> areas) {
                            if ( i[0] ==0){
                                tv.setText(areas.get(0).getName()+i[0]);
                            }else if ( i[0]==1){
                                 tv2.setText(areas.get(1).getName()+i[0]);
                            }else {
                                tv3.setText(areas.get(2).getName()+i[0]);
                            }
                            i[0]++;


                        }

                        @Override
                        public void onError(BodyResponse resetBody) {

                        }
                    });


//        }

    }
    private void httpclick() {

        RetrofitClient.getInstance().create(AreaService.class).getAreas()
                .doOnNext(new Action1<List<Area>>() {
                    @Override
                    public void call(List<Area> areas) {
                        Log.e("zx===", Looper.myLooper()==Looper.getMainLooper()?"主线程":"子线程");
                    }
                })
                .flatMap(new Func1<List<Area>, Observable<List<Area>>>() {
                    @Override
                    public Observable<List<Area>> call(List<Area> areas) {

                     return RetrofitClient.getInstance().create(AreaService.class).getCityArea(areas.get(1).getId());

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<Area>>(RxActivity.this) {
                    @Override
                    public void onSuccess(List<Area> areas) {
                        tv.setText("areaName:"+areas.get(0).getName()+"areaId:"+areas.get(0).getId());
                    }
                    @Override
                    public void onError(BodyResponse resetBody) {
                    }
                });
    }
}

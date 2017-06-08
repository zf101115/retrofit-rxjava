package com.zx.rx.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.zx.rx.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by zx on 2017/6/8.
 */

public class RxActivity extends AppCompatActivity {

    @BindView(R.id.tv)
    TextView tv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_rxjava);
        ButterKnife.bind(this);

//        Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                subscriber.onNext("1");
//                subscriber.onNext("2");
//                subscriber.onNext("3");
//                subscriber.onCompleted();
//            }
//        })
        Integer[] aaa = {1,2,3};
         Observable.from(aaa)
         .map(new Func1<Integer, String>() {
             @Override
             public String call(Integer integer) {
                 return integer+"00";
             }
         })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.e("zx====",s);
                tv.setText(s);
            }
        });




    }
}

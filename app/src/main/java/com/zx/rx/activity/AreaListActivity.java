package com.zx.rx.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ListView;

import com.zx.rx.AreaAdapter;
import com.zx.rx.R;
import com.zx.rx.module.Area;
import com.zx.rx.service.BaseObserver;
import com.zx.rx.service.AreaApi;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zx on 2017/6/6.
 */

public class AreaListActivity extends AppCompatActivity {

    @BindView(R.id.lv)
    ListView lv;
    private AreaAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_layout);
        ButterKnife.bind(this);
        mAdapter = new AreaAdapter(this);
        lv.setAdapter(mAdapter);
        LayoutAnimationController lac = new LayoutAnimationController(AnimationUtils.loadAnimation(this, R.anim.zoom_in));
        lac.setOrder(LayoutAnimationController.ORDER_NORMAL);
        lv.setLayoutAnimation(lac);
        lv.startLayoutAnimation();
    }


    @OnClick(R.id.action)
    public void action(){
        AreaApi.getDataApi().getAreas()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<Area>>() {
                    @Override
                    public void onNext(List<Area> areas) {
                        mAdapter.setItems(areas);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    @OnClick(R.id.close)
    public void close(){


    }

}

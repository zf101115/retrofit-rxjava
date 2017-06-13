package com.zx.rx.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ListView;
import android.widget.Toast;

import com.zx.rx.AreaAdapter;
import com.zx.rx.R;
import com.zx.rx.module.Area;
import com.zx.rx.module.BodyResponse;
import com.zx.rx.service.AreaService;
import com.zx.rx.service.BaseObserver;
import com.zx.rx.service.RetrofitClient;



import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;

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
        Toast.makeText(AreaListActivity.this, "action", Toast.LENGTH_SHORT).show();

        RetrofitClient.getInstance().create(AreaService.class).getAreas()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<Area>>(AreaListActivity.this) {
                    @Override
                    public void onSuccess(List<Area> areas) {
                        mAdapter.setItems(areas);
                    }
                    @Override
                    public void onError(BodyResponse resetBody) {
                    }
                });
    }

    @OnClick(R.id.close)
    public void close(){
        Toast.makeText(AreaListActivity.this, "close", Toast.LENGTH_SHORT).show();

//        if (null!=subscription&&!subscription.isUnsubscribed()){
//            subscription.unsubscribe();
//        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        BaseObserver.checkDispose();
//        if (null!=subscription&&!subscription.isUnsubscribed()){
//            subscription.unsubscribe();
//            Toast.makeText(AreaListActivity.this, "close", Toast.LENGTH_SHORT).show();
//        }
    }
}

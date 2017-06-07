package com.zx.rx.activity;

import android.app.Activity;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.zx.rx.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zx on 2017/6/6.
 */

public class SvgActivity extends AppCompatActivity {

    @BindView(R.id.request)
    ImageView request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_svg);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.request)
    public void setRequest(){
        startAnimation();
    }


    private void startAnimation() {
        Drawable drawable = request.getDrawable();
        if (drawable instanceof Animatable){
            ((Animatable) drawable).start();
        }
    }
}

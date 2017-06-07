package com.zx.rx.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.zx.rx.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        iv = (ImageView) findViewById(R.id.iv_share);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ShowActivity.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,iv,"share").toBundle());
            }
        });
    }

    @OnClick(R.id.svg)
    public void setIv(){
        Intent intent = new Intent(MainActivity.this,SvgActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.rx)
    public void rx(){
        Intent intent = new Intent(MainActivity.this,AreaListActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.voice)
    public void aVoid(){
        Intent intent = new Intent(MainActivity.this,VoiceActivity.class);
        startActivity(intent);
    }


}

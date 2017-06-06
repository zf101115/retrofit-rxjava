package com.zx.rx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

/**
 * Created by root on 17-6-5.
 */

public class ShowActivity extends AppCompatActivity {
    private ImageView iv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_share);
        iv = (ImageView) findViewById(R.id.share);
        iv.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
    }
}

package com.zx.rx;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by zx on 2017/6/9.
 */

public class MyApplicatioin extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        final Context appContext =  this.getApplicationContext();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }
}

package com.lu.libbanner;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * author: luqihua
 * date:2018/7/16
 * description:
 **/
public class MyApplication extends Application{
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}

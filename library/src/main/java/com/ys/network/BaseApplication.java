package com.ys.network;

import android.app.Application;

/**
 * Created by Home on 2018/8/9.
 */

public class BaseApplication extends Application {
    private static BaseApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static BaseApplication getInstance() {
        return instance;
    }
}

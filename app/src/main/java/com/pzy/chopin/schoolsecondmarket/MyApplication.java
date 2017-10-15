package com.pzy.chopin.schoolsecondmarket;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Chopin on 2017/8/30.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true);
    }
}

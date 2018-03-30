package com.pnpdevelopers.patryk.threes.util;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        MyApplication.context = this;
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}
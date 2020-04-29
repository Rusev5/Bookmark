package com.lxf.bookmark;

import android.app.Application;
import android.content.Context;

public class Bookmark extends Application {
    private static Context AppInstance;

    public static Context getAppInstance() {
        return AppInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppInstance=getApplicationContext();
    }
}

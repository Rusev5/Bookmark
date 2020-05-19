package com.lxf.bookmark;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class Bookmark extends Application {
    private static Context AppInstance;
    private static SharedPreferences userSharedPreferences;

    public static Context getAppInstance() {
        return AppInstance;
    }

    public static SharedPreferences getUserSharedPreferences() {
        return userSharedPreferences;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppInstance = getApplicationContext();
        userSharedPreferences = getSharedPreferences("bookmark_setting", MODE_PRIVATE);
    }

    public static void showToast(Context context, String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.show();
    }

}

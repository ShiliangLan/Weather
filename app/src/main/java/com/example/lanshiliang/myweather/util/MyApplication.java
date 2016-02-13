package com.example.lanshiliang.myweather.util;

import android.app.Application;
import android.content.Context;

/**
 * Created by lanshiliang on 2016/2/13.
 */
public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}

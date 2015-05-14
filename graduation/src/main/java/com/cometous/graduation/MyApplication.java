package com.cometous.graduation;

import android.app.Application;

import com.cometous.graduation.http.RequestManager;
import com.cometous.graduation.http.Task;

/**
 * Created by Devilsen on 2015/5/3.
 */
public class MyApplication extends Application {

    private static MyApplication instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        init();

    }

    private void init(){
        Task.init();
        RequestManager.init(this);
    }

    public static MyApplication getInstance() {
        return instance;
    }


}

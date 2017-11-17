package com.example.alex.coordinatedemo.base;

import android.app.Application;

/**
 * TODO
 *
 * @author YinGen Chu
 * @date 2017/11/17 11:25.
 * @email: youguyilin@126.com
 */

public class BaseApplication extends Application {

    private static BaseApplication sBaseApplication;

    public static  BaseApplication getInstance(){
        return sBaseApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sBaseApplication = this;
    }

    public static BaseApplication getContext(){
        return sBaseApplication;
    }
}

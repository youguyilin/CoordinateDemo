package com.example.alex.coordinatedemo.base;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author YinGen Chu
 * @date 2017/11/17 13:54.
 * @email: youguyilin@126.com
 */

public class ActivityCollector {
    private final static String TAG = "ActivityCollector";
    private static List<Activity> sActivities = new ArrayList<>();

    public static void add(Activity activity){
        sActivities.add(activity);
    }

    public static void remove(Activity activity){
        sActivities.remove(activity);
    }

    public static Activity getTop(){
        if (sActivities.isEmpty()){
            return null;
        }
        return sActivities.get(sActivities.size()-1);
    }

    public void quit(){
        try {
            for (Activity activity: sActivities){
                if (!activity.isFinishing()){
                    activity.finish();
                }
            }
        }catch (Exception e){
            System.exit(0);
            WriteLogUtil.e(e.getMessage());
        }
    }

}

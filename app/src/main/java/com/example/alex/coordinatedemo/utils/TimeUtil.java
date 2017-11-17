package com.example.alex.coordinatedemo.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TODO
 *
 * @author YinGen Chu
 * @date 2017/11/17 11:56.
 * @email: youguyilin@126.com
 */

public class TimeUtil {

    /**
     * yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getNowYMDHMSTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = dateFormat.format(new Date());
        return date;
    }

    /**
     * MM-dd HH:mm:ss
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getNowMDHMSTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
        String date = dateFormat.format(new Date());
        return date;
    }

    /**
     * MM-dd
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getNowMDTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");
        String date = dateFormat.format(new Date());
        return date;
    }

     /**
     *yyy-MM-dd
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getNowYMDTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(new Date());
        return date;
    }


}

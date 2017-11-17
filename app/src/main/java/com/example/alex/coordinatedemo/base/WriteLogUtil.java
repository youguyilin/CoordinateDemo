package com.example.alex.coordinatedemo.base;

import android.content.Context;
import android.os.Environment;

import com.example.alex.coordinatedemo.utils.TimeUtil;
import com.orhanobut.logger.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


/**
 * TODO
 *
 * @author YinGen Chu
 * @date 2017/11/17 11:30.
 * @email: youguyilin@126.com
 */

public class WriteLogUtil {
    private static final String TAG = "YinGen";
    //public static String cacheDir = "";
    public static String cacheDir = "/sdcard";
    public static String PATH = cacheDir + "/Log";
    public static final String LOG_FILE_NAME = "log.txt";

    /**
     * 是否写入日志
     */
    public static final boolean LOG_WRITE_TO_FILE = false;
    public static final boolean isIShow = true;
    public static final boolean isDShow = true;
    public static final boolean isWShow = true;
    public static final boolean isEShow = true;

    public static void init(Context context) {
        Context applicationContext = context.getApplicationContext();
        if (applicationContext.getExternalCacheDir() != null && isExistSDCard()) {
            cacheDir = applicationContext.getExternalCacheDir().toString();
        } else {
            cacheDir = applicationContext.getCacheDir().toString();
        }
        PATH = cacheDir + "/Log";
        Logger.init(TAG)            //default PRETTYLOGGER or use just init
                .methodCount(3)     //default 2
                .hideThreadInfo();
//        Logger.init(TAG)            //default PRETTYLOGGER or use just init
//                .methodCount(3)     //default 2
//                .hideThreadInfo()
//                .logLevel(LogLevel.FULL) //default LogLevel.FULL
//                .methodOffset(2);        //default 0
    }

    public static final void json(String msg) {
        Logger.json(msg);
    }

    /**
     * 错误信息
     * @param tag
     * @param msg
     */
    private static final void e(String tag, String msg) {
        if (isEShow) {
            Logger.t(tag).e(msg);
        }
        if (LOG_WRITE_TO_FILE) writeLogtoFile("e", tag, msg);
    }

    public  final static void e(String msg){
         e(TAG,msg);
    }

    /**
     * 警告信息
     * @param tag
     * @param msg
     */
    private final static void w(String tag,String msg){
        if(isWShow){
            Logger.t(tag).w(msg);
        }
        if (LOG_WRITE_TO_FILE) writeLogtoFile("w",tag,msg);
    }

    public final static void w(String msg){
        w(TAG,msg);
    }

    /**
     * 调试信息
     * @param tag
     * @param msg
     */
    private final static void d(String tag,String msg){
        if(isDShow){
            Logger.t(tag).d(msg);
        }
        if (LOG_WRITE_TO_FILE) writeLogtoFile("w",tag,msg);
    }

    public final static void d(String msg){
        d(TAG,msg);
    }

    /**
     * 提示信息
     * @param tag
     * @param msg
     */
    private final static void i(String tag,String msg){
        if(isDShow){
            Logger.t(tag).i(msg);
        }
        if (LOG_WRITE_TO_FILE) writeLogtoFile("w",tag,msg);
    }

    public final static void i(String msg){
        i(TAG,msg);
    }





    /**
     * 将日志写入文件中
     *
     * @param logType
     * @param tag
     * @param msg
     */
    private static void writeLogtoFile(String logType, String tag, String msg) {
        isExist(PATH);
        String needWriteMessage = "\r\n" + TimeUtil.getNowMDHMSTime() + "\r\n" + logType + "    " + tag + "\r\n" + msg;
        File file = new File(PATH, LOG_FILE_NAME);
        try {
            FileWriter writer = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(msg);
            bufferedWriter.newLine();
            bufferedWriter.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除日志文件
     */
    public static void delFile() {
        File file = new File(PATH, LOG_FILE_NAME);
        if (file.exists()) file.delete();
    }

    /**
     * 判断文件是否存在
     *
     * @param path
     * @return
     */
    public static void isExist(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static boolean isExistSDCard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
}

package com.wcl.notchfit.utils;

import android.util.Log;

/**
 * 日志工具类
 * Created by wangchunlong on 2018/10/24.
 */

public class LogUtils {
    private static String TAG = "NotchFit";

    public static void i(String msg){
        Log.i(TAG, msg);
    }

    public static void e(String msg){
        Log.e(TAG, msg);
    }
}

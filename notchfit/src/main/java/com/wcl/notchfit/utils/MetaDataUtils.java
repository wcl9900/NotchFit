package com.wcl.notchfit.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

/**
 * Created by wangchunlong on 2018/10/26.
 */

public class MetaDataUtils {
    /**
     * 获取Activity在AndroidManifest.xml配置的参数数据
     * @param context
     * @param metaKey 键
     * @param <T>
     * @return
     */
    public  static   <T> T getActivityMetaData(Activity context, String metaKey){
        try {
            ActivityInfo activityInfo = context.getPackageManager().getActivityInfo(context.getComponentName(), PackageManager.GET_META_DATA);
            if(activityInfo.metaData != null) {
                Object value = activityInfo.metaData.get(metaKey);
                return (T) value;
            }
            return null;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取Application在AndroidManifest.xml配置的参数数据
     * @param context
     * @param metaKey 键
     * @param <T>
     * @return
     */
    public static <T> T getApplicationMetaData(Context context, String metaKey){
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if(applicationInfo.metaData != null) {
                Object value = applicationInfo.metaData.get(metaKey);
                return (T) value;
            }
            return null;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}

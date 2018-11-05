package com.wcl.notchfit.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

import java.lang.reflect.Field;

/**
 * Created by wangchunlong on 2018/10/24.
 */

public class ActivityUtils {
    /**
     * 设置全屏显示
     * @param activity
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void setFullScreen(Activity activity){
//        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    /**
     * 判断是否是全屏
     * @param activity
     * @return
     */
    public static boolean isFullScreen(Activity activity){
        if ( (activity.getWindow().getAttributes().flags & WindowManager.LayoutParams.FLAG_FULLSCREEN)
                == WindowManager.LayoutParams.FLAG_FULLSCREEN) {
            return true;
        }

       if((activity.getWindow().getDecorView().getSystemUiVisibility() & View.SYSTEM_UI_FLAG_FULLSCREEN)
                == View.SYSTEM_UI_FLAG_FULLSCREEN){
            return true;
       }

       if(activity.getTheme() != null) {
           TypedValue typedValue = new TypedValue();
           activity.getTheme().obtainStyledAttributes(
                   new int[]{android.R.attr.windowFullscreen}).getValue(0, typedValue);
           if (typedValue.type == TypedValue.TYPE_INT_BOOLEAN) {
               if (typedValue.data != 0) {
                   return true;
               }
           }
       }
       return false;
    }
    /**
     * 沉浸式状态栏
     *
     * @param activity 需要设置的activity
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setTranslucent(Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
    }

    /**
     * 获取Activity内容父视图
     * @param activity
     * @return
     */
    public static View getDecorContentParentView(Activity activity){
        int decor_content_parent_id = 0;
        try {
            Class<?> androidId = Class.forName(activity.getPackageName() + ".R$id");
            Field decor_content_parent = androidId.getField("decor_content_parent");
            decor_content_parent.setAccessible(true);
            decor_content_parent_id = (int) decor_content_parent.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(decor_content_parent_id != 0){
            return activity.findViewById(decor_content_parent_id);
        }
        return null;
    }
}

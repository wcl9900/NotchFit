package com.wcl.notchfit.manufacturer;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Window;

import com.wcl.notchfit.core.AbstractNotch;
import com.wcl.notchfit.utils.LogUtils;

import java.lang.reflect.Method;

/**
 * 小米刘海屏
 * Created by wangchunlong on 2018/10/23.
 */

public class XiaomiNotch extends AbstractNotch {

    int flag = 0x00000100 | 0x00000200 | 0x00000400;
    @Override
    protected void applyNotch_O(Activity activity) {
        try {
            Method method = Window.class.getMethod("addExtraFlags",
                    int.class);
            method.invoke(activity.getWindow(), flag);
        } catch (Exception e) {
            LogUtils.i("xiaomi addExtraFlags not found.");
        }
    }

    @Override
    protected void disApplyNotch_O(Activity activity) {
        try {
            Method method = Window.class.getMethod("clearExtraFlags ",
                    int.class);
            method.invoke(activity.getWindow(), flag);
        } catch (Exception e) {
            LogUtils.i("xiaomi clearExtraFlags not found.");
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected boolean isNotchEnable_O(Activity activity) {
        //判断系统是否支持刘海屏
        try {
            Class<?> aClass = Class.forName("android.os.SystemProperties");
            Method getInt = aClass.getMethod("getInt", String.class, int.class);
            int invoke = (int) getInt.invoke(null, "ro.miui.notch", 0);
            if(invoke != 1){
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        //系统设置是否隐藏刘海屏
        boolean isNotchSettingClose = Settings.Global.getInt(activity.getContentResolver(), "force_black", 0) == 1;
        if(isNotchSettingClose){
            return false;
        }

        return true;
    }

    @Override
    protected int[] getNotchSize_O(Activity activity) {
        int[] notchSize = new int[]{0,0};
        try {
            //获取 Notch / 凹口 / 刘海 的高度和宽度（适用MIUI 8.6.26版本及以上）
            int widthResourceId = activity.getResources().getIdentifier("notch_width", "dimen", "android");
            if (widthResourceId > 0) {
                notchSize[0] = activity.getResources().getDimensionPixelSize(widthResourceId);
            }
            int heightResourceId = activity.getResources().getIdentifier("notch_height", "dimen", "android");
            if (heightResourceId > 0) {
                notchSize[1] = activity.getResources().getDimensionPixelSize(heightResourceId);
            }
        }catch (Exception e){
        }

        if(notchSize[0] != 0 || notchSize[1] != 0){
            return notchSize;
        }

        //MIUI接口获取刘海参数失败后，通过具体设备获取
        if (TextUtils.equals(Build.MODEL, "MI 8")) {
            notchSize[0] = 560;
            notchSize[1] = 89;
        } else if (TextUtils.equals(Build.MODEL, "MI 8 SE")) {
            notchSize[0] = 540;
            notchSize[1] = 85;
        } else if (TextUtils.equals(Build.MODEL, "MI8 Explorer Edition")) {
            notchSize[0] = 560;
            notchSize[1] = 89;
        } else if (TextUtils.equals(Build.MODEL, "Redmi 6 Pro")) {
            notchSize[0] = 352;
            notchSize[1] = 89;
        }
        return notchSize;
    }
}

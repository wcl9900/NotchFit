package com.wcl.notchfit.manufacturer;

import android.app.Activity;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Window;

import com.wcl.notchfit.core.AbstractNotch;
import com.wcl.notchfit.utils.LogUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 小米刘海屏
 * Created by wangchunlong on 2018/10/23.
 */
public class XiaomiNotch extends AbstractNotch {
//    0x00000100 | 0x00000200 竖屏绘制到耳朵区
//    0x00000100 | 0x00000400 横屏绘制到耳朵区
//    0x00000100 | 0x00000200 | 0x00000400 横竖屏都绘制到耳朵区
    int notchFlag = 0x00000100 | 0x00000200 | 0x00000400;

    @Override
    protected void applyNotch_O(Activity activity) {
        try {
            Method method = Window.class.getMethod("addExtraFlags",
                    int.class);
            method.invoke(activity.getWindow(), notchFlag);
        } catch (Exception e) {
            LogUtils.i("xiaomi addExtraFlags not found.");
        }
    }

    @Override
    protected void disApplyNotch_O(Activity activity) {
        try {
            Method method = Window.class.getMethod("clearExtraFlags ",
                    int.class);
            method.invoke(activity.getWindow(), notchFlag);
        } catch (Exception e) {
            LogUtils.i("xiaomi clearExtraFlags not found.");
        }
    }

    @Override
    protected boolean isNotchEnable_O(Activity activity) {
        return isHardwareNotchEnable(activity);
    }

    protected boolean isHardwareNotchEnable(Activity activity) {
        try {
            Class<?> aClass = Class.forName("android.os.SystemProperties");
            Method getInt = aClass.getMethod("getInt", String.class, int.class);
            int invoke = (int) getInt.invoke(null, "ro.miui.notch", 0);
            if(invoke == 1){
                LogUtils.i("xiaomi hardware enable: true");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtils.i("xiaomi hardware enable: false");
        return false;
    }

    /**
     * 系统设置中是否开启了刘海区域使用
     * @param activity
     * @return
     */
    private boolean isSettingNotchEnable(Activity activity) {
        //系统设置是否支持刘海屏使用。0:开启，1:关闭
        boolean isNotchSettingOpen = Settings.Global.getInt(activity.getContentResolver(), "force_black", 0) == 0;
        return isNotchSettingOpen;
    }

    /**
     * app中是否开启了刘海区域使用
     * @param activity
     * @return
     */
    private boolean isSoftAppNotchEnable(Activity activity) {
        try {
            Field extraFlagsField = activity.getWindow().getAttributes().getClass().getField("extraFlags");
            extraFlagsField.setAccessible(true);
            int extraFlags = (int) extraFlagsField.get(activity.getWindow().getAttributes());
            if((extraFlags & notchFlag) == notchFlag){
                return true;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
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

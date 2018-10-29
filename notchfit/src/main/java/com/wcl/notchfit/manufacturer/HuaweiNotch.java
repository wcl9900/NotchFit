package com.wcl.notchfit.manufacturer;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.wcl.notchfit.core.AbstractNotch;
import com.wcl.notchfit.utils.LogUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 华为手机刘海屏参数获取
 * Created by wangchunlong on 2018/10/23.
 */
public class HuaweiNotch extends AbstractNotch {
    /*刘海屏全屏显示FLAG*/
    public static final int FLAG_NOTCH_SUPPORT=0x00010000;
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void applyNotch_O(Activity activity) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        try {
            Class layoutParamsExCls = Class.forName("com.huawei.android.view.LayoutParamsEx");
            Constructor con=layoutParamsExCls.getConstructor(WindowManager.LayoutParams.class);
            Object layoutParamsExObj=con.newInstance(layoutParams);
            Method method=layoutParamsExCls.getMethod("addHwFlags", int.class);
            method.invoke(layoutParamsExObj, FLAG_NOTCH_SUPPORT);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException
                | InvocationTargetException e) {
            Log.e("test", "hw add notch screen flag api error");
        } catch (Exception e) {
            Log.e("test", "other Exception");
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void disApplyNotch_O(Activity activity) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        try {
            Class layoutParamsExCls = Class.forName("com.huawei.android.view.LayoutParamsEx");
            Constructor con=layoutParamsExCls.getConstructor(WindowManager.LayoutParams.class);
            Object layoutParamsExObj=con.newInstance(layoutParams);
            Method method=layoutParamsExCls.getMethod("clearHwFlags ", int.class);
            method.invoke(layoutParamsExObj, FLAG_NOTCH_SUPPORT);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException
                | InvocationTargetException e) {
            LogUtils.i("huawei add notch screen flag api error");
        } catch (Exception e) {
            LogUtils.i("other Exception");
        }
    }

    @Override
    protected boolean isNotchEnable_O(Activity activity) {
        try {
            ClassLoader classLoader = activity.getClassLoader();
            Class HwNotchSizeUtil = classLoader.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("hasNotchInScreen");
            boolean notchEnable = (boolean) get.invoke(HwNotchSizeUtil);
            if(!notchEnable) return false;
        } catch (Exception e) {
            LogUtils.e("hasNotchAtHuawei ClassNotFoundException");
            return false;
        }

        //判断刘海屏系统设置开关是否打开“隐藏显示区域”
        String DISPLAY_NOTCH_STATUS = "display_notch_status";
        // 0表示“默认”，1表示“隐藏显示区域”
        int mIsNotchSwitchOpen = Settings.Secure.getInt(activity.getContentResolver(),DISPLAY_NOTCH_STATUS, 0);
        if(mIsNotchSwitchOpen == 1){
            return false;
        }
        return true;
    }

    @Override
    protected int[] getNotchSize_O(Activity activity) {
        int[] notchSize = new int[]{0, 0};
        try {
            ClassLoader cl = activity.getClassLoader();
            Class HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("getNotchSize");
            notchSize = (int[]) get.invoke(HwNotchSizeUtil);
        } catch (ClassNotFoundException e) {
            LogUtils.e( "getNotchSizeAtHuawei ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            LogUtils.e("getNotchSizeAtHuawei NoSuchMethodException");
        } catch (Exception e) {
            LogUtils.e("getNotchSizeAtHuawei Exception");
        } finally {
            return notchSize;
        }
    }
}

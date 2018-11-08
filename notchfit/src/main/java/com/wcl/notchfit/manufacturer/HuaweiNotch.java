package com.wcl.notchfit.manufacturer;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;

import com.wcl.notchfit.core.AbstractNotch;
import com.wcl.notchfit.utils.LogUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
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
        if(!isSettingNotchEnable(activity)) return;

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
            LogUtils.e("huawei add notch screen notchFlag api error");
        } catch (Exception e) {
            LogUtils.e("huawei other Exception");
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
            LogUtils.i("huawei add notch screen notchFlag api error");
        } catch (Exception e) {
            LogUtils.i("other Exception");
        }
    }

    @Override
    protected boolean isNotchEnable_O(Activity activity) {
        return isHardwareNotchEnable(activity) && isSettingNotchEnable(activity) && isSoftAppNotchEnable(activity);
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

    @Override
    protected void applyNotch_P(Activity activity) {
        if(!isSettingNotchEnable(activity)) return;
        super.applyNotch_P(activity);
    }

    @Override
    protected boolean isNotchEnable_P(Activity activity) {
        return super.isNotchEnable_P(activity) && isSettingNotchEnable(activity);
    }

    /**
     * 设备硬件是否是刘海屏。若设备无法获取属性值时，默认返回true，由其它条件做判断
     * @param activity
     * @return
     */
    private boolean isHardwareNotchEnable(Activity activity) {
        try {
            ClassLoader classLoader = activity.getClassLoader();
            Class HwNotchSizeUtil = classLoader.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("hasNotchInScreen");
            boolean notchEnable = (boolean) get.invoke(HwNotchSizeUtil);
            if(notchEnable) {
                LogUtils.i("huawei hardware enable: true");
                return true;
            }
        } catch (Exception e) {
            LogUtils.e("hasNotchAtHuawei ClassNotFoundException");
        }
        LogUtils.i("huawei hardware enable: false");
        return false;
    }

    /**
     * 系统设置中是否开启了刘海区域使用
     * @param activity
     * @return
     */
    private boolean isSettingNotchEnable(Activity activity) {
        //判断刘海屏系统设置开关是否关闭“隐藏显示区域”
        String DISPLAY_NOTCH_STATUS = "display_notch_status";
        // 0表示“默认开启”，1表示“隐藏显示区域”
        int mIsNotchSwitchOpen = Settings.Secure.getInt(activity.getContentResolver(),DISPLAY_NOTCH_STATUS, 0);
        boolean isSettingEnable = mIsNotchSwitchOpen == 0;
        LogUtils.i("huawei setting enable: "+isSettingEnable);
        if(isSettingEnable){
            return true;
        }
        return false;
    }

    /**
     * app是否开启了刘海区域使用
     * @param activity
     * @return
     */
    private boolean isSoftAppNotchEnable(Activity activity) {
        //优先判断华为手机程序控制，检查是否用用程序开启了刘海区域的使用
        try {
            Window window = activity.getWindow();
            Field hwFlagsField = window.getAttributes().getClass().getField("hwFlags");
            hwFlagsField.setAccessible(true);
            int hwFlags = (int) hwFlagsField.get(window.getAttributes());
            boolean isAppSoftEnable = (hwFlags & FLAG_NOTCH_SUPPORT) == FLAG_NOTCH_SUPPORT;
            LogUtils.i("huawei app soft enable:"+isAppSoftEnable);
            if (isAppSoftEnable) {
                return true;
            }
        }catch (Exception e){
            LogUtils.i("huawei " + e.getMessage());
        }
        return false;
    }

}

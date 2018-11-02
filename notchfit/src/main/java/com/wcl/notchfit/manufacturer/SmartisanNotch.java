package com.wcl.notchfit.manufacturer;

import android.app.Activity;

import com.wcl.notchfit.core.AbstractNotch;
import com.wcl.notchfit.utils.LogUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 锤子手机刘海屏
 * Created by wangchunlong on 2018/10/26.
 */
public class SmartisanNotch extends AbstractNotch {

    @Override
    protected boolean isNotchEnable_O(Activity activity) {
        return isHardwareNotchEnable(activity);
    }

    protected boolean isHardwareNotchEnable(Activity activity) {
        boolean supportNotch = false;
        try {
            Class<?> DisplayUtilsSmt = Class.forName("smartisanos.api.DisplayUtilsSmt");
            Method isFeatureSupport = DisplayUtilsSmt.getMethod("isFeatureSupport", int.class);
            supportNotch = (boolean) isFeatureSupport.invoke(DisplayUtilsSmt, 0x00000001);
            LogUtils.i("Smartisan hardware enable: " + supportNotch);
            return supportNotch;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        LogUtils.i("Smartisan hardware enable: " + supportNotch);
        return supportNotch;
    }

    @Override
    protected int[] getNotchSize_O(Activity activity) {
        return new int[]{
                82, //刘海宽 px
                104 //刘海高 px
        };
    }
}

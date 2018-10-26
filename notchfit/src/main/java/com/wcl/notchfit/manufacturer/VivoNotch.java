package com.wcl.notchfit.manufacturer;

import android.app.Activity;

import com.wcl.notchfit.core.AbstractNotch;
import com.wcl.notchfit.utils.LogUtils;
import com.wcl.notchfit.utils.SizeUtils;

import java.lang.reflect.Method;

/**
 * Vivo手机刘海屏
 * Created by wangchunlong on 2018/10/24.
 */

public class VivoNotch extends AbstractNotch {

    private final int VIVO_NOTCH = 0x00000020;//是否有刘海
    private final int VIVO_FILLET = 0x00000008;//是否有圆角
    @Override
    protected boolean isNotchEnable_O(Activity activity) {
        boolean notchEnable = false;
        try {
            ClassLoader classLoader = activity.getClassLoader();
            Class FtFeature = classLoader.loadClass("android.util.FtFeature");
            Method method = FtFeature.getMethod("isFeatureSupport", int.class);
            notchEnable = (boolean) method.invoke(FtFeature, VIVO_NOTCH) | (boolean) method.invoke(FtFeature, VIVO_FILLET);
        } catch (ClassNotFoundException e) {
            LogUtils.e("hasNotchAtVivo ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            LogUtils.e( "hasNotchAtVivo NoSuchMethodException");
        } catch (Exception e) {
            LogUtils.e("hasNotchAtVivo Exception");
        } finally {
            return notchEnable;
        }
    }

    @Override
    protected int[] getNotchSize_O(Activity activity) {
        int[] notchSize = new int[]{
                SizeUtils.dp2px(activity,100), //刘海宽度
                SizeUtils.dp2px(activity, 27) //刘海高度
        };
        return notchSize;
    }
}

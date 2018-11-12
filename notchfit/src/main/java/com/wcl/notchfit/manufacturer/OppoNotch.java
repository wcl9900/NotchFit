package com.wcl.notchfit.manufacturer;

import android.app.Activity;
import android.text.TextUtils;

import com.wcl.notchfit.core.AbstractNotch;
import com.wcl.notchfit.utils.DeviceUtils;
import com.wcl.notchfit.utils.LogUtils;
import com.wcl.notchfit.utils.SizeUtils;
import com.wcl.notchfit.utils.SystemProperties;

/**
 * OPPO手机刘海屏
 * Created by wangchunlong on 2018/10/24.
 */

public class OppoNotch extends AbstractNotch {
    @Override
    protected boolean isNotchEnable_O(Activity activity) {
        return isHardwareNotchEnable(activity);
    }

    private boolean isHardwareNotchEnable(Activity activity) {
        boolean isNotchEnable = activity.getPackageManager().hasSystemFeature("com.oppo.feature.screen.heteromorphism");
        LogUtils.i("OPPO hardware enable: "+isNotchEnable);
        return isNotchEnable;
    }

    /**
     * OPPO不提供接口获取刘海尺寸，目前其有刘海屏的机型尺寸规格都是统一的。不排除以后机型会有变化。
     *其显示屏宽度为1080px，高度为2280px。刘海区域则都是宽度为324px, 高度为80px;
     * int[0]值为刘海宽度 int[1]值为刘海高度
     */
    @Override
    protected int[] getNotchSize_O(Activity activity) {
        int[] notchSize = getNotchSizeFromSystemProperties();
        if(notchSize == null) {
            notchSize = new int[]{
                    324, //刘海宽度
                    SizeUtils.getStatusBarHeight(activity) //状态栏高度模拟刘海高度
            };
        }
        return notchSize;
    }

    /**
     * 通过OPPO 属性接口获取刘海参数
     *[ro.oppo.screen.heteromorphism]: [378,0:702,80]
     * @return
     */
    private int[] getNotchSizeFromSystemProperties(){
        String mNotchSize = SystemProperties.get("ro.oppo.screen.heteromorphism");
        if(TextUtils.isEmpty(mNotchSize)) return null;
        try {
            String[] split = mNotchSize.replace("[", "").replace("]", "").split(":");
            if(split.length == 2){
                String[] splitLeftTop = split[0].split(",");
                String[] splitRightBottom = split[1].split(",");
                int[] pointLeftTop = new int[]{Integer.parseInt(splitLeftTop[0]), Integer.parseInt(splitLeftTop[1])};
                int[] pointRightBottom = new int[]{Integer.parseInt(splitRightBottom[0]), Integer.parseInt(splitRightBottom[1])};
                int notchWidth = pointRightBottom[0] - pointLeftTop[0];
                int notchHeight = pointRightBottom[1] - pointLeftTop[1];
                return new int[]{notchWidth, notchHeight};
            }
        }catch (Exception e){
            LogUtils.e(DeviceUtils.getManufacturer() + " getNotchSizeFromSystemProperties: " + e.getMessage());
        }
        return null;
    }
}

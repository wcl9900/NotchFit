package com.wcl.notchfit.manufacturer;

import android.app.Activity;

import com.wcl.notchfit.core.AbstractNotch;

/**
 * OPPO手机刘海屏
 * Created by wangchunlong on 2018/10/24.
 */

public class OppoNotch extends AbstractNotch {
    @Override
    protected boolean isNotchEnable_O(Activity activity) {
        return activity.getPackageManager().hasSystemFeature("com.oppo.feature.screen.heteromorphism");
    }

    /**
     * OPPO不提供接口获取刘海尺寸，目前其有刘海屏的机型尺寸规格都是统一的。不排除以后机型会有变化。
     *其显示屏宽度为1080px，高度为2280px。刘海区域则都是宽度为324px, 高度为80px;
     * int[0]值为刘海宽度 int[1]值为刘海高度
     */
    @Override
    protected int[] getNotchSize_O(Activity activity) {
        int[] notchSize = new int[]{
                324, //刘海宽度
                80 //刘海高度
        };
        return notchSize;
    }
}

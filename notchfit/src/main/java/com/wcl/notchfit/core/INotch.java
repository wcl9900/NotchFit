package com.wcl.notchfit.core;

import android.app.Activity;

/**
 * 刘海屏参数获取等接口
 * Created by wangchunlong on 2018/10/23.
 */

public interface INotch {
    /**
     * 刘海区域使用开关。
     * <p>对于某些设备虽然硬件是刘海屏，但需要软件控制开启才能使用（如小米、华为需要在AndroidManifest中配置属性或通过代码控制）
     * ，所以通过此接口代码控制刘海区域的使用
     * @param support 设定是否支持刘海区域使用
     * @param activity true:使用刘海区域；false:不使用刘海区域
     */
    void applyNotch(Activity activity, boolean support);
    /**
     * 获取刘海参数数据
     * @param activity
     * @param onNotchCallBack 刘海参数回调
     */
    void obtainNotch(Activity activity, OnNotchCallBack onNotchCallBack);
}

package com.wcl.notchfit.core;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.view.DisplayCutout;
import android.view.WindowManager;

import com.wcl.notchfit.args.NotchProperty;
import com.wcl.notchfit.utils.DeviceUtils;

import java.util.List;

/**
 * 刘海屏尺寸抽象类，用来判断是否存在刘海屏并获取刘海屏尺寸参数等，
 * 不同设备可继承该抽象类完成相对应刘海屏参数获取逻辑的处理
 * Created by wangchunlong on 2018/10/23.
 */
public abstract class AbstractNotch implements INotch{
    private NotchProperty notchProperty;

    @Override
    public void applyNotch(Activity activity, boolean applyEnable) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            if(applyEnable) {
                applyNotch_P(activity);
            }
            else {
                disApplyNotch_P(activity);
            }
        }
        else {
            if(applyEnable){
                applyNotch_O(activity);
            }
            else {
                disApplyNotch_O(activity);
            }
        }
    }

    /**
     * O版本及以前版本开启支持刘海屏，只负责不同设备厂商刘海区域使用开关的启动
     * @param activity
     */
    protected void applyNotch_O(Activity activity){}
    /**
     * O版本及以前版本关闭支持刘海屏，只负责不同设备厂商刘海区域使用开关的关闭
     * @param activity
     */
    protected void disApplyNotch_O(Activity activity){}

    /**
     *P版本及以后版本通过设定layoutInDisplayCutoutMode参数类型开启使用刘海区域
     * @param activity
     */
    protected void applyNotch_P(Activity activity){
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        activity.getWindow().setAttributes(lp);
    }
    /**
     *P版本及以后版本设定layoutInDisplayCutoutMode参数类型关闭使用刘海区域
     * @param activity
     */
    protected void disApplyNotch_P(Activity activity){
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER;
        activity.getWindow().setAttributes(lp);
    }

    /**
     * 获取刘海屏参数
     * @param activity
     */
    @Override
    public void obtainNotch(final Activity activity, final OnNotchCallBack onNotchCallBack) {
        if(notchProperty != null){
            notchProperty = null;
        }
        notchProperty = new NotchProperty();
        notchProperty.setManufacturer(DeviceUtils.getManufacturer());

        activity.getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                int[] notchSize = null;
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
                    notchProperty.setNotchEnable(isNotchEnable_P(activity));
                    if(notchProperty.isNotchEnable()) {
                        notchSize = getNotchSize_P(activity);
                    }
                }
                else {
                    notchProperty.setNotchEnable(isNotchEnable_O(activity));
                    if(notchProperty.isNotchEnable()) {
                        notchSize = getNotchSize_O(activity);
                    }
                }

                if(notchProperty.isNotchEnable()){
                    if(notchSize == null || notchSize.length != 2) {
                        throw new RuntimeException("刘海屏尺寸数据获取有误");
                    }
                    notchProperty.setNotchWidth(notchSize[0]);
                    notchProperty.setNotchHeight(notchSize[1]);
                }
                if(onNotchCallBack != null){
                    onNotchCallBack.onNotchReady(notchProperty);
                }
            }
        });
    }

    /**
     * 判断获取O版本及以前版本的设备是否显示刘海屏
     * @param activity
     * @return
     */
    protected abstract boolean isNotchEnable_O(Activity activity);
    /**
     * 获取O版本及以前版本的非谷歌标准的刘海屏宽高尺寸
     * @param activity
     * @return 返回刘海屏尺寸数组，int[0]：刘海屏宽度，int[1]：刘海屏高度
     */
    protected abstract int[] getNotchSize_O(Activity activity);

    /**
     * 判断获取P版本及以后版本的设备是否显示刘海屏
     * @param activity
     * @return
     */
    @TargetApi(Build.VERSION_CODES.P)
    protected boolean isNotchEnable_P(Activity activity){
        return isNotchEnableDefault_P(activity);
    }
    /**
     * 获取谷歌标准P版本及以上的刘海屏宽高尺寸
     * @param activity
     * @return 返回刘海屏尺寸数组，int[0]：刘海屏宽度，int[1]：刘海屏高度
     */
    @TargetApi(Build.VERSION_CODES.P)
    protected int[] getNotchSize_P(Activity activity){
        return getNotchSizeDefault_P(activity);
    }

    /**
     * P版本标准api判断设备是否显示刘海屏
     * @param activity
     * @return
     */
    @TargetApi(Build.VERSION_CODES.P)
    private boolean isNotchEnableDefault_P(Activity activity){
        DisplayCutout displayCutout = activity.getWindow().getDecorView().getRootWindowInsets().getDisplayCutout();
        if(displayCutout == null || displayCutout.getBoundingRects() == null || displayCutout.getBoundingRects().size() == 0){
            return false;
        }
        return true;
    }

    /**
     * 获取google标准P版本及以上刘海屏尺寸
     * @param activity
     * @return
     */
    @TargetApi(Build.VERSION_CODES.P)
    private int[] getNotchSizeDefault_P(Activity activity){
        int[] notchSize = new int[]{0,0};
        DisplayCutout displayCutout = activity.getWindow().getDecorView().getRootWindowInsets().getDisplayCutout();
        List<Rect> boundingRects = displayCutout.getBoundingRects();
        if(boundingRects.size() != 0){
            Rect rect = boundingRects.get(0);
            notchSize[0] = rect.width();
            notchSize[1] = rect.height();
        }
        return notchSize;
    }
}

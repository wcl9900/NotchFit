package com.wcl.notchfit;

import android.app.Activity;
import android.app.Application;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.wcl.notchfit.args.NotchPosition;
import com.wcl.notchfit.args.NotchProperty;
import com.wcl.notchfit.args.NotchScreenType;
import com.wcl.notchfit.core.NotchFactory;
import com.wcl.notchfit.core.OnNotchCallBack;
import com.wcl.notchfit.position.NotchPositionWrapper;
import com.wcl.notchfit.utils.ActivityUtils;
import com.wcl.notchfit.utils.LogUtils;

/**
 * 刘海屏适配
 * Created by wangchunlong on 2018/10/24.
 */

public class NotchFit {
    /**
     * 通过设定Activity活动窗口显示样式，使用并延伸布局至刘海区域
     * ，便可通过回调函数获取刘海参数用来给布局做适配
     * @param activity 需要适配的活动窗口页
     * @param notchScreenType 适配刘海区域的屏幕显示样式。
     * <p>{@link NotchScreenType#FULL_SCREEN}:全屏刘海;
     * <p>{@link NotchScreenType#TRANSLUCENT}:沉浸式刘海
     * <p>{@link NotchScreenType#CUSTOM}:用户自定义Activity显示样式
     * @param onNotchCallBack 刘海参数获取回调接口
     */
    public static void fit(final Activity activity, final NotchScreenType notchScreenType, final OnNotchCallBack onNotchCallBack){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return;
        }

        if(notchScreenType != NotchScreenType.CUSTOM) {
            NotchFactory.getInstance().getNotch().applyNotch(activity, true);
        }

        if(notchScreenType == NotchScreenType.FULL_SCREEN){
            ActivityUtils.setFullScreen(activity);
        }
        else if(notchScreenType == NotchScreenType.TRANSLUCENT){
            ActivityUtils.setTranslucent(activity);
        }

        NotchPositionWrapper notchPositionWrapper = new NotchPositionWrapper();
        notchPositionWrapper.onPosition(activity, new NotchPositionWrapper.OnPositionListener() {
            @Override
            public void onPosition(final NotchPosition notchPosition) {
                NotchFactory.getInstance().getNotch().obtainNotch(activity, new OnNotchCallBack() {
                    @Override
                    public void onNotchReady(NotchProperty notchProperty) {
                        if(notchProperty.isNotchEnable()){
                            notchProperty.setNotchPosition(notchPosition);
                            filterSystemFitAuto(activity, notchProperty);
                        }
                        if(onNotchCallBack != null){
                            onNotchCallBack.onNotchReady(notchProperty);
                        }
                    }
                });
            }
        });
    }
    /**
     * 判断系统是否已完成刘海自动适配，过滤重设刘海参数
     * @param activity
     * @param notchProperty
     */
    private static void filterSystemFitAuto(Activity activity, NotchProperty notchProperty){
        View rootView = activity.getWindow().getDecorView().getRootView();
        if(rootView != null) {
            int[] rootViewLocation = new int[2];
            rootView.getLocationOnScreen(rootViewLocation);
            if (rootViewLocation[1] >= notchProperty.getNotchHeight()) {//通过Y坐标判断设备是否自动完成了适配
                notchProperty.setNotchEnable(false);
                notchProperty.setNotchWidth(0);
                notchProperty.setNotchHeight(0);
                LogUtils.i(notchProperty.getManufacturer() + " fit notch finish by system(系统自动完成刘海适配)");
            }
        }
    }

    /**
     * 刘海参数回调获取，可在回调接口中进行UI的刘海适配
     * @param activity 需要刘海UI适配的活动窗口
     * @param onNotchCallBack 刘海参数回调接口
     */
    public static void fit(Activity activity, OnNotchCallBack onNotchCallBack){
        fit(activity,NotchScreenType.CUSTOM, onNotchCallBack);
    }

    /**
     * 对可以通过app控制刘海可使用权限的设备（如小米、华为），控制app是否开启使用刘海区域。
     * @param activity
     * @param applyEnable 是否启用刘海区域
     */
    public static void applyNotch(Activity activity, boolean applyEnable){
        NotchFactory.getInstance().getNotch().applyNotch(activity, applyEnable);
    }

    /**
     * 对有UI布局延伸到刘海区域的全屏活动窗口，可使窗口布局不使用刘海区域，用黑条填充刘海区域
     * @param activity 需要刘海适配的全屏活动窗口
     */
    public static void fitUnUse(final Activity activity){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return;
        }
        fit(activity, NotchScreenType.CUSTOM, new OnNotchCallBack() {
            @Override
            public void onNotchReady(NotchProperty notchProperty) {
                if(notchProperty.isNotchEnable() && notchProperty.getNotchHeight() != 0){
                    //不是全屏不适配
                    if(!ActivityUtils.isFullScreen(activity)) return;
                    //已经适配过的无需再适配
                    if(activity.findViewById(R.id.custom_notch_view) != null) return;

                    int fitSize = notchProperty.getNotchHeight();

                    View contentRootView = ActivityUtils.getContentRootView(activity);
                    ((ViewGroup.MarginLayoutParams)contentRootView.getLayoutParams()).topMargin += fitSize;

                    ViewGroup windowRootView = (ViewGroup) activity.getWindow().getDecorView().getRootView();
                    if(windowRootView != null && windowRootView instanceof FrameLayout) {
                        View notchView = new View(activity);
                        notchView.setId(R.id.custom_notch_view);
                        notchView.setBackgroundColor(Color.BLACK);
                        FrameLayout.LayoutParams notchViewLayoutParams =
                                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, fitSize);
                        notchViewLayoutParams.gravity = Gravity.TOP;
                        windowRootView.addView(notchView, notchViewLayoutParams);
                        LogUtils.i(notchProperty.getManufacturer() + " notch fit finish by app (程序完成适配) ");
                    }
                }
            }
        });
    }

}

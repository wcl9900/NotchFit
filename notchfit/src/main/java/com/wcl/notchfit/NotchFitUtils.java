package com.wcl.notchfit;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 刘海屏适配
 * Created by wangchunlong on 2018/9/21.
 */
public class NotchFitUtils {
    private static Map<String, Boolean> notchSupportMap = new HashMap<>(1);

    public static void fit(Activity activity){
        if(!isFullScreen(activity)) return;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT;
            activity.getWindow().setAttributes(lp);
            return;
        }

        String manufacturer = DeviceUtils.getManufacturer().toLowerCase();
        boolean notchSupportEnable;
        if(TextUtils.equals(manufacturer, "huawei")){
            if(!notchSupportMap.containsKey("huawei")){
                notchSupportEnable = hasNotchAtHuawei(activity);
                notchSupportMap.put("huawei", notchSupportEnable);
            }
            notchSupportEnable = notchSupportMap.get("huawei");
//            notchSupportEnable = true;
            if(notchSupportEnable){
                setNotchViewSize(activity, getNotchSizeAtHuawei(activity));
            }
        }
        else if(TextUtils.equals(manufacturer, "xiaomi")){
            if(!notchSupportMap.containsKey("xiaomi")){
                notchSupportEnable = hasNotchAtXiaomi(activity);
                notchSupportMap.put("xiaomi", notchSupportEnable);
            }
            notchSupportEnable = notchSupportMap.get("xiaomi");
            if(notchSupportEnable){
                setNotchViewSize(activity, getNotchSizeAtXiaomi(activity));
            }
        }
        else if(TextUtils.equals(manufacturer, "vivo")){
            if(!notchSupportMap.containsKey("vivo")){
                notchSupportEnable = hasNotchAtVivo(activity);
                notchSupportMap.put("vivo", notchSupportEnable);
            }
            notchSupportEnable = notchSupportMap.get("vivo");
            if(notchSupportEnable){
                setNotchViewSize(activity, getNotchSizeAtVivo(activity));
            }
        }
        else if(TextUtils.equals(manufacturer, "oppo")){
            if(!notchSupportMap.containsKey("oppo")){
                notchSupportEnable = hasNotchAtOPPO(activity);
                notchSupportMap.put("oppo", notchSupportEnable);
            }
            notchSupportEnable = notchSupportMap.get("oppo");
            if(notchSupportEnable){
                setNotchViewSize(activity, getNotchSizeAtOPPO(activity));
            }
        }
    }

    private static boolean isFullScreen(Activity activity){
        if ( (activity.getWindow().getAttributes().flags & WindowManager.LayoutParams.FLAG_FULLSCREEN)
                == WindowManager.LayoutParams.FLAG_FULLSCREEN) {
            // 是全屏
            return true;
        }
        return false;
    }
    /**
     *
     * @param activity
     * @param notchSize 刘海区域尺寸，[0]：刘海宽度；[1]：为刘海高度
     */
    private static void setNotchViewSize(final Activity activity, final int[] notchSize) {
        final FrameLayout windowContentView = activity.findViewById(android.R.id.content);

        View notchView = windowContentView.findViewById(R.id.custom_notch_view);
        if(notchView != null) return;

        setNotchView(windowContentView, notchSize);
    }

    private static void setNotchView(final ViewGroup windowContentView, final int[] notchSize){
        ViewGroup parent = (ViewGroup) windowContentView.getParent();
        if(parent != null){
            setNotchView(parent, notchSize);
        }
        else {
            windowContentView.post(new Runnable() {
                @Override
                public void run() {
                    int childCount = windowContentView.getChildCount();
                    for (int index = 0; index < childCount; index++) {
                        View contentView = windowContentView.getChildAt(index);

                        View notchView = new View(windowContentView.getContext());
                        notchView.setBackgroundColor(Color.BLACK);
                        FrameLayout.LayoutParams notchViewLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, notchSize[1]);
                        notchViewLayoutParams.gravity = Gravity.TOP;

                        ViewGroup.MarginLayoutParams contentLayoutParams = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
                        contentLayoutParams.topMargin = notchSize[1];
                        windowContentView.addView(notchView, notchViewLayoutParams);
                    }
                }
            });
        }
    }
    private static float actionBarSize = -1;
    private static float getActionBarHeight(Context context){
        if(actionBarSize != -1) return actionBarSize;
        TypedArray actionbarSizeTypedArray = context.obtainStyledAttributes(new int[] {
                android.R.attr.actionBarSize
        });
        actionBarSize = actionbarSizeTypedArray.getDimension(0, SizeUtils.dp2px(context, 48));
        return actionBarSize;
    }
    /**
     * 获取华为手机是否有刘海屏
     * @param context
     * @return
     */
    private static boolean hasNotchAtHuawei(Context context) {
        boolean ret = false;

        String notchSupportKey = "android.notch_support";
        Boolean activityMetaDataSupportEnable = getActivityMetaData((Activity) context, notchSupportKey);
        if(activityMetaDataSupportEnable == null || !activityMetaDataSupportEnable){
            Boolean applicationMetaDataSupportEnable = getApplicationMetaData(context, notchSupportKey);
            if(applicationMetaDataSupportEnable == null || !applicationMetaDataSupportEnable){
                return false;
            }
        }

        try {
            ClassLoader classLoader = context.getClassLoader();
            Class HwNotchSizeUtil = classLoader.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("hasNotchInScreen");
            ret = (boolean) get.invoke(HwNotchSizeUtil);
        } catch (ClassNotFoundException e) {
            Log.e("Notch", "hasNotchAtHuawei ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            Log.e("Notch", "hasNotchAtHuawei NoSuchMethodException");
        } catch (Exception e) {
            Log.e("Notch", "hasNotchAtHuawei Exception");
        } finally {
            return ret;
        }
    }
    /**
     * 获取华为手机刘海尺寸：width、height
     * int[0]值为刘海宽度 int[1]值为刘海高度
     * @param context
     * @return
     */
    private static int[] getNotchSizeAtHuawei(Context context) {
        int[] ret = new int[]{0, 0};
        try {
            ClassLoader cl = context.getClassLoader();
            Class HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("getNotchSize");
            ret = (int[]) get.invoke(HwNotchSizeUtil);
        } catch (ClassNotFoundException e) {
            Log.e("Notch", "getNotchSizeAtHuawei ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            Log.e("Notch", "getNotchSizeAtHuawei NoSuchMethodException");
        } catch (Exception e) {
            Log.e("Notch", "getNotchSizeAtHuawei Exception");
        } finally {
            return ret;
        }
    }


    public static final int VIVO_NOTCH = 0x00000020;//是否有刘海
    public static final int VIVO_FILLET = 0x00000008;//是否有圆角
    /**
     * 判断Vivo手机是否有刘海屏
     * @param context
     * @return
     */
    private static boolean hasNotchAtVivo(Context context) {
        boolean ret = false;
        try {
            ClassLoader classLoader = context.getClassLoader();
            Class FtFeature = classLoader.loadClass("android.util.FtFeature");
            Method method = FtFeature.getMethod("isFeatureSupport", int.class);
            ret = (boolean) method.invoke(FtFeature, VIVO_NOTCH) | (boolean) method.invoke(FtFeature, VIVO_FILLET);
        } catch (ClassNotFoundException e) {
            Log.e("Notch", "hasNotchAtVivo ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            Log.e("Notch", "hasNotchAtVivo NoSuchMethodException");
        } catch (Exception e) {
            Log.e("Notch", "hasNotchAtVivo Exception");
        } finally {
            return ret;
        }
    }

    /**
     * vivo不提供接口获取刘海尺寸，目前vivo的刘海宽为100dp,高为27dp;
     * int[0]值为刘海宽度 int[1]值为刘海高度
     * @param context
     * @return
     */
    private static int[] getNotchSizeAtVivo(Context context){
        int[] notchSize = new int[]{
                SizeUtils.dp2px(context,100), //刘海宽度
                SizeUtils.dp2px(context, 27) //刘海高度
        };
        return notchSize;
    }

    /**
     * 判断OPPO手机是否有刘海屏
     * @param context
     * @return
     */
    private static boolean hasNotchAtOPPO(Context context) {
        return context.getPackageManager().hasSystemFeature("com.oppo.feature.screen.heteromorphism");
    }

    /**
     * OPPO不提供接口获取刘海尺寸，目前其有刘海屏的机型尺寸规格都是统一的。不排除以后机型会有变化。
     *其显示屏宽度为1080px，高度为2280px。刘海区域则都是宽度为324px, 高度为80px;
     * int[0]值为刘海宽度 int[1]值为刘海高度
     * @param context
     * @return
     */
    private static int[] getNotchSizeAtOPPO(Context context){
        int[] notchSize = new int[]{
                324, //刘海宽度
                80 //刘海高度
        };
        return notchSize;
    }

    /**
     * 判断x小米手机是否有刘海屏；app要使用刘海屏耳朵区域需在application标签中配置<meta-data android:name="notch.config" android:value="portrait|landscape"/>，
     * 当前模块没有配置使用耳朵区域，所以不做小米刘海屏兼容处理
     * @param context
     * @return
     */
    private static boolean hasNotchAtXiaomi(Context context) {
        String notchConfigKey = "notch.config";
        String activityMetaData = getActivityMetaData((Activity) context, notchConfigKey);
        if(TextUtils.isEmpty(activityMetaData)){
            String applicationMetaData = getApplicationMetaData(context, notchConfigKey);
            if(TextUtils.isEmpty(applicationMetaData)) return false;
        }

        try {
            Class<?> aClass = Class.forName("android.os.SystemProperties");
            Method getInt = aClass.getMethod("getInt", String.class, int.class);
            int invoke = (int) getInt.invoke(null, "ro.miui.notch", 0);
            return invoke == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * int[0]值为刘海宽度 int[1]值为刘海高度
     * @param context
     * @return
     */
    private static int[] getNotchSizeAtXiaomi(Context context){
        int[] notchSize = new int[]{
                0,//暂时未获取刘海宽度
                getStatusBarHeight(context) //可通过获取状态栏高度模拟刘海高度
        };
        return notchSize;
    }
    /**
     * 获取手机状态栏高度
     * @param context
     * @return
     */
    private static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    private static <T> T getActivityMetaData(Activity context, String metaKey){
        try {
            ActivityInfo activityInfo = context.getPackageManager().getActivityInfo(context.getComponentName(), PackageManager.GET_META_DATA);
            if(activityInfo.metaData != null) {
                Object value = activityInfo.metaData.get(metaKey);
                return (T) value;
            }
            return null;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static <T> T getApplicationMetaData(Context context, String metaKey){
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if(applicationInfo.metaData != null) {
                Object value = applicationInfo.metaData.get(metaKey);
                return (T) value;
            }
            return null;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}

package com.wcl.notchfit.position;

import android.app.Activity;
import android.hardware.SensorManager;
import android.view.OrientationEventListener;
import android.view.Surface;

import com.wcl.notchfit.args.NotchPosition;

/**
 * 刘海屏位置获取包装器
 * Created by wangchunlong on 2021/06/06.
 */
public class NotchPositionWrapper {
    //传感器的旋转角度
    private int mOrientationDegree = -1;
    //屏幕视图真实的旋转角度
    private int mSurfaceOrientation = -1;
    //是否已经获取activity创建时的方向，避免和方向回调相互干扰
    private boolean hasGetOrientation = false;

    public interface OnPositionListener{
        void onPosition(NotchPosition notchPosition);
    }

    /**
     * 包装获取刘海所在屏幕位置
     * @param activity
     * @param onPositionListener
     */
    public void onPosition(final Activity activity, final OnPositionListener onPositionListener){
        final OrientationEventListener orientationEventListener = new OrientationEventListener(activity, SensorManager.SENSOR_DELAY_NORMAL) {
            @Override
            public void onOrientationChanged(int orientationDegree) {
                if (orientationDegree == OrientationEventListener.ORIENTATION_UNKNOWN) {
                    return;
                }

                final int newOrientationDegree = ((orientationDegree + 45) / 90 * 90) % 360;
                final int newOrientation = getSurfaceRotation(activity);

                if (newOrientationDegree != mOrientationDegree && newOrientation != mSurfaceOrientation) {
                    mOrientationDegree = newOrientationDegree;
                    mSurfaceOrientation = newOrientation;
                    if(onPositionListener != null && hasGetOrientation){
                         notifyPosition(mOrientationDegree, onPositionListener);
                    }
                }
            }
        };

        if(orientationEventListener.canDetectOrientation()){
            orientationEventListener.enable();
        }

        //activity新创建时根据横竖屏获取初始刘海位置
        if(mOrientationDegree == -1){
            mOrientationDegree = getSurfaceRotation(activity);
            mSurfaceOrientation = mOrientationDegree;
        }
        notifyPosition(mOrientationDegree, onPositionListener);
        hasGetOrientation = true;

        //注册activity生命周期监听，用来释放方向监听器
        activity.getApplication().registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacksAdapter(){
            @Override
            public void onActivityDestroyed(Activity _activity) {
                if(activity != _activity){
                    return;
                }
                orientationEventListener.disable();
                activity.getApplication().unregisterActivityLifecycleCallbacks(this);
            }
        });
    }

    private void notifyPosition(int orientation, OnPositionListener onPositionListener){
        switch (orientation){
            case 0:
                onPositionListener.onPosition(NotchPosition.TOP);
                break;
            case 90:
                onPositionListener.onPosition(NotchPosition.LEFT);
                break;
            case 270:
                onPositionListener.onPosition(NotchPosition.RIGHT);
                break;
        }
    }

    /**
     * 获取屏幕视图实际的偏转角度
     * @param activity
     * @return
     */
    private int getSurfaceRotation(Activity activity){
        int surfaceRotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        switch (surfaceRotation){
            case Surface.ROTATION_0:
                return 0;
            case Surface.ROTATION_90:
                return 90;
            case Surface.ROTATION_180:
                return 180;
            case Surface.ROTATION_270:
                return 270;
        }
        return 0;
    }
}

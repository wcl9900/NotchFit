package com.wcl.notchfit.core;

import android.text.TextUtils;

import com.wcl.notchfit.manufacturer.GoogleNotch;
import com.wcl.notchfit.manufacturer.HuaweiNotch;
import com.wcl.notchfit.manufacturer.OppoNotch;
import com.wcl.notchfit.manufacturer.SmartisanNotch;
import com.wcl.notchfit.manufacturer.VivoNotch;
import com.wcl.notchfit.manufacturer.XiaomiNotch;
import com.wcl.notchfit.utils.DeviceUtils;

/**
 * Notch设备参数获取工厂
 * Created by wangchunlong on 2018/10/24.
 */

public class NotchFactory {
    private static NotchFactory instance;

    private NotchFactory(){}
    public static NotchFactory getInstance(){
        if(instance == null){
            synchronized (NotchFactory.class) {
                instance = new NotchFactory();
            }
        }
        return instance;
    }

    INotch notch;
    public INotch getNotch(){
        if(notch != null){
            return notch;
        }
        String manufacturer = DeviceUtils.getManufacturer().toLowerCase();
        if(TextUtils.equals(manufacturer, "huawei")){
            notch = new HuaweiNotch();
        }
        else if(TextUtils.equals(manufacturer, "xiaomi")){
            notch = new XiaomiNotch();
        }
        else if(TextUtils.equals(manufacturer, "oppo")){
            notch = new OppoNotch();
        }
        else if(TextUtils.equals(manufacturer, "vivo")){
            notch = new VivoNotch();
        }
        else if(TextUtils.equals(manufacturer, "smartisan")){
            notch = new SmartisanNotch();
        }
        else {
            notch = new GoogleNotch();
        }
        return notch;
    }
}

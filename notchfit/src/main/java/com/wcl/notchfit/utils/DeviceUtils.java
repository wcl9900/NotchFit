package com.wcl.notchfit.utils;

import android.os.Build;

/**
 * 设备工具类
 */
public final class DeviceUtils {

    /**
     * Return the manufacturer of the product/hardware.
     * <p>e.g. Xiaomi</p>
     *
     * @return the manufacturer of the product/hardware
     */
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }
}

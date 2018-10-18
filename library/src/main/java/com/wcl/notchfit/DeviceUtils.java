package com.wcl.notchfit;

import android.os.Build;

/**
 * 设备工具类
 */
final class DeviceUtils {

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

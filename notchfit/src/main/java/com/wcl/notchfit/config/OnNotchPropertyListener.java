package com.wcl.notchfit.config;

import com.wcl.notchfit.args.NotchProperty;

/**可通过{@link NotchConfig} 配置Notch属性回调监听器，用来测试展示Notch属性信息
 * Created by wangchunlong on 2018/10/29.
 */

public interface OnNotchPropertyListener {
    /**
     * 回调获取Notch参数
     * @param notchProperty
     */
    void onNotchProperty(NotchProperty notchProperty);
}

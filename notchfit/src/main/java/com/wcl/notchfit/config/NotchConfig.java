package com.wcl.notchfit.config;

/**
 * Notch配置
 * Created by wangchunlong on 2018/10/29.
 */

public class NotchConfig {
    /**
     * 刘海参数属性获取回调全局监听
     */
    public static OnNotchPropertyListener NotchPropertyListener;
    /**
     * R 资源id文件包名路径，框架需要读取R资源id值，若AndroidManifest中package属性与applicationId不一致，可通过此变量配置R路径
     */
    public static String R_PackagePath;
}

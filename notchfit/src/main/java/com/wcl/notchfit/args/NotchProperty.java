package com.wcl.notchfit.args;

/**
 * 刘海屏参数数据实体
 * Created by wangchunlong on 2018/10/23.
 */

public class NotchProperty {
    private String manufacturer; //手机厂商
    private boolean notchEnable; //是否显示刘海屏
    private int notchWidth;           //刘海屏宽度px
    private int notchHeight;          //刘海屏高度px

    /**
     * 设备厂商
     * @return
     */
    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * 判断设备是否支持刘海屏并且窗口自定义布局已经延伸到刘海区域
     * @return
     */
    public boolean isNotchEnable() {
        return notchEnable;
    }

    public void setNotchEnable(boolean notchEnable) {
        this.notchEnable = notchEnable;
    }

    /**
     * 刘海宽度 px
     * @return
     */
    public int getNotchWidth() {
        return notchWidth;
    }

    public void setNotchWidth(int notchWidth) {
        this.notchWidth = notchWidth;
    }

    /**
     * 刘海高度 px
     * @return
     */
    public int getNotchHeight() {
        return notchHeight;
    }

    public void setNotchHeight(int notchHeight) {
        this.notchHeight = notchHeight;
    }

    @Override
    public String toString() {
        return "notchEnable: "+isNotchEnable()+" "
                +"notchWidth: "+getNotchWidth() + " "
                +"notchHeight: " + getNotchHeight() + " "
                +"manufacturer: "+ getManufacturer();
    }
}

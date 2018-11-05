package com.wcl.notchfit.demo;

import android.os.Bundle;

import com.wcl.notchfit.NotchFit;
import com.wcl.notchfit.utils.ActivityUtils;

/**
 * 沉浸式不适配刘海
 */
public class UnUseNotchTranslucentActivity extends NotchBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //开启沉浸模式
        ActivityUtils.setTranslucent(this);
//        StatusBarUtil.setTranslucent(this, 0);

        NotchFit.fitUnUse(this);
    }
}

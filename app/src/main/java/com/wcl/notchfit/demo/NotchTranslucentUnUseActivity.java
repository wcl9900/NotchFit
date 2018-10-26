package com.wcl.notchfit.demo;

import android.os.Bundle;

import com.wcl.notchfit.NotchFit;
import com.wcl.notchfit.utils.ActivityUtils;

/**
 * 沉浸式不适配刘海
 */
public class NotchTranslucentUnUseActivity extends NotchBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //开启沉浸模式
        ActivityUtils.setTranslucent(this);

        NotchFit.fitUnUse(this);
    }
}

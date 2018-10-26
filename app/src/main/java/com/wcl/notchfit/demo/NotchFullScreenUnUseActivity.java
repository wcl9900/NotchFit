package com.wcl.notchfit.demo;

import android.os.Bundle;

import com.wcl.notchfit.NotchFit;
import com.wcl.notchfit.utils.ActivityUtils;

/**
 * 全屏不适配刘海
 */
public class NotchFullScreenUnUseActivity extends NotchBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //开启全屏模式
        ActivityUtils.setFullScreen(this);

        NotchFit.fitUnUse(this);
    }
}

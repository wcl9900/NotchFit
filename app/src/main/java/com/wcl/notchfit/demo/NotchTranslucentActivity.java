package com.wcl.notchfit.demo;

import android.os.Bundle;
import android.view.ViewGroup;

import com.wcl.notchfit.NotchFit;
import com.wcl.notchfit.args.NotchPosition;
import com.wcl.notchfit.args.NotchProperty;
import com.wcl.notchfit.args.NotchScreenType;
import com.wcl.notchfit.core.OnNotchCallBack;
import com.wcl.notchfit.utils.LogUtils;
import com.wcl.notchfit.utils.SizeUtils;

/**
 * 沉浸式刘海适配
 */
public class NotchTranslucentActivity extends NotchBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ViewGroup listParent = findViewById(R.id.ll_parent);
        NotchFit.fit(this, NotchScreenType.TRANSLUCENT, new OnNotchCallBack() {
            @Override
            public void onNotchReady(NotchProperty notchProperty) {
                NotchTranslucentActivity.this.fixLayout(notchProperty, false);
            }
        });
    }
}

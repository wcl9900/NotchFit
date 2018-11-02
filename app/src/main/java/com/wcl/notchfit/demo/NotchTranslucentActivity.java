package com.wcl.notchfit.demo;

import android.os.Bundle;
import android.view.ViewGroup;

import com.wcl.notchfit.NotchFit;
import com.wcl.notchfit.args.NotchProperty;
import com.wcl.notchfit.args.NotchScreenType;
import com.wcl.notchfit.core.OnNotchCallBack;

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
                if(notchProperty.isNotchEnable()){
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) listParent.getLayoutParams();
                    marginLayoutParams.topMargin = notchProperty.getNotchHeight();
                    listParent.requestLayout();
                }
            }
        });
    }
}

package com.wcl.notchfit.demo;

import android.os.Bundle;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.wcl.notchfit.NotchFit;
import com.wcl.notchfit.args.NotchProperty;
import com.wcl.notchfit.core.OnNotchCallBack;

public class NotchCustomActivity extends NotchBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //用户自己定义显示样式
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        //支持刘海使用
        NotchFit.applyNotch(this, true);

        NotchFit.fit(this, new OnNotchCallBack() {
            @Override
            public void onNotchReady(NotchProperty notchProperty) {
                NotchCustomActivity.this.fixLayout(notchProperty, false);
            }
        });
    }
}

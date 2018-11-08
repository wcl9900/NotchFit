package com.wcl.notchfit.demo;

import android.os.Bundle;

import com.wcl.notchfit.utils.ActivityUtils;

public class NotchCustomActivity extends NotchBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityUtils.setFullScreen(this);

//        //用户自己定义显示样式
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

//        //支持刘海使用
//        NotchFit.applyNotch(this, true);
//
//        final ViewGroup listParent = findViewById(R.id.ll_parent);
//        NotchFit.fit(this, new OnNotchCallBack() {
//            @Override
//            public void onNotchReady(NotchProperty notchProperty) {
//                if(notchProperty.isNotchEnable()){
//                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) listParent.getLayoutParams();
//                    marginLayoutParams.topMargin = notchProperty.getNotchHeight();
//                    listParent.requestLayout();
//                }
//            }
//        });
    }
}

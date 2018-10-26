package com.wcl.notchfit.demo;

import android.os.Bundle;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.wcl.notchfit.NotchFit;
import com.wcl.notchfit.args.NotchProperty;
import com.wcl.notchfit.args.NotchScreenType;
import com.wcl.notchfit.core.OnNotchCallBack;

public class NotchCustomActivity extends NotchBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //用户自己定义显示样式
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        final ViewGroup listParent = findViewById(R.id.ll_parent);
        NotchFit.fit(this, NotchScreenType.CUSTOM, new OnNotchCallBack() {
            @Override
            public void onNotchReady(NotchProperty notchProperty) {
                Toast.makeText(getApplication(), notchProperty.toString(), Toast.LENGTH_LONG).show();
                if(notchProperty.isNotchEnable()){
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) listParent.getLayoutParams();
                    marginLayoutParams.topMargin = notchProperty.getNotchHeight();
                    listParent.requestLayout();
                }
            }
        });
    }
}

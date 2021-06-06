package com.wcl.notchfit.demo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wcl.notchfit.args.NotchPosition;
import com.wcl.notchfit.args.NotchProperty;
import com.wcl.notchfit.utils.SizeUtils;

public class NotchBaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notch_base);
//        if(getSupportActionBar() != null) {
//            getSupportActionBar().hide();
//        }
        String[] data = new String[50];
        for (int i = 0; i < data.length; i++){
            data[i] = "item "+i;
        }
        ListView listView = findViewById(R.id.lv);
        listView.setAdapter(new ArrayAdapter<>(this, R.layout.layout_item, R.id.btn_text, data));

        listView.addHeaderView(LayoutInflater.from(this).inflate(R.layout.layout_header, null));
    }

    protected void fixLayout(NotchProperty notchProperty, boolean isFullScreen){
        final ViewGroup listParent = findViewById(R.id.ll_parent);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) listParent.getLayoutParams();

        int statusBarHeight = SizeUtils.getStatusBarHeight(NotchBaseActivity.this);
        if(notchProperty.isNotchEnable()){
            if(notchProperty.getNotchPosition() == NotchPosition.TOP){
                marginLayoutParams.topMargin = notchProperty.getNotchHeight();
                marginLayoutParams.leftMargin = 0;
                marginLayoutParams.rightMargin = 0;
            }
            else if(notchProperty.getNotchPosition() == NotchPosition.LEFT){
                marginLayoutParams.topMargin = isFullScreen ? 0 : statusBarHeight;
                marginLayoutParams.leftMargin = notchProperty.getNotchWidth();
                marginLayoutParams.rightMargin = 0;
            }
            else if(notchProperty.getNotchPosition() == NotchPosition.RIGHT) {
                marginLayoutParams.topMargin = isFullScreen ? 0 :  statusBarHeight;
                marginLayoutParams.leftMargin = 0;
                marginLayoutParams.rightMargin = notchProperty.getNotchWidth();
            }
        }
        else {
            marginLayoutParams.topMargin = isFullScreen ? 0 : statusBarHeight;
        }

        listParent.requestLayout();
    }
}

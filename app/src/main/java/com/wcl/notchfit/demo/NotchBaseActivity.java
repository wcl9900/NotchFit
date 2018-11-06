package com.wcl.notchfit.demo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
}

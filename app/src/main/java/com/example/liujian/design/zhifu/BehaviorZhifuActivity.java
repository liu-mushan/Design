package com.example.liujian.design.zhifu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.liujian.design.R;

public class BehaviorZhifuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behavor_zhifu);
        android.app.ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }
}

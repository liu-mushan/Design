package com.example.liujian.design.appBarLayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.liujian.design.R;

public class CoordinatorLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout);
        android.app.ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }
}

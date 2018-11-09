package com.example.liujian.design.move;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.liujian.design.R;

public class Behavior2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behavior2);
        android.app.ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }
}

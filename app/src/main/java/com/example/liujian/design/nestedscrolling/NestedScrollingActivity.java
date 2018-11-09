package com.example.liujian.design.nestedscrolling;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.liujian.design.R;

/**
 * @author : liujian
 * @since : 2018/10/12
 */
public class NestedScrollingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested_scrolling);
        android.app.ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }
}

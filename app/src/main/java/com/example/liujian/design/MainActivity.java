package com.example.liujian.design;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.liujian.design.appBarLayout.CoordinatorLayoutActivity;
import com.example.liujian.design.headerscroll.HeadScrollActivity;
import com.example.liujian.design.move.Behavior2Activity;
import com.example.liujian.design.nestedscrolling.NestedScrollingActivity;
import com.example.liujian.design.uc.UCHomeActivity;
import com.example.liujian.design.zhifu.BehaviorZhifuActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        android.app.ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button5).setOnClickListener(this);
        findViewById(R.id.button6).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button1) {
            startActivity(new Intent(this, CoordinatorLayoutActivity.class));
        } else if (id == R.id.button2) {
            startActivity(new Intent(this, BehaviorZhifuActivity.class));
        } else if (id == R.id.button3) {
            startActivity(new Intent(this, Behavior2Activity.class));
        } else if (id == R.id.button4) {
            startActivity(new Intent(this, HeadScrollActivity.class));
        } else if (id == R.id.button5) {
            startActivity(new Intent(this, NestedScrollingActivity.class));
        }else if(id == R.id.button6){
            startActivity(new Intent(this, UCHomeActivity.class));
        }
    }
}

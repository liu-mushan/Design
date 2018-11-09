package com.example.liujian.design.move;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * @author : liujian
 * @since : 2018/10/8
 */
public class MoveView extends View {
    private int mTouchSlop;

    public MoveView(Context context) {
        this(context, null);
    }

    public MoveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MoveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    private float lastX = 0;
    private float lastY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 获取view相对于手机屏幕的xy值
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = (int) (x - lastX);
                int deltaY = (int) (y - lastY);
                int translationX = (int) (getTranslationX() + deltaX);
                int translationY = (int) (getTranslationY() + deltaY);
                setTranslationX(translationX);
                setTranslationY(translationY);
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        lastX = x;
        lastY = y;
        return true;

    }
}

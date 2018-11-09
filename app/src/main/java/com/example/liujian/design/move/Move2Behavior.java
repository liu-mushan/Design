package com.example.liujian.design.move;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.liujian.design.R;

import java.lang.ref.WeakReference;

/**
 * @author : liujian
 * @since : 2018/10/8
 */
public class Move2Behavior extends CoordinatorLayout.Behavior<View> {
    private static final int MOVE_TRANSACTION_Y = 80;
    private static final String TAG = "Move2Behavior";
    private WeakReference<View> weakReference;

    public Move2Behavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        if (dependency.getId() == R.id.bottom) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        Log.i(TAG, "onDependentViewChanged: " + dependency.getTranslationY());
        float x = dependency.getX();
        float y = dependency.getY();
        child.setX(x);
        child.setY(y + 300);
        return true;
    }
}

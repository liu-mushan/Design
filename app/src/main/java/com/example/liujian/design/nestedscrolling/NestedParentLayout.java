package com.example.liujian.design.nestedscrolling;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.example.liujian.design.R;

/**
 * @author : liujian
 * @since : 2018/10/12
 */
public class NestedParentLayout extends FrameLayout implements NestedScrollingParent {
    private NestedScrollingParentHelper nestedScrollingParentHelper;

    public NestedParentLayout(Context context) {
        this(context, null);
    }

    public NestedParentLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NestedParentLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        nestedScrollingParentHelper = new NestedScrollingParentHelper(this);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return child.getId() == R.id.childView;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        nestedScrollingParentHelper.onNestedScrollAccepted(child, target, axes);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        if (dxUnconsumed == 0 && dyUnconsumed == 0) {
            return;
        }
        Log.i("LIUJIAN", "NestedParentLayout dxUnconsumed: " + dxUnconsumed + " dyUnconsumed:" + dyUnconsumed);
        ViewCompat.offsetLeftAndRight(this, dxUnconsumed);
        ViewCompat.offsetTopAndBottom(this, dyUnconsumed);
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(target, dx, dy, consumed);
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return super.onNestedFling(target, velocityX, velocityY, consumed);
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return super.onNestedPreFling(target, velocityX, velocityY);
    }

    @Override
    public void onStopNestedScroll(View child) {
        super.onStopNestedScroll(child);
    }

    @Override
    public int getNestedScrollAxes() {
        return nestedScrollingParentHelper.getNestedScrollAxes();
    }
}

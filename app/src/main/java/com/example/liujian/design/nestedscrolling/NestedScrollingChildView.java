package com.example.liujian.design.nestedscrolling;

import android.content.Context;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author : liujian
 * @since : 2018/10/12
 */
public class NestedScrollingChildView extends View implements NestedScrollingChild {
    private NestedScrollingChildHelper nNestedScrollingChildHelper;
    private int[] consumed = new int[2];//消耗的距离
    private int[] offsetInWindow = new int[2];//窗口偏移

    public NestedScrollingChildView(Context context) {
        this(context, null);
    }

    public NestedScrollingChildView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NestedScrollingChildView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        nNestedScrollingChildHelper = new NestedScrollingChildHelper(this);
        setNestedScrollingEnabled(true);
    }

    private int mLastX;
    private int mLastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = (int) event.getRawX();
                mLastY = (int) event.getRawY();

                startNestedScroll(ViewCompat.SCROLL_AXIS_HORIZONTAL | SCROLL_AXIS_VERTICAL);
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = (int) (event.getRawX() - mLastX);
                int dy = (int) (event.getRawY() - mLastY);
                if (dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow)) {
                    dx -= consumed[0];
                    dy -= consumed[1];
                }
                View parent = (View) getParent();
                int parentWidth = parent.getWidth();
                int parentHeight = parent.getHeight();
                int dxConsumed = 0;
                int dyConsumed = 0;
                int dxUnConsumed = 0;
                int dyUnConsumed = 0;

                if (getLeft() + dx <= 0) {
                    dxConsumed = -getLeft();
                    dxUnConsumed = dx - dxConsumed;
                } else if (getRight() + dx >= parentWidth) {
                    dxConsumed = parentWidth - getRight();
                    dxUnConsumed = dx - dxConsumed;
                } else {
                    dxConsumed = dx;
                }
                if (getTop() + dy <= 0) {
                    dyConsumed = -getTop();
                    dyUnConsumed = dy - dyConsumed;
                } else if (getBottom() + dy >= parentHeight) {
                    dyConsumed = parentHeight - getBottom();
                    dyUnConsumed = dy - dyConsumed;
                } else {
                    dyConsumed = dy;
                }
                dispatchNestedScroll(dxConsumed, dyConsumed, dxUnConsumed, dyUnConsumed, offsetInWindow);
                ViewCompat.offsetLeftAndRight(this, dxConsumed);
                ViewCompat.offsetTopAndBottom(this, dyConsumed);

                mLastX = (int) event.getRawX();
                mLastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                stopNestedScroll();
                break;
        }
        return true;
    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        nNestedScrollingChildHelper.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return nNestedScrollingChildHelper.startNestedScroll(axes);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return nNestedScrollingChildHelper.isNestedScrollingEnabled();
    }

    @Override
    public void stopNestedScroll() {
        nNestedScrollingChildHelper.stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return nNestedScrollingChildHelper.hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return nNestedScrollingChildHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return nNestedScrollingChildHelper.dispatchNestedPreFling(velocityX, velocityY);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return nNestedScrollingChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        return nNestedScrollingChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }
}

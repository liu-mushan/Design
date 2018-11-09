package com.example.liujian.design.zhifu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.liujian.design.R;

import java.lang.ref.WeakReference;

/**
 * @author : liujian
 * @since : 2018/10/9
 */
public class NestedScrollBehavior extends CoordinatorLayout.Behavior<NestedScrollView> {
    private WeakReference<View> mDependencyView;

    public NestedScrollBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull NestedScrollView child, @NonNull View dependency) {
        if (dependency.getId() == R.id.toolbar) {
            if (mDependencyView == null || mDependencyView.get() == null) {
                mDependencyView = new WeakReference<>(dependency);
            }
            return true;
        }
        return super.layoutDependsOn(parent, child, dependency);
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull NestedScrollView child, @NonNull View dependency) {
        child.setTranslationY(dependency.getHeight() + dependency.getTranslationY());
        return true;
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull NestedScrollView child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull NestedScrollView child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        int maxTransactionY = -child.getContext().getResources().getDimensionPixelSize(R.dimen.app_bar_height);
        if (dy > 0 && getDependencyView().getTranslationY() == maxTransactionY) {
            return;
        }
        if (dy < 0 && getDependencyView().getTranslationY() == 0) {
            return;
        }
        float transactionY = getDependencyView().getTranslationY() - dy;
        if (transactionY <= 0 && transactionY >= -child.getContext().getResources().getDimensionPixelSize(R.dimen.app_bar_height)) {
            getDependencyView().setTranslationY(transactionY);
            consumed[1] = dy;
        } else if (transactionY > 0) {
            getDependencyView().setTranslationY(0);
            consumed[1] = (int) getDependencyView().getTranslationY();
        } else if (transactionY < maxTransactionY) {
            getDependencyView().setTranslationY(maxTransactionY);
            consumed[1] = (int) getDependencyView().getTranslationY() - maxTransactionY;
        }
    }

    private View getDependencyView() {
        return mDependencyView.get();
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull CoordinatorLayout parent, @NonNull NestedScrollView child, @NonNull MotionEvent ev) {
        return super.onInterceptTouchEvent(parent, child, ev);
    }

    @Override
    public boolean onTouchEvent(@NonNull CoordinatorLayout parent, @NonNull NestedScrollView child, @NonNull MotionEvent ev) {
        return super.onTouchEvent(parent, child, ev);
    }
}

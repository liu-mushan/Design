package com.example.liujian.design.headerscroll;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import com.example.liujian.design.R;

import java.lang.ref.WeakReference;

/**
 * @author : liujian
 * @since : 2018/10/9
 */
public class HeaderScrollBehavior extends CoordinatorLayout.Behavior<RecyclerView> {
    private static final String TAG = "HeaderScrollBehavior";
    private WeakReference<View> mDependencyView;

    private Scroller scroller;
    private boolean isScrolling;


    public HeaderScrollBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        scroller = new Scroller(context);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull RecyclerView child, @NonNull View dependency) {
        if (R.id.scrolling_header == dependency.getId()) {
            if (mDependencyView == null || mDependencyView.get() == null) {
                mDependencyView = new WeakReference<>(dependency);
                return true;
            }
            return true;
        }
        return super.layoutDependsOn(parent, child, dependency);
    }

    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull RecyclerView child, int layoutDirection) {
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        if (layoutParams.height == CoordinatorLayout.LayoutParams.MATCH_PARENT) {
            child.layout(0, 0, child.getMeasuredWidth(), child.getMeasuredHeight() - getCollapsedHeight());
            return true;
        }
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull RecyclerView child, @NonNull View dependency) {
        child.setTranslationY(dependency.getHeight() + dependency.getTranslationY());
        float progress = 1 - Math.abs(dependency.getTranslationY()) * 1.0f / (dependency.getHeight() - getCollapsedHeight());
        float scale = 1.0f + 0.4f * (1 - progress);
        dependency.setScaleX(scale);
        dependency.setScaleY(scale);
        dependency.setAlpha(progress);
        return true;
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull RecyclerView child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        Log.i(TAG, "onStartNestedScroll: ");
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull CoordinatorLayout coordinatorLayout, @NonNull RecyclerView child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        Log.i(TAG, "onNestedScrollAccepted: ");
        scroller.abortAnimation();
        isScrolling = false;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull RecyclerView child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        //处理上滑动
        if (dy <= 0) {
            return;
        }
        View dependentView = getDependentView();
        int maxTransaction = -(dependentView.getHeight() - getCollapsedHeight());
        if (dependentView.getTranslationY() == maxTransaction) {
            return;
        }
        float transactionY = dependentView.getTranslationY() - dy;
        if (transactionY >= maxTransaction) {
            dependentView.setTranslationY(transactionY);
            consumed[1] = dy;
        } else {
            dependentView.setTranslationY(maxTransaction);
            consumed[1] = (int) (dependentView.getTranslationY() - maxTransaction);
        }
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull RecyclerView child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        //处理向下滑动，RecyclerView滑到底部分距离
        if (dyUnconsumed >= 0) {
            return;
        }
        View dependentView = getDependentView();
        int maxTransaction = 0;
        if (dependentView.getTranslationY() == maxTransaction) {
            return;
        }
        float transactionY = dependentView.getTranslationY() - dyUnconsumed;
        if (transactionY <= maxTransaction) {
            dependentView.setTranslationY(transactionY);
        } else {
            dependentView.setTranslationY(maxTransaction);
        }
    }

    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, RecyclerView child, View target, float velocityX, float velocityY) {
        Log.i(TAG, "onNestedPreFling: ");
        return onScrollingFling((int) velocityY, false);
    }


    @Override
    public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull RecyclerView child, @NonNull View target, int type) {
        if (!isScrolling) {
            Log.i(TAG, "onStopNestedScroll: ");
            onScrollingFling(800, true);
        }
    }

    private boolean onScrollingFling(int velocityY, boolean isStop) {
        isScrolling = true;
        View dependent = getDependentView();
        int maxTran = -(dependent.getHeight() - getCollapsedHeight());
        if (dependent.getTranslationY() == 0 || dependent.getTranslationY() == maxTran) {
            return false;
        }

        boolean isExpand = true;
        if (Math.abs(dependent.getTranslationY()) >= (Math.abs(Math.abs(maxTran) - Math.abs(dependent.getTranslationY())))) {
            isExpand = false;
        }
        if (!isStop) {
            isExpand = velocityY < 0;
        }
        int finalY = isExpand ? 0 : maxTran;
        if (Math.abs(velocityY) < 800) {
            velocityY = 800;
        }
        scroller.startScroll(0, (int) dependent.getTranslationY(), 0, (int) (finalY - dependent.getTranslationY()), 1000000 / Math.abs(velocityY));
        getDependentView().post(scrollerRunnable);
        return true;
    }

    private Runnable scrollerRunnable = new Runnable() {
        @Override
        public void run() {
            if (scroller.computeScrollOffset()) {
                getDependentView().setTranslationY(scroller.getCurrY());
                getDependentView().post(this);
            } else {
                isScrolling = false;
            }
        }
    };

    private int getCollapsedHeight() {
        return getDependentView().getResources().getDimensionPixelSize(R.dimen.collapsed_header_height);
    }

    private View getDependentView() {
        return mDependencyView.get();
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull CoordinatorLayout parent, @NonNull RecyclerView child, @NonNull MotionEvent ev) {
        return super.onInterceptTouchEvent(parent, child, ev);
    }

    @Override
    public boolean onTouchEvent(@NonNull CoordinatorLayout parent, @NonNull RecyclerView child, @NonNull MotionEvent ev) {
        return super.onTouchEvent(parent, child, ev);
    }
}

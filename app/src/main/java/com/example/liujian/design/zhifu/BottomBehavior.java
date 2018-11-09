package com.example.liujian.design.zhifu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.liujian.design.R;

/**
 * 泛型中的类型就是要执行动作的View类，也就是Child
 *
 * @author : AndroidLiujian
 * @since : 2018/10/4
 */
public class BottomBehavior extends CoordinatorLayout.Behavior<TextView> {
    private static final String TAG = "BottomBehavior";

    public BottomBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull TextView child, @NonNull View dependency) {
        return dependency.getId() == R.id.toolbar;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull TextView child, @NonNull View dependency) {
        Log.i(TAG, "onDependentViewChanged: " + dependency.getTop());
        float translationY = Math.abs(dependency.getTranslationY());
        child.setTranslationY(translationY);
        return true;
    }
}

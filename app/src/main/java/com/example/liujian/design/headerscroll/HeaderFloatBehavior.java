package com.example.liujian.design.headerscroll;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.example.liujian.design.R;

/**
 * @author : liujian
 * @since : 2018/10/9
 */
public class HeaderFloatBehavior extends CoordinatorLayout.Behavior<LinearLayout> {
    private ArgbEvaluator argbEvaluator;

    public HeaderFloatBehavior(Context context, AttributeSet set) {
        super(context, set);
        argbEvaluator = new ArgbEvaluator();
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull LinearLayout child, @NonNull View dependency) {
        return dependency.getId() == R.id.scrolling_header;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull LinearLayout child, @NonNull View dependency) {
        float progress = 1 - (Math.abs(dependency.getTranslationY()) * 1.0f / (dependency.getHeight() - dependency.getResources().getDimensionPixelSize(R.dimen.collapsed_header_height)));

        int initOffset = child.getResources().getDimensionPixelSize(R.dimen.init_float_offset_y);
        int collapsedOffset = child.getResources().getDimensionPixelSize(R.dimen.collapsed_float_offset_y);
        //130 - 5dp的偏移量
        float transactionY = collapsedOffset + (initOffset - collapsedOffset) * progress;
        child.setTranslationY(transactionY);

        child.setBackgroundColor((int) argbEvaluator.evaluate(
                progress,
                child.getResources().getColor(R.color.colorCollapsedFloatBackground),
                child.getResources().getColor(R.color.colorInitFloatBackground)));

        int initMargin = child.getResources().getDimensionPixelSize(R.dimen.init_float_margin);
        int collapsedMargin = child.getResources().getDimensionPixelSize(R.dimen.collapsed_float_margin);
        float margin = collapsedMargin + (initMargin - collapsedMargin) * progress;
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        layoutParams.leftMargin = (int) margin;
        layoutParams.rightMargin = (int) margin;
        child.requestLayout();
        return true;
    }

}

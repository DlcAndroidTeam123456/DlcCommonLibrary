package cn.dlc.commonlibrary.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import cn.dlc.commonlibrary.R;

/**
 * 最多能多高的FrameLayout
 */
public class MaxWrapFrameLayout extends FrameLayout {

    float max;

    public MaxWrapFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        if (attrs != null) {

            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MaxWrapFrameLayout);
            max = ta.getDimension(R.styleable.MaxWrapFrameLayout_max_size, 0);
            ta.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (max > 0) {
            int specMode = MeasureSpec.getMode(heightMeasureSpec);
            int heightSize = MeasureSpec.getSize(heightMeasureSpec);
            heightSize = (int) Math.min(heightSize, max);
            int newHeightSpec = MeasureSpec.makeMeasureSpec(heightSize, specMode);
            super.onMeasure(widthMeasureSpec, newHeightSpec);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}

package cn.dlc.commonlibrary.ui.widget.status;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import cn.dlc.commonlibrary.R;

/**
 * 一个在原来定义的尺寸上，额外添加状态栏高度的FrameLayout
 * 如在xml中写死
 */
public class TopFrameLayout extends FrameLayout {
    
    private int mNewHeightSpec;

    private StatusDelegate mStatusDelegate;

    /**
     * 一个在原来定义的尺寸上，额外添加状态栏高度的FrameLayout
     *
     * @param context
     */
    public TopFrameLayout(@NonNull Context context) {
        this(context, null);
    }

    public TopFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs,
        @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mStatusDelegate = new StatusDelegate(context);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TopFrameLayout);
        mStatusDelegate.setFitStatusBar(
            ta.getBoolean(R.styleable.TopFrameLayout_fitStatusBar, true));
        ta.recycle();

        mNewHeightSpec = 0;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (mStatusDelegate.toFitStatusBar() && mNewHeightSpec == 0) {
            try {
                int height =
                    MeasureSpec.getSize(heightMeasureSpec) + mStatusDelegate.getStatusHeight();
                mNewHeightSpec =
                    MeasureSpec.makeMeasureSpec(height, MeasureSpec.getMode(heightMeasureSpec));
            } catch (Resources.NotFoundException e) {
                //e.printStackTrace();
            }
        }

        if (mNewHeightSpec != 0) {
            super.onMeasure(widthMeasureSpec, mNewHeightSpec);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}

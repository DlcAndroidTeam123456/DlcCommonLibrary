package cn.dlc.commonlibrary.ui.widget.status;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import cn.dlc.commonlibrary.R;

/**
 * Created by John on 2017/10/9.
 */

public class TopLinearLayout extends LinearLayout {

    private int mNewHeightSpec;
    private StatusDelegate mStatusDelegate;

    public TopLinearLayout(@NonNull Context context) {
        this(context, null);
    }

    public TopLinearLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopLinearLayout(@NonNull Context context, @Nullable AttributeSet attrs,
        @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mStatusDelegate = new StatusDelegate(context);

        setOrientation(VERTICAL);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TopLinearLayout);
        mStatusDelegate.setFitStatusBar(
            ta.getBoolean(R.styleable.TopLinearLayout_fitStatusBar, true));
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

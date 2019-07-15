package cn.dlc.commonlibrary.ui.widget.status;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import cn.dlc.commonlibrary.R;

/**
 * Created by John on 2017/10/9.
 */

public class TopRelativeLayout extends RelativeLayout {

    private int mNewHeightSpec;
    private StatusDelegate mStatusDelegate;

    public TopRelativeLayout(@NonNull Context context) {
        this(context, null);
    }

    public TopRelativeLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopRelativeLayout(@NonNull Context context, @Nullable AttributeSet attrs,
        @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mStatusDelegate = new StatusDelegate(context);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TopRelativeLayout);
        mStatusDelegate.setFitStatusBar(
            ta.getBoolean(R.styleable.TopRelativeLayout_fitStatusBar, true));
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

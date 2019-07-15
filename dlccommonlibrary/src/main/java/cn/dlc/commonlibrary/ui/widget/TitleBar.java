package cn.dlc.commonlibrary.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.dlc.commonlibrary.R;
import cn.dlc.commonlibrary.ui.widget.status.StatusHolderView;

/**
 * Created by lizhiyong on 2015/12/28.
 */
public class TitleBar extends RelativeLayout {

    public final TextView titleText;
    public final ImageButton leftImage;
    public final ImageButton rightImage;
    public final TextView leftText;
    public final TextView rightText;
    private final View bottomLine;

    //private int mHeight;

    public static final int TITLE_TEXT = R.id.titleText;
    public static final int LEFT_IMAGE = R.id.leftImage;
    public static final int RIGHT_IMAGE = R.id.rightImage;
    public static final int LEFT_TEXT = R.id.leftText;
    public static final int RIGHT_TEXT = R.id.rightText;
    private static final int STATUS_HOLDER = R.id.statusHolder;

    // 适配状态栏
    private boolean mFitStatusBar;
    private int mNewHeightSpec;

    private StatusHolderView mStatusHolderView;
    private int mStatusHeight;

    public TitleBar(@NonNull Context context) {
        this(context, null);
    }

    public TitleBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(@NonNull Context context, @Nullable AttributeSet attrs,
        @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);

        mFitStatusBar = typedArray.getBoolean(R.styleable.TitleBar_fitStatusBar, false);
        mNewHeightSpec = 0;

        // 在最底层填充一个布局
        int bottomLayout = typedArray.getResourceId(R.styleable.TitleBar_bottom_layout, 0);
        if (bottomLayout != 0) {
            LayoutInflater.from(context).inflate(bottomLayout, this, true);
        }

        View view = LayoutInflater.from(context)
            .inflate(R.layout.view_title_bar_fit_status_bar, this, true);

        mStatusHolderView = (StatusHolderView) view.findViewById(STATUS_HOLDER);
        titleText = (TextView) view.findViewById(TITLE_TEXT);
        leftImage = (ImageButton) view.findViewById(LEFT_IMAGE);
        rightImage = (ImageButton) view.findViewById(RIGHT_IMAGE);
        leftText = (TextView) view.findViewById(LEFT_TEXT);
        rightText = (TextView) view.findViewById(RIGHT_TEXT);
        bottomLine = view.findViewById(R.id.bottomLine);

        setupTitleBar(context, typedArray);

        typedArray.recycle();
    }

    private void setupTitleBar(Context context, TypedArray typedArray) {

        mStatusHolderView.setVisibility(mFitStatusBar ? VISIBLE : GONE);
        // 标题
        int textColor = ContextCompat.getColor(context, R.color.title_text_color);
        textColor = typedArray.getColor(R.styleable.TitleBar_titleTextColor, textColor);
        titleText.setTextColor(textColor);

        float titleSize = getResources().getDimension(R.dimen.title_text_size);
        titleSize = typedArray.getDimension(R.styleable.TitleBar_titleTextSize, titleSize);
        titleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize);

        boolean boldTitleText = typedArray.getBoolean(R.styleable.TitleBar_boldTitleText, false);
        if (boldTitleText) {
            titleText.setTypeface(titleText.getTypeface(), Typeface.BOLD);
        }

        String titleTextString = typedArray.getString(R.styleable.TitleBar_titleText);
        titleText.setText(titleTextString);

        // 下方分隔线
        int lineColor = ContextCompat.getColor(context, R.color.title_bottom_line_color);
        lineColor = typedArray.getColor(R.styleable.TitleBar_lineColor, lineColor);
        bottomLine.setBackgroundColor(lineColor);

        boolean showLine = typedArray.getBoolean(R.styleable.TitleBar_showLine, false);

        bottomLine.setVisibility(showLine ? VISIBLE : GONE);

        // 左侧按钮
        int leftDrawableRes = typedArray.getResourceId(R.styleable.TitleBar_leftDrawable, 0);

        if (leftDrawableRes != 0) {
            leftImage.setImageResource(leftDrawableRes);
        }

        boolean showLeftImage =
            typedArray.getBoolean(R.styleable.TitleBar_showLeftImage, leftDrawableRes != 0);

        leftImage.setVisibility(showLeftImage ? VISIBLE : GONE);

        // 右侧按钮
        int rightDrawableRes = typedArray.getResourceId(R.styleable.TitleBar_rightDrawable, 0);

        if (rightDrawableRes != 0) {
            rightImage.setImageResource(rightDrawableRes);
        }

        boolean showRightImage =
            typedArray.getBoolean(R.styleable.TitleBar_showRightImage, rightDrawableRes != 0);

        rightImage.setVisibility(showRightImage ? VISIBLE : GONE);

        // 按钮文字大小
        float buttonTextSize = getResources().getDimension(R.dimen.title_button_text_size);
        buttonTextSize =
            typedArray.getDimension(R.styleable.TitleBar_buttonTextSize, buttonTextSize);

        // 左侧文字
        int leftTextColor = typedArray.getColor(R.styleable.TitleBar_leftTextColor, textColor);
        leftText.setTextColor(leftTextColor);
        leftText.setTextSize(TypedValue.COMPLEX_UNIT_PX, buttonTextSize);

        String leftString = typedArray.getString(R.styleable.TitleBar_leftText);
        leftText.setText(leftString);

        boolean showLeftText = typedArray.getBoolean(R.styleable.TitleBar_showLeftText,
            !TextUtils.isEmpty(leftString));
        leftText.setVisibility(showLeftText ? VISIBLE : GONE);

        // 右侧文字
        int rightTextColor = typedArray.getColor(R.styleable.TitleBar_rightTextColor, textColor);
        rightText.setTextColor(rightTextColor);

        rightText.setTextSize(TypedValue.COMPLEX_UNIT_PX, buttonTextSize);

        String rightString = typedArray.getString(R.styleable.TitleBar_rightText);
        rightText.setText(rightString);
        boolean showRightText = typedArray.getBoolean(R.styleable.TitleBar_showRightText,
            !TextUtils.isEmpty(rightString));

        rightText.setVisibility(showRightText ? VISIBLE : GONE);

        // 按钮背景
        int buttonBg =
            typedArray.getResourceId(R.styleable.TitleBar_imageBg, R.drawable.title_bar_button_bg);

        int textBg =
            typedArray.getResourceId(R.styleable.TitleBar_textBg, R.drawable.title_bar_text_bg);

        setImageBackground(buttonBg);
        setTextBackground(textBg);
    }

    public void setImageBackground(int resId) {
        leftImage.setBackgroundResource(resId);
        rightImage.setBackgroundResource(resId);
    }

    public void setTextBackground(int resId) {
        leftText.setBackgroundResource(resId);
        rightText.setBackgroundResource(resId);
    }

    public void setTitle(@StringRes int resId) {
        titleText.setText(resId);
    }

    public void setTitle(CharSequence title) {
        titleText.setText(title);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 让左右两个按钮的宽度跟高度一样高
        changeWidth(leftImage, h - mStatusHeight);
        changeWidth(rightImage, h - mStatusHeight);
    }

    private void changeWidth(View view, int newHeight) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = newHeight;
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        leftImage.setOnClickListener(l);
        rightImage.setOnClickListener(l);
        leftText.setOnClickListener(l);
        rightText.setOnClickListener(l);
    }

    /**
     * 设置左边按钮的点击事件
     *
     * @param l
     */
    public void setLeftOnClickListener(@Nullable OnClickListener l) {
        leftImage.setOnClickListener(l);
        leftText.setOnClickListener(l);
    }

    /**
     * 设置右边按钮的点击事件
     *
     * @param l
     */
    public void setRightOnClickListener(@Nullable OnClickListener l) {
        rightImage.setOnClickListener(l);
        rightText.setOnClickListener(l);
    }

    /**
     * 左边按钮退出
     *
     * @param activity
     */
    public void leftExit(final Activity activity) {
        leftImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (mFitStatusBar
            && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
            && mNewHeightSpec == 0) {
            try {
                Resources resources = getResources();
                int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
                mStatusHeight = resources.getDimensionPixelSize(resourceId);
                int height = MeasureSpec.getSize(heightMeasureSpec) + mStatusHeight;
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

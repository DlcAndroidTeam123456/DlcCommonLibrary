package cn.dlc.commonlibrary.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import cn.dlc.commonlibrary.R;

/**
 * Created by John on 2017/11/12.
 */

public class EmptyView extends FrameLayout {

    private ImageView mImageView;
    private TextView mTextView;

    public EmptyView(@NonNull Context context) {
        this(context, null);
    }

    public EmptyView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyView(@NonNull Context context, @Nullable AttributeSet attrs,
        @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (attrs != null) {

            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.EmptyView);

            int layoutId = ta.getResourceId(R.styleable.EmptyView_empty_layout, 0);

            if (layoutId != 0) {
                LayoutInflater.from(context).inflate(layoutId, this, true);
            }

            int imageViewId = ta.getResourceId(R.styleable.EmptyView_image_view_id, 0);

            if (imageViewId != 0) {

                Drawable drawable = ta.getDrawable(R.styleable.EmptyView_empty_drawable);
                if (drawable != null) {

                    ImageView imageView = (ImageView) findViewById(imageViewId);
                    imageView.setImageDrawable(drawable);
                }
            }

            int textViewId = ta.getResourceId(R.styleable.EmptyView_text_view_id, 0);

            if (textViewId != 0) {

                String text = ta.getString(R.styleable.EmptyView_empty_text);
                if (!TextUtils.isEmpty(text)) {

                    TextView textView = (TextView) findViewById(textViewId);
                    textView.setText(text);
                }
            }

            ta.recycle();
        }
    }
}

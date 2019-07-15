package cn.dlc.commonlibrary.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import cn.dlc.commonlibrary.R;

/**
 * Created by Yz on 2018/3/10.
 * 搜索控件
 */

public class SearchWidget extends LinearLayout {

    EditText mEtSearch;
    ImageView mIvSearchClear;
    private OnEditListener mListener;

    public SearchWidget(@NonNull Context context) {
        this(context, null);
    }

    public SearchWidget(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.view_search, this);
        mEtSearch = findViewById(R.id.et_search);
        mIvSearchClear = findViewById(R.id.iv_search_clear);
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        setBackgroundResource(R.drawable.cp_search_box_bg);

        mIvSearchClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtSearch.setText(null);
                mIvSearchClear.setVisibility(View.GONE);
            }
        });
        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String keyword = s.toString();
                if (TextUtils.isEmpty(keyword)) {
                    mIvSearchClear.setVisibility(View.GONE);
                } else {
                    mIvSearchClear.setVisibility(View.VISIBLE);
                }
                if (mListener != null) {
                    mListener.afterTextChanged(s);
                }
            }
        });
        //xml实现设置name和value,和是否显示图片
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SearchWidget);
            int ime_options =
                typedArray.getInt(R.styleable.SearchWidget_android_imeOptions, InputType.TYPE_NULL);
            mEtSearch.setImeOptions(ime_options);
            String hint = typedArray.getString(R.styleable.SearchWidget_android_hint);
            String text = typedArray.getString(R.styleable.SearchWidget_android_text);
            int type = typedArray.getInt(R.styleable.SearchWidget_android_inputType,
                InputType.TYPE_CLASS_TEXT);
            mEtSearch.setInputType(type);
            setHint(hint);
            setText(text);
            typedArray.recycle();
        }
    }

    private void setHint(CharSequence hint) {
        mEtSearch.setHint(hint);
    }

    /**
     * 设置监听
     *
     * @param listener
     */
    public void setOnEditListener(OnEditListener listener) {
        mListener = listener;
    }

    public interface OnEditListener {
        /**
         * 回调文本变化
         *
         * @param s
         */
        void afterTextChanged(Editable s);
    }

    /**
     * 设置输入内容
     *
     * @param text
     */
    public final void setText(CharSequence text) {
        mEtSearch.setText(text);
        mEtSearch.setSelection(text.length());
    }
}

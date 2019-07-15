package cn.dlc.commonlibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;

/**
 * 键盘处理类
 *
 * @author zhy
 */
public class KeyBoardUtils {

    private static class Holder {
        private static KeyBoardUtils util = new KeyBoardUtils();
    }

    public static final KeyBoardUtils getInstant() {
        return Holder.util;
    }

    //打卡软键盘
    private void openKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm =
            (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    //关闭软键盘
    private void closeKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm =
            (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    //让布局向上,不挡住ediext
    private void makeLayoutUp(Activity activity) {
        FrameLayout content = (FrameLayout) activity.findViewById(android.R.id.content);
        final View mChildOfContent = content.getChildAt(0);
        mChildOfContent.getViewTreeObserver()
            .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                public void onGlobalLayout() {
                    possiblyResizeChildOfContent(mChildOfContent);
                }
            });
        frameLayoutParams = (FrameLayout.LayoutParams) mChildOfContent.getLayoutParams();
    }

    private int usableHeightPrevious;
    private FrameLayout.LayoutParams frameLayoutParams;

    private void possiblyResizeChildOfContent(View mChildOfContent) {
        int usableHeightNow = computeUsableHeight(mChildOfContent);
        if (usableHeightNow != usableHeightPrevious) {
            int usableHeightSansKeyboard = mChildOfContent.getRootView().getHeight();
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;
            if (heightDifference > (usableHeightSansKeyboard / 4)) {
                // keyboard probably just became visible
                frameLayoutParams.height = usableHeightSansKeyboard - heightDifference;
            } else {
                // keyboard probably just became hidden
                frameLayoutParams.height = usableHeightSansKeyboard;
            }
            mChildOfContent.requestLayout();
            usableHeightPrevious = usableHeightNow;
        }
    }

    private int computeUsableHeight(View mChildOfContent) {
        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        return (r.bottom - r.top);
    }
}

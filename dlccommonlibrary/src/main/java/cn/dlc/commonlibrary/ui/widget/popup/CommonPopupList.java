package cn.dlc.commonlibrary.ui.widget.popup;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import cn.dlc.commonlibrary.R;
import cn.dlc.commonlibrary.ui.adapter.BaseListAdapter;
import java.util.Arrays;
import java.util.List;

/**
 * 通用的
 *
 * @param <T>
 */
public abstract class CommonPopupList<T> extends PopupWindow {

    private final Context mContext;

    private final ListView mListView;

    private int mDefaultMargin;

    private boolean mDimmerBackground;

    private OnSelectItemListener<T> mOnSelectItemListener;

    private ItemConverter<T> mItemConverter = new StringItemConverter();
    private final BaseListAdapter<T> mAdapter;

    protected int mCurrent = -1;

    /**
     * 选择监听器
     *
     * @param <T>
     */
    public interface OnSelectItemListener<T> {
        /**
         * @param popup 弹出列表
         * @param item 选中那个的数据
         * @param string 选中那个的字符串
         * @param position 选中的位置
         * @param prevPosition 上一个选中的位置
         */
        void onSelectItem(CommonPopupList popup, T item, String string, int position,
            int prevPosition);
    }

    /**
     * 元素格式化器
     *
     * @param <T>
     */
    public interface ItemConverter<T> {
        String covert(T item, int position);
    }

    /**
     * 默认的字符串格式化器
     */
    public class StringItemConverter implements ItemConverter {

        @Override
        public String covert(Object o, int position) {
            return String.valueOf(o);
        }
    }

    public void setItemConverter(ItemConverter<T> itemConverter) {
        if (itemConverter == null) {
            return;
        }
        mItemConverter = itemConverter;
    }

    public CommonPopupList(Context context) {
        super(context);

        mContext = context;

        View view = LayoutInflater.from(context).inflate(getContentViewLayout(), null);

        mListView = findListView(view);

        mAdapter = new BaseListAdapter<T>() {
            @Override
            protected void inflateItem(ListViewHolder holder, int position) {

                T item = mAdapter.getItem(position);

                CommonPopupList.this.inflateItem(holder, item, position, mCurrent, mItemConverter);
            }

            @Override
            public int getItemLayoutId(int viewType) {
                return CommonPopupList.this.getItemLayoutId();
            }
        };

        mListView.setAdapter(mAdapter);

        mDefaultMargin = getDefaultMargin();

        setContentView(view);
        setAnimationStyle(R.style.PopupStyle);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        setWidth(getDimen(150));

        update();
    }

    public Context getContext() {
        return mContext;
    }

    /**
     * 获取自定义布局文件id
     *
     * @return
     */
    public abstract int getContentViewLayout();

    /**
     * 找到ListView
     *
     * @param contentView
     * @return
     */
    public abstract ListView findListView(View contentView);

    /**
     * 获取Item的布局文件id
     *
     * @return
     */
    protected abstract int getItemLayoutId();

    /**
     * 填充Item
     *
     * @param holder
     * @param position 填充的位置
     * @param selected 已选中的那个
     * @param itemConverter Item格式化器
     */
    public abstract void inflateItem(BaseListAdapter.ListViewHolder holder, T item, int position,
        int selected, ItemConverter<T> itemConverter);

    /**
     * 默认margin为 1个单位
     *
     * @return
     */
    public int getDefaultMargin() {
        return getDimen(1);
    }

    /**
     * 获取尺寸简便方法
     *
     * @param dp
     * @return
     */
    private int getDimen(int dp) {

        float pxDimension = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, dp,
            mContext.getResources().getDisplayMetrics());
        return (int) pxDimension;
    }

    /**
     * 设置选择监听器
     *
     * @param onSelectItemListener
     */
    public void setOnSelectItemListener(final OnSelectItemListener<T> onSelectItemListener) {

        if (onSelectItemListener != null) {
            mOnSelectItemListener = onSelectItemListener;
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    selectItem(position);
                }
            });
        }
    }

    /**
     * 选择某项
     *
     * @param position
     */
    public void selectItem(int position) {

        int prevPosition = mCurrent;

        T item = null;
        String string = null;

        if (position >= 0 && position <= mAdapter.getCount()) {
            mCurrent = position;
            item = mAdapter.getItem(position);
            string = mItemConverter.covert(item, position);
        } else {
            mCurrent = -1;
        }

        mAdapter.notifyDataSetChanged();

        if (mOnSelectItemListener != null) {
            mOnSelectItemListener.onSelectItem(this, item, string, mCurrent, prevPosition);
        }
    }

    /**
     * 获取当前索引
     *
     * @return
     */
    protected int getCurrentIndex() {
        return mCurrent;
    }

    /**
     * 让窗口宽度跟制定的View以这样
     *
     * @param view
     */
    public void setWidthEquals(final View view) {
        view.post(new Runnable() {
            @Override
            public void run() {
                setWidth(view.getWidth());
                update();
            }
        });
    }

    /**
     * 让背景变暗
     *
     * @param dimmerBackground
     */
    public void setDimmerBackground(boolean dimmerBackground) {
        mDimmerBackground = dimmerBackground;
    }

    public void setData(List<T> items) {
        mAdapter.setNewData(items);
    }

    public void setData(List<T> items, int selectIndex) {
        setData(items);
        selectItem(selectIndex);
    }

    public void setData(T[] items) {
        if (items != null) {
            mAdapter.setNewData(Arrays.asList(items));
        } else {
            mAdapter.setNewData(null);
        }
    }

    public void setData(T[] items, int selectIndex) {
        setData(items);
        selectItem(selectIndex);
    }

    @Override
    public void showAsDropDown(View anchor) {
        super.showAsDropDown(anchor, 0, mDefaultMargin);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        super.showAsDropDown(anchor, xoff, yoff, gravity);
        if (mDimmerBackground) {
            setBackground(true);
        }
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        if (mDimmerBackground) {
            setBackground(true);
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (mDimmerBackground) {
            setBackground(false);
        }
    }

    public void dealyDismiss(final long dealy) {
        mListView.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, dealy);
    }

    private void setBackground(boolean dimmer) {

        Window window = ((Activity) mContext).getWindow();

        WindowManager.LayoutParams mLayoutParams = window.getAttributes();
        mLayoutParams.alpha = dimmer ? 0.8f : 1f;
        window.setAttributes(mLayoutParams);
    }
}

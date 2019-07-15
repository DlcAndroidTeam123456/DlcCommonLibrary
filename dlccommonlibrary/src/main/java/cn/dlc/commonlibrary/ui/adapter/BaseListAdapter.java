package cn.dlc.commonlibrary.ui.adapter;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/12 0012.
 */

public abstract class BaseListAdapter<T> extends BaseAdapter {

    protected ArrayList<T> mData;

    public BaseListAdapter() {
        mData = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mData.size() == 0 ? 0 : mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<T> getData() {
        return mData;
    }

    public void setNewData(List<? extends T> data) {
        mData.clear();
        if (data != null) {
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }

    /**
     * 追加数据
     *
     * @param data
     */
    public void appendData(List<? extends T> data) {
        if (data != null && data.size() > 0) {
            mData.addAll(data);
            notifyDataSetChanged();
        }
    }

    /**
     * 添加单个数据
     *
     * @param data
     */
    public void addData(T data) {
        if (data != null) {
            mData.add(data);
            notifyDataSetChanged();
        }
    }

    public void delete(int position) {
        mData.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ListViewHolder holder;
        if (convertView != null) {
            holder = (ListViewHolder) convertView.getTag();
        } else {
            int viewType = getItemViewType(position);
            convertView = LayoutInflater.from(parent.getContext())
                .inflate(getItemLayoutId(viewType), parent, false);
            holder = new ListViewHolder(convertView);
            setChildViewListener(holder, viewType);
            convertView.setTag(holder);
        }
        holder.bindPosition(position);
        inflateItem(holder, position);
        return convertView;
    }

    /**
     * 设置子控件的监听事件，可以通过{@link ListViewHolder#getPosition()}拿到item的位置
     *
     * @param holder
     * @param viewType
     */
    public void setChildViewListener(ListViewHolder holder, int viewType) {
        // 默认空实现
    }

    /**
     * 填充布局
     *
     * @param holder
     * @param position
     */
    protected abstract void inflateItem(ListViewHolder holder, int position);

    /**
     * 获取item布局
     *
     * @return
     */
    public abstract int getItemLayoutId(int viewType);

    public static class ListViewHolder {

        private SparseArray<View> mViewArray;
        public View itemView;
        public int position;

        public ListViewHolder(View itemView) {
            this.itemView = itemView;
            mViewArray = new SparseArray<>();
        }

        public void bindPosition(int position) {
            this.position = position;
        }

        public int getPosition() {
            return position;
        }

        public <V extends View> V getView(int resId) {
            View view = mViewArray.get(resId);
            if (view == null) {
                view = itemView.findViewById(resId);
                mViewArray.put(resId, view);
            }
            return (V) view;
        }

        public void setText(int resId, CharSequence text) {
            TextView textView = getView(resId);
            textView.setText(text);
        }

        public TextView getText(int id) {
            return getView(id);
        }

        public ImageView getImage(int id) {
            return getView(id);
        }
    }
}

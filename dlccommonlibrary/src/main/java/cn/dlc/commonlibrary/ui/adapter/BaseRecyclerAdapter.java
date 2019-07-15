package cn.dlc.commonlibrary.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 2017/5/25.
 */

public abstract class BaseRecyclerAdapter<T>
    extends RecyclerView.Adapter<BaseRecyclerAdapter.CommonHolder> {

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public interface OnItemClickListener {
        void onItemClick(ViewGroup parent, CommonHolder holder, int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(ViewGroup parent, CommonHolder holder, int position);
    }

    protected final ArrayList<T> mData;

    public BaseRecyclerAdapter() {
        mData = new ArrayList<>();
    }

    @Override
    public CommonHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        final CommonHolder commonHolder = createCommonHolder(parent, viewType);

        View view = commonHolder.itemView;

        setChildViewListener(commonHolder, viewType);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null
                    && commonHolder.getAdapterPosition() != RecyclerView.NO_POSITION) {

                    mOnItemClickListener.onItemClick(parent, commonHolder,
                        commonHolder.getAdapterPosition());
                }
            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                if (mOnItemLongClickListener != null
                    && commonHolder.getAdapterPosition() != RecyclerView.NO_POSITION) {

                    return mOnItemLongClickListener.onItemLongClick(parent, commonHolder,
                        commonHolder.getAdapterPosition());
                } else {
                    return false;
                }
            }
        });

        return commonHolder;
    }

    /**
     * 创建通用Holder，子类可以重写这个方法
     *
     * @param parent
     * @param viewType
     * @return
     */
    public CommonHolder createCommonHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(getItemLayoutId(viewType), parent, false);
        return new CommonHolder(view);
    }

    /**
     * 设置子控件的监听事件，可以通过{@link RecyclerView.ViewHolder#getAdapterPosition()}拿到item的位置
     *
     * @param holder
     */
    public void setChildViewListener(CommonHolder holder, int viewType) {
        // 默认空实现

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public List<T> getData() {
        return mData;
    }

    /**
     * 获取item布局
     *
     * @return
     */
    public abstract int getItemLayoutId(int viewType);

    /**
     * 删除数据
     *
     * @param position
     */
    public void delete(int position) {
        mData.remove(position);
        notifyDataSetChanged();
    }

    /**
     * 获取item
     *
     * @param position
     * @return
     */
    public T getItem(int position) {
        return mData.get(position);
    }

    /**
     * 设置新数据
     *
     * @param data
     */
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

    /**
     * 设置点击事件
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    /**
     * 设置长按事件
     *
     * @param onItemLongClickListener
     */
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }

    public static class CommonHolder extends RecyclerView.ViewHolder {

        private SparseArray<View> mViews;

        public CommonHolder(View itemView) {
            super(itemView);
            mViews = new SparseArray<>();
        }

        public Context getContext() {
            return itemView.getContext();
        }

        public Resources getResource() {
            return itemView.getResources();
        }

        public <V extends View> V getView(int id) {
            View view = mViews.get(id);
            if (view == null) {
                view = itemView.findViewById(id);
                mViews.put(id, view);
            }
            return (V) view;
        }

        public void setText(int id, CharSequence text) {
            ((TextView) getView(id)).setText(text);
        }

        public TextView getText(int id) {
            return getView(id);
        }

        public ImageView getImage(int id) {
            return getView(id);
        }
    }
}


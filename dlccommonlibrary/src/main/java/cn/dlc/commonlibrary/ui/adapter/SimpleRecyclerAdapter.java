package cn.dlc.commonlibrary.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

/**
 * 只封装了点击事件的Adapter，需要自己写ViewHolder（可以用黄油刀）
 */

public abstract class SimpleRecyclerAdapter<T, VH extends RecyclerView.ViewHolder>
    extends RecyclerView.Adapter<VH> {

    private OnItemClickListener<VH> mOnItemClickListener;
    private OnItemLongClickListener<VH> mOnItemLongClickListener;

    public interface OnItemClickListener<VH> {
        void onItemClick(ViewGroup parent, VH holder, int position);
    }

    public interface OnItemLongClickListener<VH> {
        boolean onItemLongClick(ViewGroup parent, VH holder, int position);
    }

    protected final ArrayList<T> mData;

    public SimpleRecyclerAdapter() {
        mData = new ArrayList<>();
    }

    @Override
    public VH onCreateViewHolder(final ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
            .inflate(getItemLayoutId(viewType), parent, false);

        final VH holder = createViewHolderInstance(view, viewType);

        setChildViewListener(holder, viewType);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (mOnItemClickListener != null && position != RecyclerView.NO_POSITION) {
                    mOnItemClickListener.onItemClick(parent, holder, position);
                }
            }
        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                int position = holder.getAdapterPosition();
                if (mOnItemLongClickListener != null && position != RecyclerView.NO_POSITION) {
                    return mOnItemLongClickListener.onItemLongClick(parent, holder, position);
                } else {
                    return false;
                }
            }
        });

        return holder;
    }

    /**
     * 创建ViewHolder实例
     *
     * @param itemView 已经填充好布局的itemView实例
     * @param viewType itemView的类型
     * @return
     */
    public abstract VH createViewHolderInstance(View itemView, int viewType);

    /**
     * 设置子控件的监听事件，可以通过{@link RecyclerView.ViewHolder#getAdapterPosition()}拿到item的位置
     *
     * @param holder
     */
    public void setChildViewListener(VH holder, int viewType) {
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
    public void setOnItemClickListener(OnItemClickListener<VH> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    /**
     * 设置长按事件
     *
     * @param onItemLongClickListener
     */
    public void setOnItemLongClickListener(OnItemLongClickListener<VH> onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }
}


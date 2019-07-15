package cn.dlc.commonlibrary.utils.rv_tool;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 为RecyclerView绑定无数据时的布局的辅助类，根据Adapter中数据的数量，显示/隐藏空白布局
 */
public class EmptyRecyclerView extends RecyclerView.AdapterDataObserver {

    private RecyclerView.Adapter mAdapter;
    private View mEmptyView;
    private int mHeaderAmount;
    private int mFooterAmount;

    public EmptyRecyclerView(View emptyView) {
        this(emptyView, 0, 0);
    }

    public EmptyRecyclerView(View emptyView, int headerAmount, int footerAmount) {
        mEmptyView = emptyView;
        mHeaderAmount = headerAmount;
        mFooterAmount = footerAmount;
    }

    /**
     * 绑定Adapter
     *
     * @param adapter
     */
    public void bindAdapter(RecyclerView.Adapter adapter) {

        unbindAdapter();
        mAdapter = adapter;
        mAdapter.registerAdapterDataObserver(this);
        checkIfEmpty();
    }

    /**
     * 解绑Adapter
     */
    public void unbindAdapter() {
        if (mAdapter != null) {
            try {
                mAdapter.unregisterAdapterDataObserver(this);
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
    }

    /**
     * 绑定RecyclerView（的Adapter）和空白布局
     *
     * @param recyclerView
     * @param emptyView
     * @return
     */
    public static EmptyRecyclerView bind(RecyclerView recyclerView, View emptyView) {
        return bind(recyclerView, emptyView, 0, 0);
    }

    /**
     * 绑定RecyclerView（的Adapter）和空白布局
     *
     * @param recyclerView
     * @param emptyView
     * @param headerAmount
     * @param footerAmount
     * @return
     */
    public static EmptyRecyclerView bind(RecyclerView recyclerView, View emptyView,
        int headerAmount, int footerAmount) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter == null) {
            throw new IllegalStateException("草泥马，RecyclerView必须先setAdapter()");
        }
        EmptyRecyclerView emptyRecyclerView =
            new EmptyRecyclerView(emptyView, headerAmount, footerAmount);
        emptyRecyclerView.bindAdapter(adapter);
        return emptyRecyclerView;
    }

    /**
     * 设置空白布局
     *
     * @param emptyView
     */
    public void setEmptyView(View emptyView) {
        mEmptyView = emptyView;
        checkIfEmpty();
    }

    @Override
    public void onChanged() {
        checkIfEmpty();
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
        checkIfEmpty();
    }

    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
        checkIfEmpty();
    }

    private void checkIfEmpty() {
        if (mEmptyView != null && mAdapter != null) {
            boolean hideEmpty = mAdapter.getItemCount() > mHeaderAmount + mFooterAmount;
            mEmptyView.setVisibility(hideEmpty ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * 强制隐藏空白布局
     *
     * @return
     */
    public EmptyRecyclerView hindEmptyView() {
        if (mEmptyView != null) {
            mEmptyView.setVisibility(View.GONE);
        }
        return this;
    }

    /**
     * 强制显示空白布局
     *
     * @return
     */
    public EmptyRecyclerView showEmptyView() {
        if (mEmptyView != null) {
            mEmptyView.setVisibility(View.VISIBLE);
        }
        return this;
    }
}

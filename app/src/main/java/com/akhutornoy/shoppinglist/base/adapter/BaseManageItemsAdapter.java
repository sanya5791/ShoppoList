package com.akhutornoy.shoppinglist.base.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.base.model.ItemModel;

import java.util.List;

public class BaseManageItemsAdapter<T extends ItemModel> extends RecyclerView.Adapter<BaseManageItemsAdapter.ItemViewHolder> {

    private OnItemClickListener mOnItemClickListener;
    private List<T> mItems;
    private Mode mMode;

    public interface OnItemClickListener<T0 extends ItemModel> {
        void onEditItemClicked(T0 shopModel);

        void onDeleteItemClicked(T0 shopModel);
    }

    public enum Mode {EDIT, DELETE, RESORT}

    public BaseManageItemsAdapter(OnItemClickListener onItemClickListener, Mode mode) {
        mOnItemClickListener = onItemClickListener;
        this.mMode = mode;
    }

    public BaseManageItemsAdapter(OnItemClickListener onItemClickListener) {
        this(onItemClickListener, Mode.EDIT);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_manage_item, parent, false);
        return new ItemViewHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.bind(mItems.get(position), mMode);
    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size() : 0;
    }

    public void setProducts(List<T> products) {
        this.mItems = products;
        notifyDataSetChanged();
    }

    public void setMode(Mode mode) {
        mMode = mode;
        notifyDataSetChanged();
    }

    static class ItemViewHolder<T1 extends ItemModel> extends RecyclerView.ViewHolder {
        private OnItemClickListener mOnItemClickListener;
        private View mRootView;
        private TextView mTvShopName;
        private ImageView mIvDelete;
        private ImageView mIvResort;

        public ItemViewHolder(View view, OnItemClickListener onItemClickListener) {
            super(view);
            mOnItemClickListener = onItemClickListener;
            mRootView = view;
            mTvShopName = view.findViewById(R.id.tv_name);
            mIvDelete = view.findViewById(R.id.iv_delete);
            mIvResort = view.findViewById(R.id.iv_resort);
        }

        private void bind(T1 itemModel, Mode mode) {
            mTvShopName.setText(itemModel.getName());
            setListeners(itemModel, mode);
            setIconVisibility(mode);
        }

        private void setListeners(T1 itemModel, Mode mode) {
            mRootView.setOnClickListener(Mode.EDIT == mode
                    ? v -> mOnItemClickListener.onEditItemClicked(itemModel)
                    : null);
            mIvDelete.setOnClickListener(v -> mOnItemClickListener.onDeleteItemClicked(itemModel));
        }

        private void setIconVisibility(Mode mode) {
            mIvDelete.setVisibility(Mode.DELETE == mode ? View.VISIBLE : View.GONE);
            mIvResort.setVisibility(Mode.RESORT == mode ? View.VISIBLE : View.GONE);
        }
    }
}

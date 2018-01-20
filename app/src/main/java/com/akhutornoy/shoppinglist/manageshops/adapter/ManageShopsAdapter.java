package com.akhutornoy.shoppinglist.manageshops.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.manageshops.model.ManageShopModel;

import java.util.List;

public class ManageShopsAdapter extends RecyclerView.Adapter<ManageShopsAdapter.ShopViewHolder> {

    private OnShopClickListener mOnShopClickListener;
    private List<ManageShopModel> mShops;
    private Mode mMode;

    public interface OnShopClickListener {
        void onEditShopClicked(ManageShopModel shopModel);

        void onDeleteShopClicked(ManageShopModel shopModel);
    }

    public enum Mode {EDIT, DELETE, RESORT}

    public ManageShopsAdapter(OnShopClickListener onShopClickListener,  Mode mode) {
        mOnShopClickListener = onShopClickListener;
        this.mMode = mode;
    }

    public ManageShopsAdapter(OnShopClickListener onShopClickListener) {
        this(onShopClickListener, Mode.EDIT);
    }

    @Override
    public ShopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_manage_shop, parent, false);
        return new ShopViewHolder(view, mOnShopClickListener);
    }

    @Override
    public void onBindViewHolder(ShopViewHolder holder, int position) {
        holder.bind(mShops.get(position), mMode);
    }

    @Override
    public int getItemCount() {
        return mShops != null ? mShops.size() : 0;
    }

    public void setProducts(List<ManageShopModel> products) {
        this.mShops = products;
        notifyDataSetChanged();
    }

    public void setMode(Mode mode) {
        mMode = mode;
        notifyDataSetChanged();
    }

    static class ShopViewHolder extends RecyclerView.ViewHolder {
        private OnShopClickListener mOnShopClickListener;
        private View mRootView;
        private TextView mTvShopName;
        private ImageView mIvDelete;
        private ImageView mIvResort;

        public ShopViewHolder(View view, OnShopClickListener onShopClickListener) {
            super(view);
            mOnShopClickListener = onShopClickListener;
            mRootView = view;
            mTvShopName = view.findViewById(R.id.tv_name);
            mIvDelete = view.findViewById(R.id.iv_delete);
            mIvResort = view.findViewById(R.id.iv_resort);
        }

        private void bind(ManageShopModel shopModel, Mode mode) {
            mTvShopName.setText(shopModel.getName());
            setListeners(shopModel, mode);
            setIconVisibility(mode);
        }

        private void setListeners(ManageShopModel shopModel, Mode mode) {
            mRootView.setOnClickListener(Mode.EDIT == mode
                    ? v -> mOnShopClickListener.onEditShopClicked(shopModel)
                    : null);
            mIvDelete.setOnClickListener(v -> mOnShopClickListener.onDeleteShopClicked(shopModel));
        }

        private void setIconVisibility(Mode mode) {
            mIvDelete.setVisibility(Mode.DELETE == mode ? View.VISIBLE : View.GONE);
            mIvResort.setVisibility(Mode.RESORT == mode ? View.VISIBLE : View.GONE);
        }
    }
}

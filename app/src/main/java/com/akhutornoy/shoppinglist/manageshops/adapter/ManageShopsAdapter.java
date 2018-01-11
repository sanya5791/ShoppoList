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
    private List<ManageShopModel> mProducts;

    public interface OnShopClickListener {
        void onEditShopClicked(ManageShopModel shopModel);
        void onDeleteShopClicked(ManageShopModel shopModel);
    }

    public ManageShopsAdapter(OnShopClickListener onShopClickListener) {
        mOnShopClickListener = onShopClickListener;
    }

    @Override
    public ShopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_manage_shop, parent, false);
        return new ShopViewHolder(view, mOnShopClickListener);
    }

    @Override
    public void onBindViewHolder(ShopViewHolder holder, int position) {
        holder.bind(mProducts.get(position));
    }

    @Override
    public int getItemCount() {
        return mProducts != null ? mProducts.size() : 0;
    }

    public void setProducts(List<ManageShopModel> products) {
        this.mProducts = products;
        notifyDataSetChanged();
    }

    static class ShopViewHolder extends RecyclerView.ViewHolder {
        private OnShopClickListener mOnShopClickListener;
        private TextView mTvShopName;
        private ImageView mIvEdit;
        private ImageView mIvDelete;
        private ImageView mIvResort;

        public ShopViewHolder(View view, OnShopClickListener onShopClickListener) {
            super(view);
            mOnShopClickListener = onShopClickListener;
            mTvShopName = view.findViewById(R.id.tv_name);
            mIvEdit = view.findViewById(R.id.iv_edit);
            mIvDelete = view.findViewById(R.id.iv_delete);
            mIvResort = view.findViewById(R.id.iv_resort);
        }

        private void bind(ManageShopModel shopModel) {
            mTvShopName.setText(shopModel.getName());
            mIvEdit.setOnClickListener(v -> mOnShopClickListener.onEditShopClicked(shopModel));
            mIvDelete.setOnClickListener(v -> mOnShopClickListener.onDeleteShopClicked(shopModel));
        }
    }
}

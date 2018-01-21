package com.akhutornoy.shoppinglist.shops.displayshops.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.base.ValueCallback;
import com.akhutornoy.shoppinglist.base.model.ItemModel;

import java.util.List;

public class ShopsAdapter extends RecyclerView.Adapter <ShopsAdapter.ShopTypeViewHolder> {
    private final ValueCallback<ItemModel> mCallback;
    private List<ItemModel> mShopTypes;

    public ShopsAdapter(ValueCallback<ItemModel> listener) {
        mCallback = listener;
    }

    @Override
    public ShopTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shop_type, parent, false);
        return new ShopTypeViewHolder(v, mCallback);
    }

    @Override
    public void onBindViewHolder(ShopTypeViewHolder holder, int position) {
        holder.bind(mShopTypes.get(position));
    }

    @Override
    public int getItemCount() {
        return mShopTypes != null ? mShopTypes.size() : 0;
    }

    public void setShops(List<ItemModel> shopTypes) {
        mShopTypes = shopTypes;
        notifyDataSetChanged();
    }

    static class ShopTypeViewHolder extends RecyclerView.ViewHolder {
        private final ValueCallback<ItemModel> mCallback;
        private TextView mTvName;

        ShopTypeViewHolder(View itemView, ValueCallback<ItemModel> listener) {
            super(itemView);
            mCallback = listener;
            mTvName = itemView.findViewById(R.id.tv_name);
        }

        private void bind(ItemModel shopName) {
            mTvName.setText(shopName.getName());
            mTvName.setOnClickListener(v -> mCallback.select(shopName));
        }
    }
}

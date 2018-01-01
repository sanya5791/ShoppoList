package com.akhutornoy.shoppinglist.shops;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.base.ValueCallback;

import java.util.List;

public class ShopsAdapter extends RecyclerView.Adapter <ShopsAdapter.ShopTypeViewHolder> {
    private final ValueCallback<ShopModel> mCallback;
    private List<ShopModel> mShopTypes;

    public ShopsAdapter(ValueCallback<ShopModel> listener) {
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

    public void setShopTypes(List<ShopModel> shopTypes) {
        mShopTypes = shopTypes;
        notifyDataSetChanged();
    }

    public static class ShopTypeViewHolder extends RecyclerView.ViewHolder {
        private final ValueCallback<ShopModel> mCallback;
        private TextView mTvName;

        public ShopTypeViewHolder(View itemView, ValueCallback<ShopModel> listener) {
            super(itemView);
            mCallback = listener;
            mTvName = itemView.findViewById(R.id.tv_name);
        }

        private void bind(ShopModel shopName) {
            mTvName.setText(shopName.getName());
            mTvName.setOnClickListener(v -> mCallback.select(shopName));
        }
    }
}

package com.akhutornoy.shoppinglist.shops.displayshops.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.base.ValueCallback;
import com.akhutornoy.shoppinglist.shops.model.ShopModel;

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
        return new ShopTypeViewHolder(v, this::onShopSelected);
    }

    protected void onShopSelected(ShopModel selectedType) {
        for (int i = 0; i < mShopTypes.size(); i++) {
            ShopModel type = mShopTypes.get(i);
            if (type.equals(selectedType)) {
                type.isChecked(true);
            } else {
                type.isChecked(false);
            }
            notifyItemChanged(i);
        }
        mCallback.select(selectedType);
    }

    @Override
    public void onBindViewHolder(ShopTypeViewHolder holder, int position) {
        holder.bind(mShopTypes.get(position));
    }

    @Override
    public int getItemCount() {
        return mShopTypes != null ? mShopTypes.size() : 0;
    }

    public void setShops(List<ShopModel> shopTypes) {
        mShopTypes = shopTypes;
        notifyDataSetChanged();
    }

    static class ShopTypeViewHolder extends RecyclerView.ViewHolder {
        private final ValueCallback<ShopModel> mCallback;
        private TextView mTvName;
        private ImageView mChecked;

        ShopTypeViewHolder(View itemView, ValueCallback<ShopModel> listener) {
            super(itemView);
            mCallback = listener;
            mTvName = itemView.findViewById(R.id.tv_name);
            mChecked = itemView.findViewById(R.id.iv_checked);
        }

        private void bind(ShopModel shop) {
            mTvName.setText(shop.getName());
            mTvName.setOnClickListener(v -> mCallback.select(shop));
            mChecked.setImageResource(shop.isChecked() ? android.R.drawable.presence_online : 0);
        }
    }
}

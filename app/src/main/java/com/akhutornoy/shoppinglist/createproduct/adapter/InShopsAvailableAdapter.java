package com.akhutornoy.shoppinglist.createproduct.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.createproduct.model.ShopModel;

import java.util.ArrayList;
import java.util.List;

public class InShopsAvailableAdapter extends RecyclerView.Adapter<InShopsAvailableAdapter.ShopViewHolder> {

    private List<ShopModel> mShops;

    @Override
    public ShopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shop_checkable, parent, false);
        return new ShopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShopViewHolder holder, int position) {
        holder.bind(mShops.get(position));
    }

    @Override
    public int getItemCount() {
        return mShops != null ? mShops.size() : 0;
    }

    public void setShops(List<ShopModel> shops) {
        this.mShops = shops;
        notifyDataSetChanged();
    }

    public List<ShopModel> getSelectedShops() {
        List<ShopModel> selectedShops = new ArrayList<>();
        for (ShopModel shop : mShops) {
            if (shop.isChecked()) {
                selectedShops.add(shop);
            }
        }
        return selectedShops;
    }

    static class ShopViewHolder extends RecyclerView.ViewHolder {
        private CheckBox mChkShop;

        private ShopViewHolder(View view) {
            super(view);
            mChkShop = view.findViewById(R.id.chk_name);
        }

        private void bind(ShopModel shop) {
            mChkShop.setText(shop.getName());
            mChkShop.setChecked(shop.isChecked());
            mChkShop.setOnCheckedChangeListener((button, isChecked) -> shop.setChecked(isChecked));
        }
    }
}

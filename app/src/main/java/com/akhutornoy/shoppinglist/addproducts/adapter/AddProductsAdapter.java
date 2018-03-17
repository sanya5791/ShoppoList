package com.akhutornoy.shoppinglist.addproducts.adapter;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.addproducts.model.AddProductModel;

import java.util.ArrayList;
import java.util.List;

public class AddProductsAdapter extends RecyclerView.Adapter<AddProductsAdapter.ProductViewHolder> {
    private final OnEditQuantityListener mCallback;
    private List<AddProductModel> mProducts;

    public AddProductsAdapter(OnEditQuantityListener onEditQuantityListener) {
        this.mCallback = onEditQuantityListener;
    }

    public interface OnEditQuantityListener {
        void onEditQuantityListener(AddProductModel addProductModel);
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_to_buy_product, parent, false);
        return new ProductViewHolder(view, mCallback);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        holder.bind(mProducts.get(position));
    }

    @Override
    public int getItemCount() {
        return mProducts != null ? mProducts.size() : 0;
    }

    public void setProducts(List<AddProductModel> products) {
        this.mProducts = products;
        notifyDataSetChanged();
    }

    public void updateProductQuantity(AddProductModel newItem) {
        for (int i = 0; i < mProducts.size(); i++) {
            AddProductModel item = mProducts.get(i);
            if (item.getName().equals(newItem.getName())) {
                item.setQuantity(item.getQuantity());
                notifyItemChanged(i);
                return;
            }
        }
        throw new IllegalArgumentException(String.format("Can't find %s in list to update", newItem));
    }

    public List<AddProductModel> getSelected() {
        List<AddProductModel> selectedProducts = new ArrayList<>();
        for (AddProductModel product : mProducts) {
            if (product.isAdded()) {
                selectedProducts.add(product);
            }
        }
        return selectedProducts;
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        private final OnEditQuantityListener mCallback;
        private AppCompatCheckBox mCbIsBought;
        private TextView mTvUnit;
        private TextView mTvQuantity;

        private ProductViewHolder(View view, OnEditQuantityListener onEditQuantityListener) {
            super(view);
            mCallback = onEditQuantityListener;
            mCbIsBought = view.findViewById(R.id.checkbox);
            mTvUnit = view.findViewById(R.id.tv_unit);
            mTvQuantity = view.findViewById(R.id.tv_quantity);
        }

        private void bind(AddProductModel product) {
            mTvUnit.setText(product.getUnit());
            mTvQuantity.setText(product.getQuantity());

            View.OnClickListener editQuantityListener = v -> {
                if (mCbIsBought.isChecked()) {
                    mCallback.onEditQuantityListener(product);
                }
            };
            mTvUnit.setOnClickListener(editQuantityListener);
            mTvQuantity.setOnClickListener(editQuantityListener);

            mCbIsBought.setChecked(product.isAdded());
            mCbIsBought.setText(product.getName(), TextView.BufferType.SPANNABLE);
            mCbIsBought.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                product.setIsAdded(isChecked);
                if (isChecked) {
                    makeBlinkEffect(mTvQuantity);
                    makeBlinkEffect(mTvUnit);
                }
            });
        }

        private void makeBlinkEffect(TextView view) {
            int accentColor = ContextCompat.getColor(view.getContext(), R.color.colorAccent);
            ObjectAnimator anim = ObjectAnimator.ofInt(view, "textColor",
                    Color.BLACK,
                    accentColor,
                    Color.BLACK);
            anim.setDuration(500);
            anim.setEvaluator(new ArgbEvaluator());
            anim.setRepeatMode(ValueAnimator.REVERSE);
            anim.setRepeatCount(1);
            anim.start();
        }
    }
}

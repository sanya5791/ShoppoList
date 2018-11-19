package com.akhutornoy.shoppinglist.ui.addproducts.adapter;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.ui.addproducts.model.AddProductModel;
import com.akhutornoy.shoppinglist.ui.base.ValueCallback;

import java.util.ArrayList;
import java.util.List;

public class AddProductsAdapter extends RecyclerView.Adapter<AddProductsAdapter.ProductViewHolder> {
    private final OnEditQuantityListener mOnEditQuantityCallback;
    private final ValueCallback<AddProductModel> mOnEditProductCallback;
    private List<AddProductModel> mProducts;

    public AddProductsAdapter(OnEditQuantityListener onEditQuantityListener,
                              ValueCallback<AddProductModel> mOnEditProductListener) {
        this.mOnEditQuantityCallback = onEditQuantityListener;
        this.mOnEditProductCallback = mOnEditProductListener;
    }

    public interface OnEditQuantityListener {
        void onEditQuantityListener(AddProductModel addProductModel);
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_to_buy_product, parent, false);
        return new ProductViewHolder(view, mOnEditQuantityCallback, mOnEditProductCallback);
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
        private final OnEditQuantityListener mOnEditQuantityCallback;
        private ValueCallback<AddProductModel> mOnEditProductCallback;
        private AddProductModel mProduct;
        private AppCompatCheckBox mCheckBox;
        private TextView mTvUnit;
        private TextView mTvQuantity;

        private ProductViewHolder(View view, OnEditQuantityListener onEditQuantityListener,
                                  ValueCallback<AddProductModel> onEditProductListener) {
            super(view);
            mOnEditQuantityCallback = onEditQuantityListener;
            mOnEditProductCallback = onEditProductListener;
            mCheckBox = view.findViewById(R.id.checkbox);
            mTvUnit = view.findViewById(R.id.tv_unit);
            mTvQuantity = view.findViewById(R.id.tv_quantity);
        }

        private void bind(AddProductModel product) {
            mProduct = product;
            mTvUnit.setText(product.getUnit());
            mTvQuantity.setText(product.getQuantity());

            View.OnClickListener editQuantityListener = v -> {
                if (mCheckBox.isChecked()) {
                    mOnEditQuantityCallback.onEditQuantityListener(product);
                }
            };
            mTvUnit.setOnClickListener(editQuantityListener);
            mTvQuantity.setOnClickListener(editQuantityListener);

            mCheckBox.setChecked(product.isAdded());
            mCheckBox.setText(product.getName(), TextView.BufferType.SPANNABLE);
            mCheckBox.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                product.setIsAdded(isChecked);
                if (isChecked) {
                    makeBlinkEffect(mTvQuantity);
                    makeBlinkEffect(mTvUnit);
                }
            });
            mCheckBox.setOnLongClickListener(this::showContextMenu);
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

        private boolean showContextMenu(View view) {
            PopupMenu popup = new PopupMenu(view.getContext(), view);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_edit, popup.getMenu());
            popup.setOnMenuItemClickListener(this::onMenuItemClicked);
            popup.show();

            return true;
        }

        private boolean onMenuItemClicked(MenuItem menuItem) {
            if (menuItem.getItemId() == R.id.menu_edit) {
                mOnEditProductCallback.select(mProduct);
                return true;
            }
            return false;
        }
    }
}

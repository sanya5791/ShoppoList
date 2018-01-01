package com.akhutornoy.shoppinglist.tobuy;

import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.akhutornoy.shoppinglist.R;

import java.util.List;

public class ToBuyProductsAdapter extends RecyclerView.Adapter<ToBuyProductsAdapter.ProductViewHolder> {

    private List<ToBuyProductModel> mProducts;

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_to_buy_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        holder.bind(mProducts.get(position));
    }

    @Override
    public int getItemCount() {
        return mProducts != null ? mProducts.size() : 0;
    }

    public void setProducts(List<ToBuyProductModel> products) {
        this.mProducts = products;
        notifyDataSetChanged();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        private AppCompatCheckBox mCbIsBought;
        private StrikethroughSpan mStrikethroughSpan;

        private ProductViewHolder(View view) {
            super(view);
            mCbIsBought = view.findViewById(R.id.checkbox);
            mStrikethroughSpan = new StrikethroughSpan();
        }

        private void bind(ToBuyProductModel product) {
            mCbIsBought.setText(product.getName(), TextView.BufferType.SPANNABLE);
            mCbIsBought.setChecked(product.isBought());
            mCbIsBought.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                product.setBought(isChecked);
                setTextStyle(compoundButton, isChecked);
            });
        }

        private void setTextStyle(CompoundButton button, boolean isChecked) {
            SpannableString ss = (SpannableString) button.getText();
            if (isChecked) {
                ss.setSpan(mStrikethroughSpan, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                ss.removeSpan(mStrikethroughSpan);
            }
        }
    }
}

package com.akhutornoy.shoppinglist.addproducts;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akhutornoy.shoppinglist.R;

import java.util.List;

public class AddProductsAdapter extends RecyclerView.Adapter<AddProductsAdapter.ProductViewHolder> {

    private List<AddProductModel> mProducts;

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

    public void setProducts(List<AddProductModel> products) {
        this.mProducts = products;
        notifyDataSetChanged();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        private AppCompatCheckBox mCbIsBought;
        private TextView mTvUnit;
        private TextView mTvQuantity;

        private ProductViewHolder(View view) {
            super(view);
            mCbIsBought = view.findViewById(R.id.checkbox);
            mTvUnit = view.findViewById(R.id.tv_unit);
            mTvQuantity = view.findViewById(R.id.tv_quantity);
        }

        private void bind(AddProductModel product) {
            mTvUnit.setText(product.getUnit());
            mTvQuantity.setText(product.getQuantity());

            mCbIsBought.setChecked(product.isAdded());
            mCbIsBought.setText(product.getName(), TextView.BufferType.SPANNABLE);
            setBackground(product.isAdded());
            mCbIsBought.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                product.setIsAdded(isChecked);
                setBackground(isChecked);
            });
        }

        private void setBackground(boolean isChecked) {
            int color = ContextCompat.getColor(itemView.getContext(), isChecked ?
                    R.color.colorDivider : android.R.color.white);

            itemView.setBackgroundColor(color);
        }
    }
}

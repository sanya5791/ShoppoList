package com.akhutornoy.shoppinglist.createproduct.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.base.ValueCallback;
import com.akhutornoy.shoppinglist.createproduct.model.ProductTypeModel;

import java.util.List;

public class SelectProductTypeAdapter extends RecyclerView.Adapter<SelectProductTypeAdapter.ProductViewHolder> {

    private List<ProductTypeModel> mTypes;
    private int mSelectedTypeIndex;

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_add_type, parent, false);
        return new ProductViewHolder(view, this::onTypeSelected);
    }

    private void onTypeSelected(ProductTypeModel selectedType) {
        for (int i = 0; i < mTypes.size(); i++) {
            ProductTypeModel type = mTypes.get(i);
            if (type.equals(selectedType)) {
                mSelectedTypeIndex = i;
                type.setChecked(true);
            } else {
                type.setChecked(false);
            }
            notifyItemChanged(i);
        }
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        holder.bind(mTypes.get(position));
    }

    @Override
    public int getItemCount() {
        return mTypes != null ? mTypes.size() : 0;
    }

    public void setTypes(List<ProductTypeModel> types) {
        this.mTypes = types;
        notifyDataSetChanged();
    }

    public ProductTypeModel getSelectedType() {
        return mTypes.get(mSelectedTypeIndex);
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        private ValueCallback<ProductTypeModel> mCallback;
        private TextView mTvUnit;

        private ProductViewHolder(View view, ValueCallback<ProductTypeModel> listener) {
            super(view);
            mCallback = listener;
            mTvUnit = view.findViewById(R.id.tv_unit);
        }

        private void bind(ProductTypeModel type) {
            mTvUnit.setText(type.getName());
            mTvUnit.setCompoundDrawablesWithIntrinsicBounds(type.isChecked() ? android.R.drawable.presence_online : 0,
                    0, 0, 0);
            mTvUnit.setOnClickListener(view -> mCallback.select(type));
        }
    }
}

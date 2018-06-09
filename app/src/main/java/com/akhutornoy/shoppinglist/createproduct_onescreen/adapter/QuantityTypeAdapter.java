package com.akhutornoy.shoppinglist.createproduct_onescreen.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.base.ValueCallback;
import com.akhutornoy.shoppinglist.createproduct_onescreen.model.CheckableItemModel;
import com.akhutornoy.shoppinglist.domain.MeasureType;

import java.util.ArrayList;
import java.util.List;

public class QuantityTypeAdapter extends RecyclerView.Adapter<QuantityTypeAdapter.ViewHolder> {

    private List<CheckableItemModel> items;

    public void setQuantityTypes(List<MeasureType> quantityTypes) {
        items = new ArrayList<>(quantityTypes.size());
        for (MeasureType quantityType : quantityTypes) {
            items.add(new CheckableItemModel(quantityType.getName()));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_create_product_string, parent, false);
        return new ViewHolder(view, this::onClicked);
    }

    private void onClicked(String clickedName) {
        for (int position = 0; position < items.size(); position++) {
            CheckableItemModel item = items.get(position);
            boolean isSelectionRequired = item.getName().equals(clickedName);
            if (isSelectionRequired == item.isChecked()) {
                continue;
            }
            item.setChecked(isSelectionRequired);
            notifyItemChanged(position);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setItem(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    /**
     * @return either name of selected QuantityType or empty string
     */
    public String getSelectedQuantityType() {
        for (CheckableItemModel item : items) {
            if (item.isChecked()) {
                return item.getName();
            }
        }
        return "";
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final CheckedTextView textView;

        ViewHolder(View itemView, ValueCallback<String> listener) {
            super(itemView);
            textView = (CheckedTextView) itemView;
            textView.setOnClickListener(ignored -> onClick(listener));
        }

        private void onClick(ValueCallback<String> callback) {
            if (textView.isChecked()) {
                return;
            }
            textView.setChecked(true);
            callback.select(textView.getText().toString());
        }

        private void setItem(CheckableItemModel item) {
            textView.setText(item.getName());
            textView.setChecked(item.isChecked());
        }
    }
}

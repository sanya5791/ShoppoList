package com.akhutornoy.shoppinglist.createproduct_onescreen.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.base.ValueCallback;
import com.akhutornoy.shoppinglist.createproduct_onescreen.model.CheckableItemModel;

import java.util.ArrayList;
import java.util.List;

public class ShopsAdapter extends RecyclerView.Adapter<ShopsAdapter.ViewHolder> {

    private List<CheckableItemModel> items;

    public void setShops(List<String> quantityTypes) {
        items = new ArrayList<>(quantityTypes.size());
        for (String quantityType : quantityTypes) {
            items.add(new CheckableItemModel(quantityType));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_create_product_string, parent, false);
        return new ViewHolder(view, this::onClicked);
    }

    private void onClicked(CheckableItemModel clickedName) {
        for (int position = 0; position < items.size(); position++) {
            CheckableItemModel item = items.get(position);
            if (item.getName().equals(clickedName.getName())) {
                item.setChecked(clickedName.isChecked());
                notifyItemChanged(position);
                break;
            }
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

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final CheckedTextView textView;

        ViewHolder(View itemView, ValueCallback<CheckableItemModel> listener) {
            super(itemView);
            textView = (CheckedTextView) itemView;
            textView.setOnClickListener(ignored -> onClick(listener));
        }

        private void onClick(ValueCallback<CheckableItemModel> callback) {
            boolean checkedState = !textView.isChecked();
//            textView.setChecked(checkedState);
            CheckableItemModel result = new CheckableItemModel(textView.getText().toString());
            result.setChecked(checkedState);
            callback.select(result);
        }

        private void setItem(CheckableItemModel item) {
            textView.setText(item.getName());
            textView.setChecked(item.isChecked());
        }
    }
}

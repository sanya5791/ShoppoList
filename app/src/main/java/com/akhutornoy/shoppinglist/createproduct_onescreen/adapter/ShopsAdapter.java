package com.akhutornoy.shoppinglist.createproduct_onescreen.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.base.ValueCallback;
import com.akhutornoy.shoppinglist.createproduct_onescreen.model.CheckableItemModel;
import com.akhutornoy.shoppinglist.domain.Shop;

import java.util.ArrayList;
import java.util.List;

public class ShopsAdapter extends RecyclerView.Adapter<ShopsAdapter.ViewHolder> {

    private List<CheckableItemModel> items;
    private final String allShopsTitle;
    private int allShopsPosition;

    public ShopsAdapter(Context context) {
        allShopsTitle = context.getString(R.string.all);
    }

    public void setShops(List<Shop> quantityTypes) {
        items = new ArrayList<>(quantityTypes.size());
        for (Shop quantityType : quantityTypes) {
            items.add(new CheckableItemModel(quantityType.getName()));
        }

        setAllShopsPosition();
    }

    private void setAllShopsPosition() {
        allShopsPosition = -1;
        for (int i = 0; i < items.size(); i++) {
            if (isAllShopsItem(items.get(i))) {
                allShopsPosition = i;
                break;
            }
        }
        if (allShopsPosition == -1) {
            throw new IllegalArgumentException(allShopsTitle + " should be one of shop items");
        }
    }

    private boolean isAllShopsItem(CheckableItemModel clickedName) {
        return allShopsTitle.equals(clickedName.getName());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_create_product_string, parent, false);

        return new ViewHolder(view, this::onClicked);
    }

    private void onClicked(CheckableItemModel clickedName) {
        if (isAllShopsItem(clickedName)) {
            checkAll(clickedName.isChecked());
            return;
        }

        for (int position = 0; position < items.size(); position++) {
            CheckableItemModel item = items.get(position);
            if (item.getName().equals(clickedName.getName())) {
                item.setChecked(clickedName.isChecked());
                notifyItemChanged(position);
                break;
            }
        }

        handleAllShopsButtonBoundState();
    }

    private void checkAll(boolean isChecked) {
        for (int position = 0; position < items.size(); position++) {
            CheckableItemModel item = items.get(position);
            item.setChecked(isChecked);
        }
        notifyDataSetChanged();
    }

    private void handleAllShopsButtonBoundState() {
        Boolean firstItemChecked = null;
        boolean isAllSameChecked = true;
        for (int i = 0; i < items.size(); i++) {
            if (allShopsPosition == i) {
                continue;
            }

            CheckableItemModel item = items.get(i);
            if (firstItemChecked == null) {
                firstItemChecked = item.isChecked();
                continue;
            }

            if (item.isChecked() != firstItemChecked) {
                isAllSameChecked = false;
                break;
            }
        }

        if (isAllSameChecked && firstItemChecked != null) {
            items.get(allShopsPosition).setChecked(firstItemChecked);
        } else {
            items.get(allShopsPosition).setChecked(false);
        }
        notifyItemChanged(allShopsPosition);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setItem(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public List<String> getSelectedShops() {
        List<String> shops = new ArrayList<>();
        for (CheckableItemModel item : items) {
            if (item.isChecked()) {
                shops.add(item.getName());
            }
        }
        return shops;
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

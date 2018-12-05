package com.akhutornoy.shoppinglist.ui.createproduct_onescreen.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.domain.Shop;
import com.akhutornoy.shoppinglist.ui.base.ValueCallback;
import com.akhutornoy.shoppinglist.ui.createproduct_onescreen.model.CheckableItemModel;

import java.util.ArrayList;
import java.util.List;

public class ShopsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected static final String ITEM_EDIT_LIST_NAME = "Edit";
    private static final int ITEM_TYPE_GENERAL = 0;
    private static final int ITEM_TYPE_EDIT_LIST = 1;

    private final OnListEditClickListener onListEditClickCallback;
    private List<CheckableItemModel> items;

    public ShopsAdapter(OnListEditClickListener onListEditClickCallback) {
        this.onListEditClickCallback = onListEditClickCallback;
    }

    public void setShopsSelected(List<String> shopsSelected) {
        for (String shopName : shopsSelected) {
            onClicked(new CheckableItemModel(shopName, true));
        }
    }

    public void setShops(List<Shop> quantityTypes) {
        items = new ArrayList<>(quantityTypes.size());
        for (Shop quantityType : quantityTypes) {
            items.add(new CheckableItemModel(quantityType.getName()));
        }
        addEditItem(items);
        notifyDataSetChanged();
    }

    private void addEditItem(List<CheckableItemModel> items) {
        items.add(new CheckableItemModel(ITEM_EDIT_LIST_NAME));
    }

    @Override
    public int getItemViewType(int position) {
        String name = items.get(position).getName();
        return name.equals(ITEM_EDIT_LIST_NAME)
                ? ITEM_TYPE_EDIT_LIST : ITEM_TYPE_GENERAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_create_product_string, parent, false);

        return ITEM_TYPE_GENERAL == viewType
                ? new GeneralViewHolder(view)
                : new EditViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case ITEM_TYPE_GENERAL:
                GeneralViewHolder generalGeneralViewHolder = (GeneralViewHolder) holder;
                generalGeneralViewHolder.setListener(this::onClicked);
                generalGeneralViewHolder.setItem(items.get(position));
                break;
            case ITEM_TYPE_EDIT_LIST:
                EditViewHolder editViewHolder = (EditViewHolder) holder;
                editViewHolder.setListener(this::onListEditClicked);
                break;
            default:
                throw new IllegalArgumentException(String.format("ViewHolderType=%d NOT supported"));
        }
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

    private void onListEditClicked() {
        onListEditClickCallback.onListEditClicked();
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

    static class GeneralViewHolder extends RecyclerView.ViewHolder {
        private final CheckedTextView textView;

        GeneralViewHolder(View itemView) {
            super(itemView);
            textView = (CheckedTextView) itemView;
        }

        private void setListener(ValueCallback<CheckableItemModel> listener) {
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

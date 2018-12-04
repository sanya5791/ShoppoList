package com.akhutornoy.shoppinglist.ui.createproduct_onescreen.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.akhutornoy.shoppinglist.R;
import com.akhutornoy.shoppinglist.ui.base.ValueCallback;
import com.akhutornoy.shoppinglist.ui.createproduct_onescreen.model.CheckableItemModel;
import com.akhutornoy.shoppinglist.domain.MeasureType;

import java.util.ArrayList;
import java.util.List;

public class QuantityTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected static final String ITEM_EDIT_LIST_NAME = "Edit";
    private static final int ITEM_TYPE_GENERAL = 0;
    private static final int ITEM_TYPE_EDIT_LIST = 1;

    private final OnListEditClickListener onListEditClickCallback;
    private List<CheckableItemModel> items;

    public QuantityTypeAdapter(OnListEditClickListener onListEditClickCallback) {
        this.onListEditClickCallback = onListEditClickCallback;
    }

    public void setQuantityTypes(List<MeasureType> quantityTypes) {
        List<CheckableItemModel> items = new ArrayList<>(quantityTypes.size() + 1);
        for (MeasureType quantityType : quantityTypes) {
            items.add(new CheckableItemModel(quantityType.getName()));
        }
        addEditItem(items);

        this.items = items;
    }

    private void addEditItem(List<CheckableItemModel> items) {
        items.add(new CheckableItemModel(ITEM_EDIT_LIST_NAME));
    }

    public void setSelected(String type) {
        onClicked(type);
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
                GeneralViewHolder generalViewHolder = (GeneralViewHolder) holder;
                generalViewHolder.setListener(this::onClicked);
                generalViewHolder.setItem(items.get(position));
                break;
            case ITEM_TYPE_EDIT_LIST:
                EditViewHolder editViewHolder = (EditViewHolder) holder;
                editViewHolder.setListener(this::onListEditClicked);
                break;
            default:
                throw new IllegalArgumentException(String.format("ViewHolderType=%d NOT supported"));
        }
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

    private void onListEditClicked() {
        onListEditClickCallback.onListEditClicked();
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

    static class GeneralViewHolder extends RecyclerView.ViewHolder {
        private final CheckedTextView textView;

        GeneralViewHolder(View itemView) {
            super(itemView);
            textView = (CheckedTextView) itemView;
        }

        private void setItem(CheckableItemModel item) {
            textView.setText(item.getName());
            textView.setChecked(item.isChecked());
        }

        private void setListener(ValueCallback<String> listener) {
            textView.setOnClickListener(ignored -> onClick(listener));
        }

        private void onClick(ValueCallback<String> callback) {
            if (textView.isChecked()) {
                return;
            }
            textView.setChecked(true);
            callback.select(textView.getText().toString());
        }
    }
}

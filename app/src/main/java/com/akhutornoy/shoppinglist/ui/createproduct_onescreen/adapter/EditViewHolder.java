package com.akhutornoy.shoppinglist.ui.createproduct_onescreen.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckedTextView;

public class EditViewHolder extends RecyclerView.ViewHolder {
    EditViewHolder(View itemView) {
        super(itemView);

        CheckedTextView tv = (CheckedTextView) itemView;
        tv.setCompoundDrawablesRelativeWithIntrinsicBounds(android.R.drawable.ic_menu_edit, 0, 0, 0);
    }

    public void setListener(OnListEditClickListener listener) {
        itemView.setOnClickListener(ignored -> listener.onListEditClicked());
    }
}

package com.akhutornoy.shoppinglist.ui.base.view;

public interface LoadableView<T> extends ProgressView {
    void onDataLoaded(T data);
}
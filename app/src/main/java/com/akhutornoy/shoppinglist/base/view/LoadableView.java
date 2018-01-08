package com.akhutornoy.shoppinglist.base.view;

public interface LoadableView<T> extends ProgressView {
    void onDataLoaded(T data);
}
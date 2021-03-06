package com.akhutornoy.shoppinglist.ui.base.model;

public class ItemModel {
    private final int sortNumber;
    private final String name;

    public ItemModel(String name, int sortNumber) {
        this.sortNumber = sortNumber;
        this.name = name;
    }

    public int getSortNumber() {
        return sortNumber;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemModel that = (ItemModel) o;

        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}

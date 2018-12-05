package com.akhutornoy.shoppinglist.ui.shops.mapper;

import com.akhutornoy.shoppinglist.domain.Shop;
import com.akhutornoy.shoppinglist.ui.shops.model.ShopModel;

import java.util.ArrayList;
import java.util.List;

public class ShopModelFilteredMapper {
    private String filter;

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public List<ShopModel> map(List<Shop> source) {
        if (filter == null) {
            throw new IllegalArgumentException(String.format("'filter' is NOT set"));
        }

        List<ShopModel> result = new ArrayList<>(source.size());
        for (Shop shop : source) {
            if (!filter.equals(shop.getName())) {
                result.add(map(shop));
            }
        }
        return result;
    }
    public ShopModel map(Shop source) {
        String name = source.getName();
        int sortNumber = source.getSortNumber();
        return new ShopModel(name, sortNumber);
    }
}

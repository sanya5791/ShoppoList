package com.akhutornoy.shoppinglist.createproduct_onescreen.mapper;

import com.akhutornoy.shoppinglist.createproduct_onescreen.model.CreateProductOutputModel;
import com.akhutornoy.shoppinglist.domain.ProductInShop;

import java.util.ArrayList;
import java.util.List;

public class ProductInShopMapper {
    private final String all;

    public ProductInShopMapper(String all) {
        this.all = all;
    }

    public List<ProductInShop> map(CreateProductOutputModel source) {
        List<ProductInShop> result = new ArrayList<>();
        if (isProductAvailableInAllShops(source.getShopsSelected())) {
            result.add(new ProductInShop(source.getName(), all));
            return result;
        }

        for (String shop : source.getShopsSelected()) {
            result.add(new ProductInShop(source.getName(), shop));
        }
        return result;
    }

    private boolean isProductAvailableInAllShops(List<String> shopsSelected) {
        for (String shop : shopsSelected) {
            if (all.equals(shop)) {
                return true;
            }
        }
        return false;
    }
}

package com.akhutornoy.shoppinglist.ui.createproduct_onescreen.mapper;

import com.akhutornoy.shoppinglist.ui.createproduct_onescreen.model.CreateProductOutputModel;
import com.akhutornoy.shoppinglist.domain.Product;
import com.akhutornoy.shoppinglist.domain.ProductInShop;

import java.util.ArrayList;
import java.util.List;

public class ProductInShopMapper {
    private String shopNameAll;

    public void setShopNameAll(String shopNameAll) {
        this.shopNameAll = shopNameAll;
    }

    public List<ProductInShop> map(CreateProductOutputModel source) {
        assertShopNameAllIsSet();

        List<ProductInShop> result = new ArrayList<>();
        if (isProductAvailableInAllShops(source.getShopsSelected())) {
            result.add(new ProductInShop(source.getName(), shopNameAll));
            return result;
        }

        for (String shop : source.getShopsSelected()) {
            result.add(new ProductInShop(source.getName(), shop));
        }
        return result;
    }

    private void assertShopNameAllIsSet() {
        if (shopNameAll == null) {
            throw new IllegalArgumentException("Field 'shopNameAll' cannot be null" );
        }
    }

    public CreateProductOutputModel map(Product product, List<ProductInShop> inShops) {
        assertShopNameAllIsSet();

        List<String> inShopsResult = new ArrayList<>(inShops.size());
        for (ProductInShop inShop : inShops) {
            inShopsResult.add(inShop.getShop());
        }

        return CreateProductOutputModel.builder()
                .setName(product.getName())
                .setDefaultQuantity(product.getDefaultQuantity())
                .setQuantityTypeSelected(product.getMeasureType())
                .setShopsSelected(inShopsResult)
                .build();
    }

    private boolean isProductAvailableInAllShops(List<String> shopsSelected) {
        for (String shop : shopsSelected) {
            if (shopNameAll.equals(shop)) {
                return true;
            }
        }
        return false;
    }
}

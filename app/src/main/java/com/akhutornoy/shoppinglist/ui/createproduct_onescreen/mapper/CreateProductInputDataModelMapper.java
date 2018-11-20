package com.akhutornoy.shoppinglist.ui.createproduct_onescreen.mapper;

import com.akhutornoy.shoppinglist.ui.createproduct_onescreen.model.CreateProductInputDataModel;
import com.akhutornoy.shoppinglist.domain.MeasureType;
import com.akhutornoy.shoppinglist.domain.Shop;

import java.util.ArrayList;
import java.util.List;

public class CreateProductInputDataModelMapper {
    private String shopNameAll;

    public CreateProductInputDataModel map(List<MeasureType> measureTypes, List<Shop> shopsList) {
        assertShopNameAllIsSet();

        List<Shop> filteredShopsList = new ArrayList<>(shopsList.size());
        for (Shop shop : shopsList) {
            if (!shop.getName().equals(shopNameAll)) {
                filteredShopsList.add(shop);
            }
        }
        return new CreateProductInputDataModel(measureTypes, filteredShopsList);
    }

    public void setShopNameAll(String shopNameAll) {
        this.shopNameAll = shopNameAll;
    }

    private void assertShopNameAllIsSet() {
        if (shopNameAll == null) {
            throw new IllegalArgumentException("Field 'shopNameAll' cannot be null" );
        }
    }
}

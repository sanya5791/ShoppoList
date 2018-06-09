package com.akhutornoy.shoppinglist.createproduct_onescreen.mapper;

import com.akhutornoy.shoppinglist.createproduct_onescreen.model.CreateProductInputDataModel;
import com.akhutornoy.shoppinglist.domain.MeasureType;
import com.akhutornoy.shoppinglist.domain.Shop;

import java.util.List;

public class CreateProductInputDataModelMapper {

    public CreateProductInputDataModel map(List<MeasureType> measureTypes, List<Shop> shopsList) {
        return new CreateProductInputDataModel(measureTypes, shopsList);
    }
}

package com.akhutornoy.shoppinglist.managemeasuretypes.mapper;

import com.akhutornoy.shoppinglist.base.Mapper;
import com.akhutornoy.shoppinglist.base.model.ItemModel;
import com.akhutornoy.shoppinglist.domain.MeasureType;

public class ItemModelMapper extends Mapper<MeasureType, ItemModel> {
    @Override
    public ItemModel map(MeasureType source) {
        return new ItemModel(source.getName(), source.getSortNumber());
    }

    @Override
    public MeasureType mapInverse(ItemModel source) {
        return new MeasureType(source.getName());
    }
}

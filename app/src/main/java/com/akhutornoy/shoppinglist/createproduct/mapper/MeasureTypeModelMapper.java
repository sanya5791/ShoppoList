package com.akhutornoy.shoppinglist.createproduct.mapper;

import com.akhutornoy.shoppinglist.base.Mapper;
import com.akhutornoy.shoppinglist.createproduct.model.MeasureTypeModel;
import com.akhutornoy.shoppinglist.domain.MeasureType;

public class MeasureTypeModelMapper extends Mapper<MeasureType, MeasureTypeModel> {
    @Override
    public MeasureTypeModel map(MeasureType source) {
        return new MeasureTypeModel(source.getName(), source.getSortNumber());
    }

    @Override
    public MeasureType mapInverse(MeasureTypeModel source) {
        throw new UnsupportedOperationException();
    }
}

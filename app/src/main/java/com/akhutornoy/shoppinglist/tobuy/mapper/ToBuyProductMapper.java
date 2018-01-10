package com.akhutornoy.shoppinglist.tobuy.mapper;

import com.akhutornoy.shoppinglist.base.Mapper;
import com.akhutornoy.shoppinglist.domain.ToBuy;
import com.akhutornoy.shoppinglist.tobuy.model.ToBuyProductModel;

public class ToBuyProductMapper extends Mapper<ToBuy, ToBuyProductModel> {
    @Override
    public ToBuyProductModel map(ToBuy source) {
        return new ToBuyProductModel.Builder()
                .setId("1")
                .setName(source.getName())
                .setUnit(source.getUnit())
                .setQuantity(source.getQuantity())
                .build();
    }

    @Override
    public ToBuy mapInverse(ToBuyProductModel source) {
        return null;
    }
}

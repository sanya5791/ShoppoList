package com.akhutornoy.shoppinglist.ui.tobuy.mapper;

import com.akhutornoy.shoppinglist.base.Mapper;
import com.akhutornoy.shoppinglist.domain.ToBuy;
import com.akhutornoy.shoppinglist.ui.tobuy.model.ToBuyProductModel;

public class ToBuyProductMapper extends Mapper<ToBuy, ToBuyProductModel> {
    @Override
    public ToBuyProductModel map(ToBuy source) {
        return new ToBuyProductModel.Builder()
                .setName(source.getName())
                .setUnit(source.getUnit())
                .setQuantity(source.getQuantity())
                .setShopName(source.getShopName())
                .setIsBought(source.isBought())
                .build();
    }

    @Override
    public ToBuy mapInverse(ToBuyProductModel source) {
        return new ToBuy(
                source.getName(),
                source.getUnit(),
                source.getQuantity(),
                source.getShopName(),
                source.isBought()
        );
    }
}

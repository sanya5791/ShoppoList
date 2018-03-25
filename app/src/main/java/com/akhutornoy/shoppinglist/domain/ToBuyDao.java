package com.akhutornoy.shoppinglist.domain;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public abstract class ToBuyDao {

    @Query("SELECT * FROM ToBuy")
    public abstract Flowable<List<ToBuy>> getAll();

    @Query("SELECT * FROM ToBuy WHERE shopName LIKE :shopName")
    public abstract Flowable<List<ToBuy>> getAllByShop(String shopName);

    @Query("SELECT CurrentShop.name " +
            "FROM CurrentShop " +
            "LIMIT 1")
    abstract Flowable<String> getCurrentShop();

    public Flowable<List<ToBuy>> getAllByCurrentShop(){
        return getCurrentShop()
                .flatMap(shopName -> {
                    if (shopName.equals(getNameForAllShops())) {
                        return getAll();
                    } else {
                        return getAllByShop(shopName);
                    }
                });
    }

    private String getNameForAllShops() {
        return getConstantString(ConstantString.SHOP_NAME_ALL);
    }

    @Query("SELECT ConstantString.value FROM ConstantString WHERE ConstantString.name == :constantName LIMIT 1")
    protected abstract String getConstantString(String constantName);

    @Insert
    public abstract void insertNew(ToBuy item);

    @Insert
    public abstract void insertNew(List<ToBuy> items);

    @Query("DELETE FROM ToBuy")
    public abstract void deleteAll();

    @Query("DELETE FROM ToBuy WHERE ToBuy.shopName == :shopName")
    public abstract void deleteAllByShop(String shopName);
}

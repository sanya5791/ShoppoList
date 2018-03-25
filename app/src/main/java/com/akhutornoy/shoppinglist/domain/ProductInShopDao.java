package com.akhutornoy.shoppinglist.domain;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public abstract class ProductInShopDao {
    @Query("SELECT * FROM ProductInShop")
    public abstract List<ProductInShop> getAll();

    @Query("SELECT * FROM ProductInShop WHERE product LIKE :productName")
    public abstract List<ProductInShop> getByProduct(String productName);

    @Query("SELECT * FROM ProductInShop WHERE shop LIKE :shopName1 OR shop LIKE :shopName2")
    protected abstract List<ProductInShop> getByTwoShops(String shopName1, String shopName2);

    public List<ProductInShop> getByShop(String shopName) {
        return getByTwoShops(getNameForAllShops(), shopName);
    }

    private String getNameForAllShops() {
        return getConstantString(ConstantString.SHOP_NAME_ALL);
    }

    @Query("SELECT ConstantString.value FROM ConstantString WHERE ConstantString.name == :constantName LIMIT 1")
    protected abstract String getConstantString(String constantName);

    @Insert
    public abstract void insertNew(ProductInShop productInShop);

    @Delete
    public abstract void delete(ProductInShop productInShop);

    @Update
    public abstract void update(ProductInShop... productInShops);
}


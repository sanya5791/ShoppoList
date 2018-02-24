package com.akhutornoy.shoppinglist.domain;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ProductInShopDao {
    @Query("SELECT * FROM ProductInShop")
    List<ProductInShop> getAll();

    @Query("SELECT * FROM ProductInShop WHERE product LIKE :productName")
    List<ProductInShop> getByProduct(String productName);

    @Query("SELECT * FROM ProductInShop WHERE shop LIKE :shopName")
    List<ProductInShop> getByShop(String shopName);

    @Insert
    void insertNew(ProductInShop productInShop);

    @Delete
    void delete(ProductInShop productInShop);

    @Update
    void update(ProductInShop... productInShops);
}


package com.akhutornoy.shoppinglist.domain;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface ToBuyDao {

    @Query("SELECT * FROM ToBuy")
    Flowable<List<ToBuy>> getAll();

    @Query("SELECT * FROM ToBuy WHERE shopName LIKE :shopName")
    Flowable<List<ToBuy>> getAllByShop(String shopName);

    @Query("SELECT ToBuy.* FROM ToBuy, CurrentShop WHERE ToBuy.shopName == CurrentShop.name")
    Flowable<List<ToBuy>> getAllByCurrentShop();

    @Insert
    void insertNew(ToBuy item);

    @Insert
    void insertNew(List<ToBuy> items);

    @Query("DELETE FROM ToBuy")
    void deleteAll();

    @Query("DELETE FROM ToBuy WHERE ToBuy.shopName == :shopName")
    void deleteAllByShop(String shopName);
}

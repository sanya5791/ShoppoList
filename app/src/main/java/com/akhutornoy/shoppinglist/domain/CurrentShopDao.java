package com.akhutornoy.shoppinglist.domain;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import io.reactivex.Flowable;

@Dao
public interface CurrentShopDao {
    @Query("SELECT * FROM CurrentShop LIMIT 1")
    String get();

    @Query("SELECT * FROM CurrentShop LIMIT 1")
    Flowable<String> getFlowable();

    @Insert
    void set(CurrentShop currentShop);

    @Query("DELETE FROM CurrentShop")
    void deleteAll();
}

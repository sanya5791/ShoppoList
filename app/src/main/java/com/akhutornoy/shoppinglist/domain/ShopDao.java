package com.akhutornoy.shoppinglist.domain;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface ShopDao {
    @Query("SELECT * FROM Shop")
    Flowable<List<Shop>> getAll();

    @Insert
    void insertNew(Shop shop);

    @Delete
    void delete(Shop shop);

    @Update
    void update(Shop... shops);
}

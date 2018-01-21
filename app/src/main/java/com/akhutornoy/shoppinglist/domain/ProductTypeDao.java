package com.akhutornoy.shoppinglist.domain;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface ProductTypeDao {
    @Query("SELECT * FROM ProductType")
    Flowable<List<ProductType>> getAll();

    @Insert
    void insertNew(ProductType productType);

    @Delete
    void delete(ProductType productType);

    @Update
    void update(ProductType... productType);
}

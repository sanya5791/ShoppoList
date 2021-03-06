package com.akhutornoy.shoppinglist.domain;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM Product")
    Flowable<List<Product>> getAll();

    @Query("SELECT * FROM Product WHERE name LIKE :name LIMIT 1")
    Product getByName(String name);

    @Insert
    void insertNew(Product product);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNewWithReplace(Product product);

    @Delete
    void delete(Product product);

    @Update
    void update(Product... product);
}

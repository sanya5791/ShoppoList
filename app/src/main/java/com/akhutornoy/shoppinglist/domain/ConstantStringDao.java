package com.akhutornoy.shoppinglist.domain;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ConstantStringDao {
    @Query("SELECT * FROM ConstantString")
    List<ConstantString> getAll();

    @Query("SELECT ConstantString.value FROM ConstantString WHERE ConstantString.name == :name LIMIT 1")
    String getByName(String name);

    @Insert
    void insert(ConstantString currentShop);

    @Query("DELETE FROM ConstantString")
    void deleteAll();
}

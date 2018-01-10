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

    @Insert
    void insertNew(ToBuy item);
}

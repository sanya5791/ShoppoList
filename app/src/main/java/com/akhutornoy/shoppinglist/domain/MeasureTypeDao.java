package com.akhutornoy.shoppinglist.domain;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface MeasureTypeDao {
    @Query("SELECT * FROM MeasureType")
    Flowable<List<MeasureType>> getAll();

    @Insert
    void insertNew(MeasureType measureType);

    @Delete
    void delete(MeasureType measureType);

    @Update
    void update(MeasureType... measureType);
}

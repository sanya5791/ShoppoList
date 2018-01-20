package com.akhutornoy.shoppinglist.domain;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

@android.arch.persistence.room.Database(entities = {ToBuy.class, Shop.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract ToBuyDao toBuy();
    public abstract ShopDao toShop();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            Log.w("AppDatabase", "creating DB Instance...");
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "Database.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

package com.akhutornoy.shoppinglist.domain;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

@android.arch.persistence.room.Database(entities = {ToBuy.class, Shop.class, Product.class, ProductType.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract ToBuyDao toBuy();
    public abstract ShopDao toShop();
    public abstract ProductDao toProduct();
    public abstract ProductTypeDao toProductType();

    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE 'ProductType' "
                    + "('name' TEXT NOT NULL, "
                    + "'sortNumber' INTEGER NOT NULL, "
                    + "PRIMARY KEY('name'))");
        }
    };

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            Log.w("AppDatabase", "creating DB Instance...");
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                AppDatabase.class, "Database.db")
                            .addMigrations(MIGRATION_2_3)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

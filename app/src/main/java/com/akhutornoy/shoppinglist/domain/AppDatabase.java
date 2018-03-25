package com.akhutornoy.shoppinglist.domain;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;

@android.arch.persistence.room.Database(
        entities = {
            ConstantString.class,
            ToBuy.class,
            Shop.class,
            Product.class,
            MeasureType.class,
            ProductInShop.class,
            CurrentShop.class
        },
        version = 11)
public abstract class AppDatabase extends RoomDatabase {
    private static final String TAG = "AppDatabase";

    private static volatile AppDatabase INSTANCE;

    public abstract ConstantStringDao toConstantString();
    public abstract ToBuyDao toBuy();
    public abstract ShopDao toShop();
    public abstract ProductDao toProduct();
    public abstract MeasureTypeDao toMeasureType();
    public abstract ProductInShopDao toProductInShop();
    public abstract CurrentShopDao toCurrentShop();

    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE 'MeasureType' "
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
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    prepopulateDb(context, getInstance(context));
                                }
                            })
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static void prepopulateDb(Context context, AppDatabase db) {
        Completable.fromAction(() -> DefaultDbDataInflater.prepopulate(context, db))
            .subscribeOn(Schedulers.io())
            .subscribe(
                    () -> {},
                    error -> {
                        Log.e(TAG, "prepopulateDb: ", error);
                        Toast.makeText(context, "Error: " + error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    });
    }
}

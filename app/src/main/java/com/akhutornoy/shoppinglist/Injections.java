package com.akhutornoy.shoppinglist;

import android.content.Context;

import com.akhutornoy.shoppinglist.ui.addproducts.contract.AddProductsContract;
import com.akhutornoy.shoppinglist.ui.addproducts.mapper.AddProductModelMapper;
import com.akhutornoy.shoppinglist.ui.addproducts.mapper.ToBuyMapper;
import com.akhutornoy.shoppinglist.ui.addproducts.presenter.AddProductsPresenter;
import com.akhutornoy.shoppinglist.ui.createproduct_onescreen.contract.CreateProductContract;
import com.akhutornoy.shoppinglist.ui.createproduct_onescreen.mapper.CreateProductInputDataModelMapper;
import com.akhutornoy.shoppinglist.ui.createproduct_onescreen.mapper.ProductInShopMapper;
import com.akhutornoy.shoppinglist.ui.createproduct_onescreen.mapper.ProductMapper;
import com.akhutornoy.shoppinglist.ui.createproduct_onescreen.presenter.CreateProductPresenter;
import com.akhutornoy.shoppinglist.domain.AppDatabase;
import com.akhutornoy.shoppinglist.ui.editproduct.contract.EditProductContract;
import com.akhutornoy.shoppinglist.ui.editproduct.presenter.EditProductPresenter;
import com.akhutornoy.shoppinglist.ui.settings.contract.SettingsContract;
import com.akhutornoy.shoppinglist.ui.settings.dbbackup.BackupSourceHelper;
import com.akhutornoy.shoppinglist.ui.settings.dbbackup.Zipper;
import com.akhutornoy.shoppinglist.ui.settings.presenter.SettingsPresenter;
import com.akhutornoy.shoppinglist.ui.settings.presenter.TempDbHandler;
import com.akhutornoy.shoppinglist.ui.shops.displayshops.contract.ShopsContract;
import com.akhutornoy.shoppinglist.ui.shops.displayshops.presenter.ShopsPresenter;
import com.akhutornoy.shoppinglist.ui.shops.manageshops.contract.ManageShopsContract;
import com.akhutornoy.shoppinglist.ui.shops.manageshops.presenter.ManageShopsPresenter;
import com.akhutornoy.shoppinglist.ui.shops.mapper.ItemModelMapper;
import com.akhutornoy.shoppinglist.ui.shops.mapper.ShopModelMapper;
import com.akhutornoy.shoppinglist.ui.tobuy.contract.ToBuyProductsContract;
import com.akhutornoy.shoppinglist.ui.tobuy.mapper.ToBuyProductMapper;
import com.akhutornoy.shoppinglist.ui.tobuy.presenter.ToBuyProductsPresenter;

public class Injections {

    public static SettingsContract.Presenter provideSettingsPresenter(Context context) {
        BackupSourceHelper backupSourceHelper = new BackupSourceHelper(context);
        Zipper zipper = new Zipper();
        TempDbHandler tempDbHelper = new TempDbHandler(backupSourceHelper);
        return new SettingsPresenter(
                backupSourceHelper,
                tempDbHelper,
                zipper
        );
    }

    public static AddProductsContract.Presenter provideAddProductsPresenter(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        return new AddProductsPresenter(appDatabase.toCurrentShop(),
                appDatabase.toProductInShop(),
                appDatabase.toProduct(),
                appDatabase.toBuy(),
                new AddProductModelMapper(),
                new ToBuyMapper());
    }

    public static CreateProductContract.Presenter provideCreateProductPresenter(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        return new CreateProductPresenter(
                db.toProduct(),
                db.toProductInShop(),
                new ProductInShopMapper(),
                new ProductMapper(),
                db.toMeasureType(),
                db.toShop(),
                new CreateProductInputDataModelMapper(),
                db.toConstantString(),
                db.toCurrentShop()
        );
    }

    public static EditProductContract.Presenter provideEditProductPresenter(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        return new EditProductPresenter(
                db.toProduct(),
                db.toProductInShop(),
                new ProductInShopMapper(),
                new ProductMapper(),
                db.toMeasureType(),
                db.toShop(),
                new CreateProductInputDataModelMapper(),
                db.toConstantString(),
                db.toCurrentShop()
        );
    }

    public static ShopsContract.Presenter provideShopsPresenter(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        return new ShopsPresenter(
                db.toShop(),
                db.toCurrentShop(),
                new ShopModelMapper()
        );
    }

    public static ManageShopsContract.Presenter provideManageShopsPresenter(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        return new ManageShopsPresenter(
                db.toShop(),
                new ShopModelMapper(),
                new ItemModelMapper()
        );
    }

    public static ToBuyProductsContract.Presenter provideToBuyProductsPresenter(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        return new ToBuyProductsPresenter(
                db.toBuy(),
                db.toCurrentShop(),
                new ToBuyProductMapper());
    }
}

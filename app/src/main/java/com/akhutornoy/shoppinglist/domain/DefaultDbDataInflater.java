package com.akhutornoy.shoppinglist.domain;

import android.content.Context;

import com.akhutornoy.shoppinglist.R;

public class DefaultDbDataInflater {
    public static void prepopulate(Context context, AppDatabase db) {
        addShops(context, db.toShop());
        addCurrentShop(context, db.toCurrentShop());
        addMeasureTypes(context, db.toMeasureType());
        addProducts(context, db.toProduct());
        addProductInShops(context, db.toProductInShop());
    }

    private static void addShops(Context context, ShopDao shopDb) {
        shopDb.insertNew(new Shop(context.getString(R.string.shop_all)));
        shopDb.insertNew(new Shop(context.getString(R.string.shop_1)));
        shopDb.insertNew(new Shop(context.getString(R.string.shop_2)));
    }

    private static void addCurrentShop(Context context, CurrentShopDao currentShopDao) {
        currentShopDao.set(new CurrentShop(context.getString(R.string.shop_1)));
    }

    private static void addMeasureTypes(Context context, MeasureTypeDao measureTypeDao) {
        measureTypeDao.insertNew(new MeasureType(context.getString(R.string.measure_type_1)));
        measureTypeDao.insertNew(new MeasureType(context.getString(R.string.measure_type_2)));
        measureTypeDao.insertNew(new MeasureType(context.getString(R.string.measure_type_3)));
        measureTypeDao.insertNew(new MeasureType(context.getString(R.string.measure_type_4)));
        measureTypeDao.insertNew(new MeasureType(context.getString(R.string.measure_type_5)));
        measureTypeDao.insertNew(new MeasureType(context.getString(R.string.measure_type_6)));
    }

    private static void addProducts(Context context, ProductDao productDao) {
        productDao.insertNew(new Product(
                context.getString(R.string.product_1),
                context.getString(R.string.product_1_default_quantity),
                context.getString(R.string.product_1_measure_type)));
        productDao.insertNew(new Product(
                context.getString(R.string.product_2),
                context.getString(R.string.product_2_default_quantity),
                context.getString(R.string.product_2_measure_type)));
        productDao.insertNew(new Product(
                context.getString(R.string.product_3),
                context.getString(R.string.product_3_default_quantity),
                context.getString(R.string.product_3_measure_type)));
        productDao.insertNew(new Product(
                context.getString(R.string.product_4),
                context.getString(R.string.product_4_default_quantity),
                context.getString(R.string.product_4_measure_type)));
        productDao.insertNew(new Product(
                context.getString(R.string.product_5),
                context.getString(R.string.product_5_default_quantity),
                context.getString(R.string.product_5_measure_type)));
        productDao.insertNew(new Product(
                context.getString(R.string.product_6),
                context.getString(R.string.product_6_default_quantity),
                context.getString(R.string.product_6_measure_type)));
        productDao.insertNew(new Product(
                context.getString(R.string.product_7),
                context.getString(R.string.product_7_default_quantity),
                context.getString(R.string.product_7_measure_type)));
        productDao.insertNew(new Product(
                context.getString(R.string.product_8),
                context.getString(R.string.product_8_default_quantity),
                context.getString(R.string.product_8_measure_type)));
    }

    private static void addProductInShops(Context context, ProductInShopDao productInShopDao) {
        productInShopDao.insertNew(new ProductInShop(
                context.getString(R.string.product_1),
                context.getString(R.string.shop_all)));
        productInShopDao.insertNew(new ProductInShop(
                context.getString(R.string.product_2),
                context.getString(R.string.shop_2)));
        productInShopDao.insertNew(new ProductInShop(
                context.getString(R.string.product_3),
                context.getString(R.string.shop_2)));
        productInShopDao.insertNew(new ProductInShop(
                context.getString(R.string.product_4),
                context.getString(R.string.shop_all)));
        productInShopDao.insertNew(new ProductInShop(
                context.getString(R.string.product_5),
                context.getString(R.string.shop_all)));
        productInShopDao.insertNew(new ProductInShop(
                context.getString(R.string.product_6),
                context.getString(R.string.shop_2)));
        productInShopDao.insertNew(new ProductInShop(
                context.getString(R.string.product_7),
                context.getString(R.string.shop_all)));
        productInShopDao.insertNew(new ProductInShop(
                context.getString(R.string.product_8),
                context.getString(R.string.shop_all)));
    }
}

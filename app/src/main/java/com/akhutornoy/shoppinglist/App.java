package com.akhutornoy.shoppinglist;

import android.app.Application;

import com.idescout.sql.SqlScoutServer;


public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SqlScoutServer.create(this, getPackageName());
    }
}

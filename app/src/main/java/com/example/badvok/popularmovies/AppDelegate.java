package com.example.badvok.popularmovies;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;

/**
 * Created by simon on 08-May-16.
 */
public class AppDelegate extends Application {

    public static Context ctx;

    public static Realm getRealmInstance() {
        return Realm.getInstance(ctx);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppDelegate.ctx = getApplicationContext();
    }
}

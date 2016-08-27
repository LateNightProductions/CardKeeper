package com.awscherb.cardkeeper.ui.controller.base;

import android.app.Application;

import com.awscherb.cardkeeper.data.CoreData;

public class CKApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        CoreData.initialize(this);
    }
}

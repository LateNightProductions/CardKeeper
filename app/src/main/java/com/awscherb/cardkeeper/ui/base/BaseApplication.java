package com.awscherb.cardkeeper.ui.base;

import android.app.Application;

import com.awscherb.cardkeeper.di.component.DaggerViewComponent;
import com.awscherb.cardkeeper.di.component.ViewComponent;
import com.awscherb.cardkeeper.di.module.AppModule;
import com.awscherb.cardkeeper.di.module.DaoModule;

public class BaseApplication extends Application {

    private ViewComponent viewComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        viewComponent = DaggerViewComponent.builder()
                .appModule(new AppModule(this))
                .daoModule(new DaoModule())
                .build();

    }

    public ViewComponent getViewComponent() {
        return viewComponent;
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}

package com.awscherb.cardkeeper.ui.base;

import android.app.Application;

import com.awscherb.cardkeeper.dagger.component.DaggerServicesComponent;
import com.awscherb.cardkeeper.dagger.component.ServicesComponent;
import com.awscherb.cardkeeper.dagger.module.HandlersModule;

import io.realm.Realm;

public class BaseApplication extends Application {

    private ServicesComponent servicesComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);

        servicesComponent = DaggerServicesComponent.builder()
                .handlersModule(new HandlersModule())
                .build();

    }

    public ServicesComponent getServicesComponent() {
        return servicesComponent;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}

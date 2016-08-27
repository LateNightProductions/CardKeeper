package com.awscherb.cardkeeper.data.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

@Module
public class RealmModule {

    private final Context context;

    public RealmModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Realm provideRealm() {
        RealmConfiguration config = new RealmConfiguration.Builder(context).build();
        Realm.setDefaultConfiguration(config);

        return Realm.getDefaultInstance();
    }
}

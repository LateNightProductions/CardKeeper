package com.awscherb.cardkeeper.dagger.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

@Module
public class RealmModule {

    private final Context context;

    public RealmModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Realm provideRealm() {
        Realm.init(context);

        return Realm.getDefaultInstance();
    }
}

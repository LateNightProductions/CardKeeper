package com.awscherb.cardkeeper.dagger.module;

import com.awscherb.cardkeeper.data.handler.RealmScannedCodeHandler;
import com.awscherb.cardkeeper.data.service.ScannedCodeService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

@Module
public class HandlersModule {

    @Provides
    @Singleton
    ScannedCodeService provideScannedCodeService(Realm realm) {
        return new RealmScannedCodeHandler(realm);
    }
}

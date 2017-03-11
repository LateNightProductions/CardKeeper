package com.awscherb.cardkeeper.dagger.module;

import com.awscherb.cardkeeper.data.handler.RealmScannedCodeHandler;
import com.awscherb.cardkeeper.data.service.ScannedCodeService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class HandlersModule {

    @Provides
    @Singleton
    ScannedCodeService provideScannedCodeService() {
        return new RealmScannedCodeHandler();
    }
}

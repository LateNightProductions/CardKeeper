package com.awscherb.cardkeeper.dagger.component;

import com.awscherb.cardkeeper.dagger.module.HandlersModule;
import com.awscherb.cardkeeper.dagger.module.RealmModule;
import com.awscherb.cardkeeper.data.service.ScannedCodeService;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {
        HandlersModule.class,
        RealmModule.class
})
@Singleton
public interface ServicesComponent {

    ScannedCodeService scannedCodeService();
}

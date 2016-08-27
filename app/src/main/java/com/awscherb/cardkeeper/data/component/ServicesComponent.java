package com.awscherb.cardkeeper.data.component;

import com.awscherb.cardkeeper.data.module.RealmModule;
import com.awscherb.cardkeeper.data.module.HandlersModule;
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

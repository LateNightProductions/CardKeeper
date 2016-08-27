package com.awscherb.cardkeeper.data;

import android.content.Context;

import com.awscherb.cardkeeper.data.component.DaggerServicesComponent;
import com.awscherb.cardkeeper.data.component.ServicesComponent;
import com.awscherb.cardkeeper.data.module.HandlersModule;
import com.awscherb.cardkeeper.data.module.RealmModule;
import com.awscherb.cardkeeper.data.service.ScannedCodeService;

public class CoreData {

    private static ServicesComponent sServices;

    // ========================================================================
    // Initialization
    // ========================================================================

    public static void initialize(Context context) {
        initServices(context);
    }

    // ========================================================================
    // Service initialization
    // ========================================================================

    private static void initServices(Context context) {
        sServices = DaggerServicesComponent.builder()
                .realmModule(new RealmModule(context))
                .handlersModule(new HandlersModule()).build();
    }

    // ========================================================================
    // Exposed service methods
    // ========================================================================

    public static ScannedCodeService getScannedCodeService() {
        return sServices.scannedCodeService();
    }

}

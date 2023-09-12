package com.awscherb.cardkeeper.di.component

import com.awscherb.cardkeeper.barcode.di.BarcodeModule
import com.awscherb.cardkeeper.di.module.AppModule
import com.awscherb.cardkeeper.di.module.BindingsModule
import com.awscherb.cardkeeper.pkpass.di.PkPassModule
import com.awscherb.cardkeeper.ui.base.CardKeeperApplication
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        BarcodeModule::class,
        BindingsModule::class,
        PkPassModule::class,
    ]
)
@Singleton
interface ViewComponent {
    fun inject(app: CardKeeperApplication)
}
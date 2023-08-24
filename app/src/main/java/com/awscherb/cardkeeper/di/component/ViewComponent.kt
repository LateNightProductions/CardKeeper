package com.awscherb.cardkeeper.di.component

import com.awscherb.cardkeeper.di.module.ApiModule
import com.awscherb.cardkeeper.di.module.AppModule
import com.awscherb.cardkeeper.di.module.BindingsModule
import com.awscherb.cardkeeper.di.module.DaoModule
import com.awscherb.cardkeeper.di.module.ServiceModule
import com.awscherb.cardkeeper.ui.base.CardKeeperActivity
import com.awscherb.cardkeeper.ui.base.CardKeeperApplication
import com.awscherb.cardkeeper.ui.card_detail.CardDetailFragment
import com.awscherb.cardkeeper.ui.items.ItemsFragment
import com.awscherb.cardkeeper.ui.create.CreateFragment
import com.awscherb.cardkeeper.ui.scan.ScanFragment
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Component(
    modules = [
        AndroidInjectionModule::class,
        ApiModule::class,
        AppModule::class,
        BindingsModule::class,
        DaoModule::class,
        ServiceModule::class,
    ]
)
@Singleton
interface ViewComponent {
    fun inject(app: CardKeeperApplication)
}
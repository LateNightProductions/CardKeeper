package com.awscherb.cardkeeper.di.component

import com.awscherb.cardkeeper.di.module.AppModule
import com.awscherb.cardkeeper.di.module.DaoModule
import com.awscherb.cardkeeper.di.module.PresenterModule
import com.awscherb.cardkeeper.di.module.SchedulerModule
import com.awscherb.cardkeeper.di.module.ServiceModule
import com.awscherb.cardkeeper.ui.card_detail.CardDetailFragment
import com.awscherb.cardkeeper.ui.cards.CardsFragment
import com.awscherb.cardkeeper.ui.scan.ScanFragment
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [AppModule::class,
        DaoModule::class,
        ServiceModule::class,
        SchedulerModule::class,
        PresenterModule::class
    ]
)
@Singleton
interface ViewComponent {
    fun inject(view: CardDetailFragment)
    fun inject(view: CardsFragment)
    fun inject(view: ScanFragment)
}
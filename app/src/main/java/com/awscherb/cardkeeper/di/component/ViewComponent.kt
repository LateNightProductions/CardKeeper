package com.awscherb.cardkeeper.di.component

import com.awscherb.cardkeeper.di.module.AppModule
import com.awscherb.cardkeeper.di.module.DaoModule
import com.awscherb.cardkeeper.di.module.DispatchersModule
import com.awscherb.cardkeeper.di.module.PresenterModule
import com.awscherb.cardkeeper.di.module.ServiceModule
import com.awscherb.cardkeeper.ui.card_detail.CardDetailFragment
import com.awscherb.cardkeeper.ui.cards.CardsFragment
import com.awscherb.cardkeeper.ui.create.CreateFragment
import com.awscherb.cardkeeper.ui.create.CreatePresenter
import com.awscherb.cardkeeper.ui.scan.ScanFragment
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [AppModule::class,
        DaoModule::class,
        ServiceModule::class,
        DispatchersModule::class,
        PresenterModule::class
    ]
)
@Singleton
interface ViewComponent {
    fun inject(view: CardDetailFragment)
    fun inject(view: CardsFragment)
    fun inject(view: CreateFragment)
    fun inject(view: ScanFragment)
}
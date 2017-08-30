package com.awscherb.cardkeeper.di.component

import com.awscherb.cardkeeper.di.module.*
import com.awscherb.cardkeeper.ui.card_detail.CardDetailFragment
import com.awscherb.cardkeeper.ui.cards.CardsFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = arrayOf(
        AppModule::class,
        DaoModule::class,
        ServiceModule::class,
        SchedulerModule::class,
        PresenterModule::class))
@Singleton
interface ViewComponent {
    fun inject(view: CardDetailFragment)
    fun inject(view: CardsFragment)
}
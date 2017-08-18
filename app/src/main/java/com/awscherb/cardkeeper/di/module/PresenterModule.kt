package com.awscherb.cardkeeper.di.module

import com.awscherb.cardkeeper.ui.card_detail.CardDetailContract
import com.awscherb.cardkeeper.ui.card_detail.CardDetailPresenter
import com.awscherb.cardkeeper.ui.cards.CardsContract
import com.awscherb.cardkeeper.ui.cards.CardsPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class PresenterModule {
    @Binds abstract fun bindCardsPresnter(p: CardsPresenter): CardsContract.Presenter
    @Binds abstract fun bindCardDetailPresenter(p: CardDetailPresenter): CardDetailContract.Presenter
}
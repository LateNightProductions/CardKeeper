package com.awscherb.cardkeeper.di.module

import com.awscherb.cardkeeper.ui.card_detail.CardDetailContract
import com.awscherb.cardkeeper.ui.card_detail.CardDetailPresenter
import com.awscherb.cardkeeper.ui.cards.CardsContract
import com.awscherb.cardkeeper.ui.cards.CardsPresenter
import com.awscherb.cardkeeper.ui.create.CreateContract
import com.awscherb.cardkeeper.ui.create.CreatePresenter
import com.awscherb.cardkeeper.ui.scan.ScanContract
import com.awscherb.cardkeeper.ui.scan.ScanPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class PresenterModule {
    @Binds
    abstract fun bindCardsPresenter(p: CardsPresenter): CardsContract.Presenter

    @Binds
    abstract fun bindCardDetailPresenter(p: CardDetailPresenter): CardDetailContract.Presenter

    @Binds
    abstract fun bindCreatePresenter(p: CreatePresenter): CreateContract.Presenter

    @Binds
    abstract fun bindScanPresenter(p: ScanPresenter): ScanContract.Presenter
}
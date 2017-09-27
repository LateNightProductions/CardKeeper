package com.awscherb.cardkeeper.ui.card_detail

import com.awscherb.cardkeeper.data.model.ScannedCode
import com.awscherb.cardkeeper.data.service.ScannedCodeService
import com.awscherb.cardkeeper.ui.base.Presenter
import io.reactivex.Scheduler

import javax.inject.Inject

class CardDetailPresenter @Inject constructor(
        @[JvmField Inject] var uiScheduler: Scheduler,
        @[JvmField Inject] var service: ScannedCodeService)
    : Presenter<CardDetailContract.View>(uiScheduler), CardDetailContract.Presenter {

    private lateinit var originalTitle: String
    private lateinit var card: ScannedCode

    //================================================================================
    // Presenter methods
    //================================================================================

    override fun loadCard(id: Int) {
        addDisposable(service.getScannedCode(id)
                .compose(scheduleSingle())
                .subscribe({
                    card = it
                    originalTitle = it.title
                    view?.showCard(it)
                }, { view?.onError(it) }))
    }

    override fun setTitle(title: String) {
        view?.setSaveVisible(title != originalTitle)
        card.title = title
    }

    override fun saveCard() {
            addDisposable(service.updateScannedCode(card)
                    .compose(scheduleSingle())
                    .subscribe({ view?.onCardSaved() },
                            { view?.onError(it) }))
    }
}

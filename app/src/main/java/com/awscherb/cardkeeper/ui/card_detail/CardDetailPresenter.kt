package com.awscherb.cardkeeper.ui.card_detail

import com.awscherb.cardkeeper.data.service.ScannedCodeService
import com.awscherb.cardkeeper.ui.base.Presenter
import io.reactivex.Scheduler

import javax.inject.Inject

class CardDetailPresenter @Inject constructor(
        @[JvmField Inject] var uiScheduler: Scheduler,
        @[JvmField Inject] var service: ScannedCodeService)
    : Presenter<CardDetailContract.View>(uiScheduler), CardDetailContract.Presenter {

    //================================================================================
    // Presenter methods
    //================================================================================

    override fun loadCard(id: Int) {
        addDisposable(service.getScannedCode(id)
                .compose(scheduleSingle())
                .subscribe({ view?.showCard(it) },
                        { view?.onError(it) }))
    }
}

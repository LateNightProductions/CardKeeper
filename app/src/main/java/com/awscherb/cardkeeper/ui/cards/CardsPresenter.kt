package com.awscherb.cardkeeper.ui.cards

import com.awscherb.cardkeeper.data.model.ScannedCode
import com.awscherb.cardkeeper.data.service.ScannedCodeService
import com.awscherb.cardkeeper.ui.base.Presenter
import com.awscherb.cardkeeper.ui.card_detail.CardDetailContract
import com.awscherb.cardkeeper.util.extensions.toMainThread

import javax.inject.Inject

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable


class CardsPresenter @Inject constructor()
    : Presenter<CardsContract.View>(), CardsContract.Presenter {

    @Inject internal lateinit var scannedCodeService: ScannedCodeService

    override fun loadCards() {
        addDisposable(scannedCodeService.listAllScannedCodes()
                .toMainThread()
                .subscribe({ view?.showCards(it) },
                        { it.printStackTrace() }))
    }

    override fun addNewCard(code: ScannedCode) {
        addDisposable(scannedCodeService.addScannedCode(code)
                .toMainThread()
                .subscribe({ view?.onCardAdded(it) },
                        { it.printStackTrace() }))
    }

    override fun deleteCard(code: ScannedCode) {
        addDisposable(scannedCodeService.deleteScannedCode(code)
                .toMainThread()
                .subscribe({ view?.onCardDeleted() },
                        { it.printStackTrace() }))
    }
}

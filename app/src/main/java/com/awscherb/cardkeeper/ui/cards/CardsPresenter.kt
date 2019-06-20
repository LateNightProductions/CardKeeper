package com.awscherb.cardkeeper.ui.cards

import com.awscherb.cardkeeper.data.model.ScannedCode
import com.awscherb.cardkeeper.data.service.ScannedCodeService
import com.awscherb.cardkeeper.ui.base.Presenter
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class CardsPresenter @Inject constructor(
    dispatcher: CoroutineDispatcher,
    private val scannedCodeService: ScannedCodeService
) : Presenter<CardsContract.View>(dispatcher), CardsContract.Presenter {

    override fun loadCards() {
        uiScope(view::onError) {
            view.showCards(scannedCodeService.listAllScannedCodes())
        }
    }

    override fun addNewCard(code: ScannedCode) {
        uiScope(view::onError) {
            view.onCardAdded(scannedCodeService.addScannedCode(code))
        }
    }

    override fun deleteCard(code: ScannedCode) {
        uiScope(view::onError) {
            scannedCodeService.deleteScannedCode(code)
            view.onCardDeleted()
        }
    }
}

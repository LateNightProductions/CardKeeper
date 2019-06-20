package com.awscherb.cardkeeper.ui.card_detail

import com.awscherb.cardkeeper.data.model.ScannedCode
import com.awscherb.cardkeeper.data.service.ScannedCodeService
import com.awscherb.cardkeeper.ui.base.Presenter
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class CardDetailPresenter @Inject constructor(
    dispatcher: CoroutineDispatcher,
    private val service: ScannedCodeService
) : Presenter<CardDetailContract.View>(dispatcher), CardDetailContract.Presenter {

    private lateinit var originalTitle: String
    private lateinit var card: ScannedCode

    //================================================================================
    // Presenter methods
    //================================================================================

    override fun loadCard(id: Int) {
        uiScope(view::onError) {
            val code = service.getScannedCode(id)
            card = code
            originalTitle = code.title
            view.showCard(code)
        }
    }

    override fun setTitle(title: String) {
        view.setSaveVisible(title != originalTitle)
        card.title = title
    }

    override fun saveCard() {
        uiScope(view::onError) {
            service.updateScannedCode(card)
            view.onCardSaved()
        }
    }
}

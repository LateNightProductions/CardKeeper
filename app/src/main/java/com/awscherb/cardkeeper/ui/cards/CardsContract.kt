package com.awscherb.cardkeeper.ui.cards

import com.awscherb.cardkeeper.data.model.ScannedCode
import com.awscherb.cardkeeper.ui.base.BasePresenter
import com.awscherb.cardkeeper.ui.base.BaseView

interface CardsContract {

    interface View : BaseView {

        fun showCards(codes: List<ScannedCode>)

        fun onCardAdded(code: ScannedCode)

        fun onCardDeleted()
    }

    interface Presenter : BasePresenter<CardsContract.View> {

        fun loadCards()

        fun addNewCard(code: ScannedCode)

        fun deleteCard(code: ScannedCode)
    }
}

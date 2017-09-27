package com.awscherb.cardkeeper.ui.card_detail

import com.awscherb.cardkeeper.data.model.ScannedCode
import com.awscherb.cardkeeper.ui.base.BasePresenter
import com.awscherb.cardkeeper.ui.base.BaseView

interface CardDetailContract {

    interface View : BaseView {
        fun showCard(code: ScannedCode)
        fun setSaveVisible(visible: Boolean)
        fun onCardSaved()
    }

    interface Presenter : BasePresenter<CardDetailContract.View> {
        fun loadCard(id: Int)
        fun setTitle(title: String)
        fun saveCard()
    }

}

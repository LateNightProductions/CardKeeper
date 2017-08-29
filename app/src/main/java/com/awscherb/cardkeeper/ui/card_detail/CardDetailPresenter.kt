package com.awscherb.cardkeeper.ui.card_detail

import com.awscherb.cardkeeper.data.service.ScannedCodeService
import com.awscherb.cardkeeper.ui.base.Presenter
import com.awscherb.cardkeeper.util.extensions.toMainThread

import javax.inject.Inject

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable


class CardDetailPresenter @Inject constructor()
    : Presenter<CardDetailContract.View>(), CardDetailContract.Presenter {

    @Inject internal lateinit var service: ScannedCodeService

    //================================================================================
    // Presenter methods
    //================================================================================

    override fun loadCard(id: Int) {
        addDisposable(service.getScannedCode(id)
                .toMainThread()
                .subscribe({ view!!.showCard(it) },
                        { it.printStackTrace() }))
    }
}

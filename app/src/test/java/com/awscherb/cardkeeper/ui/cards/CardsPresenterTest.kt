package com.awscherb.cardkeeper.ui.cards

import com.awscherb.cardkeeper.data.model.ScannedCode
import com.awscherb.cardkeeper.data.service.ScannedCodeService
import com.awscherb.cardkeeper.ui.base.BasePresenterTest
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when` as whn

class CardsPresenterTest : BasePresenterTest<CardsContract.View, CardsPresenter>() {

    @Mock lateinit var service: ScannedCodeService

    override fun getViewClass() = CardsContract.View::class.java
    override fun createPresenter() = CardsPresenter(Schedulers.trampoline(), service)

    @Test
    fun `Load cards success`() {
        whn(service.listAllScannedCodes())
                .thenReturn(Flowable.just(ArrayList()))

        presenter.loadCards()

        verify(view, times(1)).showCards(any())
    }

    @Test
    fun `Load cards failure`() {
        whn(service.listAllScannedCodes())
                .thenReturn(Flowable.error(Throwable()))

        presenter.loadCards()

        verify(view, times(1)).onError(any())
    }

    @Test
    fun `Add new card success`() {
        val newCode = ScannedCode()
        whn(service.addScannedCode(newCode))
                .thenReturn(Single.just(newCode))

        presenter.addNewCard(newCode)

        verify(view, times(1)).onCardAdded(newCode)
    }

    @Test
    fun `Add new card failure`() {
        whn(service.addScannedCode(any()))
                .thenReturn(Single.error(Throwable()))

        presenter.addNewCard(ScannedCode())

        verify(view, times(1)).onError(any())
    }

    @Test
    fun `Delete card success`() {
        whn(service.deleteScannedCode(any()))
                .thenReturn(Completable.complete())

        presenter.deleteCard(ScannedCode())

        verify(view, times(1)).onCardDeleted()
    }

    @Test
    fun `Delete card failure`() {
        whn(service.deleteScannedCode(any()))
                .thenReturn(Completable.error(Throwable()))

        presenter.deleteCard(ScannedCode())

        verify(view, times(1)).onError(any())

    }
}


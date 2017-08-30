package com.awscherb.cardkeeper.ui.cards

import com.awscherb.cardkeeper.data.service.ScannedCodeService
import com.awscherb.cardkeeper.ui.base.BasePresenterTest
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Flowable
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

}
package com.awscherb.cardkeeper.ui.card_detail

import com.awscherb.cardkeeper.data.model.ScannedCode
import com.awscherb.cardkeeper.data.service.ScannedCodeService
import com.awscherb.cardkeeper.ui.base.BasePresenterTest
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class CardDetailPresenterTest : BasePresenterTest<CardDetailContract.View, CardDetailPresenter>() {

    @Mock lateinit var service: ScannedCodeService

    override fun getViewClass() = CardDetailContract.View::class.java
    override fun createPresenter() = CardDetailPresenter(Schedulers.trampoline(), service)

    @Test
    fun `Load cards success`() {
        Mockito.`when`(service.getScannedCode(1))
                .thenReturn(Single.just(ScannedCode()))

        presenter.loadCard(1)

        verify(view, times(1)).showCard(any())
    }

    @Test
    fun `Load cards failure`() {
        Mockito.`when`(service.getScannedCode(1))
                .thenReturn(Single.error(Throwable()))

        presenter.loadCard(1)

        verify(view, times(1)).onError(any())
    }

}

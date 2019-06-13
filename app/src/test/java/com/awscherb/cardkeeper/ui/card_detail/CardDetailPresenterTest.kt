package com.awscherb.cardkeeper.ui.card_detail

import com.awscherb.cardkeeper.data.model.ScannedCode
import com.awscherb.cardkeeper.data.service.ScannedCodeService
import com.awscherb.cardkeeper.ui.base.BasePresenterTest
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when` as whn

class CardDetailPresenterTest : BasePresenterTest<CardDetailContract.View, CardDetailPresenter>() {

    @Mock lateinit var service: ScannedCodeService

    override fun getViewClass() = CardDetailContract.View::class.java
    override fun createPresenter() = CardDetailPresenter(Schedulers.trampoline(), service)

    @Test
    fun `Load card success`() {
        whn(service.getScannedCode(1))
                .thenReturn(Single.just(ScannedCode().apply { title = "title" }))

        presenter.loadCard(1)

        verify(view, times(1)).showCard(any())
    }

    @Test
    fun `Load card failure`() {
        whn(service.getScannedCode(1))
                .thenReturn(Single.error(Throwable()))

        presenter.loadCard(1)

        verify(view, times(1)).onError(any())
    }

    @Test
    fun `Update title`() {
        `Set card data`()
        val cardField = presenter.javaClass.getDeclaredField("card").apply { isAccessible = true }
        val code = cardField.get(presenter) as ScannedCode

        presenter.setTitle("Title")

        assertEquals("Title", code.title)

        verify(view, times(1)).setSaveVisible(true)
    }

    @Test
    fun `Save card success`() {
        `Set card data`()
        whn(service.updateScannedCode(any()))
                .thenReturn(Single.just(ScannedCode()))

        presenter.saveCard()

        verify(view, times(1)).onCardSaved()
    }

    @Test
    fun `Save card failure`() {
        `Set card data`()
        whn(service.updateScannedCode(any()))
                .thenReturn(Single.error(Throwable()))

        presenter.saveCard()

        verify(view, times(1)).onError(any())
    }

    private fun `Set card data`() {
        val cardField = presenter.javaClass.getDeclaredField("card")
        cardField.isAccessible = true
        cardField.set(presenter, ScannedCode())

        val titleField = presenter.javaClass.getDeclaredField("originalTitle")
        titleField.isAccessible = true
        titleField.set(presenter, "original")
    }
}

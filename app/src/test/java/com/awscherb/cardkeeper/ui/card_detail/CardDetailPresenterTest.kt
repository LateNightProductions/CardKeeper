package com.awscherb.cardkeeper.ui.card_detail

import com.awscherb.cardkeeper.data.entity.ScannedCodeEntity
import com.awscherb.cardkeeper.data.service.ScannedCodeService
import com.awscherb.cardkeeper.ui.base.BasePresenterTest
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mock

class CardDetailPresenterTest : BasePresenterTest<CardDetailContract.View, CardDetailPresenter>() {

    @Mock
    lateinit var service: ScannedCodeService

    override fun getViewClass() = CardDetailContract.View::class.java
    override fun createPresenter() = CardDetailPresenter(Dispatchers.Unconfined, service)

    @Test
    fun `Load card success`() = runBlocking {
        whenever(service.getScannedCode(1))
            .thenReturn(ScannedCodeEntity().apply {
                title = "title"
            })

        presenter.loadCard(1)

        verify(view, times(1)).showCard(any())
    }

    @Test
    fun `Load card failure`() = runBlocking {
        whenever(service.getScannedCode(1))
            .thenThrow(RuntimeException())

        presenter.loadCard(1)

        verify(view, times(1)).onError(any())
    }

    @Test
    fun `Update title`() {
        `Set card data`()
        val cardField = presenter.javaClass.getDeclaredField("card").apply { isAccessible = true }
        val code = cardField.get(presenter) as ScannedCodeEntity

        presenter.setTitle("Title")

        assertEquals("Title", code.title)

        verify(view, times(1)).setSaveVisible(true)
    }

    @Test
    fun `Save card success`() = runBlocking {
        `Set card data`()
        whenever(service.updateScannedCode(any()))
            .thenReturn(ScannedCodeEntity())

        presenter.saveCard()

        verify(view, times(1)).onCardSaved()
    }

    @Test
    fun `Save card failure`() = runBlocking {
        `Set card data`()
        whenever(service.updateScannedCode(any()))
            .thenThrow(RuntimeException())

        presenter.saveCard()

        verify(view, times(1)).onError(any())
    }

    private fun `Set card data`() {
        val cardField = presenter.javaClass.getDeclaredField("card")
        cardField.isAccessible = true
        cardField.set(presenter, ScannedCodeEntity())

        val titleField = presenter.javaClass.getDeclaredField("originalTitle")
        titleField.isAccessible = true
        titleField.set(presenter, "original")
    }
}


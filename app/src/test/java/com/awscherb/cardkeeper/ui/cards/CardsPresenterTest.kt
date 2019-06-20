package com.awscherb.cardkeeper.ui.cards

import com.awscherb.cardkeeper.data.model.ScannedCode
import com.awscherb.cardkeeper.data.service.ScannedCodeService
import com.awscherb.cardkeeper.ui.base.BasePresenterTest
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mock

class CardsPresenterTest : BasePresenterTest<CardsContract.View, CardsPresenter>() {

    @Mock
    lateinit var service: ScannedCodeService

    override fun getViewClass() = CardsContract.View::class.java

    override fun createPresenter() = CardsPresenter(Dispatchers.Unconfined, service)

    @Test
    fun `Load cards success`() = runBlocking {
        whenever(service.listAllScannedCodes())
            .thenReturn(ArrayList())

        presenter.loadCards()

        verify(view, times(1)).showCards(any())
    }

    @Test
    fun `Load cards failure`() = runBlocking {
        whenever(service.listAllScannedCodes())
            .thenThrow(RuntimeException())

        presenter.loadCards()

        verify(view, times(1)).onError(any())
    }

    @Test
    fun `Add new card success`() = runBlocking {
        val newCode = ScannedCode()
        whenever(service.addScannedCode(newCode))
            .thenReturn(newCode)

        presenter.addNewCard(newCode)

        verify(view, times(1)).onCardAdded(newCode)
    }

    @Test
    fun `Add new card failure`() = runBlocking {
        whenever(service.addScannedCode(any()))
            .thenThrow(RuntimeException())

        presenter.addNewCard(ScannedCode())

        verify(view, times(1)).onError(any())
    }

    @Test
    fun `Delete card success`() = runBlocking {
        //        whenever(service.deleteScannedCode(any()))
//            .thenAnswer {  }

        presenter.deleteCard(ScannedCode())

        verify(view, times(1)).onCardDeleted()
    }

    @Test
    fun `Delete card failure`() = runBlocking {
        whenever(service.deleteScannedCode(any()))
            .thenThrow(RuntimeException())

        presenter.deleteCard(ScannedCode())

        verify(view, times(1)).onError(any())
    }
}


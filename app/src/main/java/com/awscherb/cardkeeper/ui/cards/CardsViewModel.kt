package com.awscherb.cardkeeper.ui.cards

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.awscherb.cardkeeper.data.model.ScannedCode
import com.awscherb.cardkeeper.data.service.ScannedCodeService
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class CardsViewModel(
    private val scannedCodeService: ScannedCodeService
) : ViewModel() {

    val cards = MutableLiveData<List<ScannedCode>>()

    val searchQuery = MutableLiveData("")

    init {
        searchQuery.asFlow()
            .flatMapLatest { scannedCodeService.listAllScannedCodes(it) }
            .onEach {
                cards.postValue(it)
            }
            .launchIn(viewModelScope)
    }

    fun deleteCard(code: ScannedCode) {
        scannedCodeService.deleteScannedCode(code).launchIn(viewModelScope)
    }
}
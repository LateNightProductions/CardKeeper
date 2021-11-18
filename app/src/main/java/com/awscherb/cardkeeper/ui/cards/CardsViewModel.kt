package com.awscherb.cardkeeper.ui.cards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awscherb.cardkeeper.data.model.ScannedCode
import com.awscherb.cardkeeper.data.service.ScannedCodeService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn

class CardsViewModel(
    private val scannedCodeService: ScannedCodeService
) : ViewModel() {

    val searchQuery = MutableStateFlow("")

    val cards: Flow<List<ScannedCode>> = searchQuery
        .flatMapLatest { scannedCodeService.listAllScannedCodes(it) }

    fun deleteCard(code: ScannedCode) {
        scannedCodeService.deleteScannedCode(code).launchIn(viewModelScope)
    }
}
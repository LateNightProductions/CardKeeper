package com.awscherb.cardkeeper.ui.cards

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awscherb.cardkeeper.data.model.ScannedCode
import com.awscherb.cardkeeper.data.service.ScannedCodeService
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CardsViewModel(
    private val scannedCodeService: ScannedCodeService
) : ViewModel() {

    val cards = MutableLiveData<List<ScannedCode>>()

    init {
        viewModelScope.launch {
            scannedCodeService.listAllScannedCodes()
                .collect {
                    cards.postValue(it)
                }
        }
    }

    fun addNewCard(code: ScannedCode) {
        viewModelScope.launch {
            scannedCodeService.addScannedCode(code)
        }
    }

    fun deleteCard(code: ScannedCode) {
        viewModelScope.launch {
            scannedCodeService.deleteScannedCode(code)
        }
    }
}
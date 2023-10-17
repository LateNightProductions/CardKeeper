package com.awscherb.cardkeeper.ui.scannedCode

import androidx.lifecycle.*
import com.awscherb.cardkeeper.barcode.service.ScannedCodeService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScannedCodeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val scannedCodeService: ScannedCodeService
) : ViewModel() {

    val cardId = savedStateHandle.get<Int>("codeId")!!
    val card = scannedCodeService.getScannedCode(cardId)

    suspend fun delete() {
        scannedCodeService.deleteCode(cardId)
    }
}
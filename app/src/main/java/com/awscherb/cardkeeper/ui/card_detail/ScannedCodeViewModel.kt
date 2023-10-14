package com.awscherb.cardkeeper.ui.card_detail

import androidx.lifecycle.*
import com.awscherb.cardkeeper.barcode.service.ScannedCodeService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScannedCodeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    scannedCodeService: ScannedCodeService
) : ViewModel() {

    val cardId = savedStateHandle.get<Int>("codeId")!!
    val card = scannedCodeService.getScannedCode(cardId)
}
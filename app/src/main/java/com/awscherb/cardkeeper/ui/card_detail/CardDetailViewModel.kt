package com.awscherb.cardkeeper.ui.card_detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.awscherb.cardkeeper.data.model.ScannedCode
import com.awscherb.cardkeeper.data.service.ScannedCodeService
import com.awscherb.cardkeeper.ui.create.*
import kotlinx.coroutines.launch

class CardDetailViewModel(
    codeId: Int,
    private val scannedCodeService: ScannedCodeService
) : ViewModel() {

    val card = MutableLiveData<ScannedCode>()

    val title = MutableLiveData<String>()
    val saveResult = MutableLiveData<SaveResult>()

    init {
        viewModelScope.launch {
            card.postValue(
                scannedCodeService.getScannedCode(codeId)
            )
        }

    }

    fun save() {
        val titleValue = title.value
        when {
            titleValue.isNullOrEmpty() -> saveResult.postValue(InvalidTitle)
            else -> {
                val scannedCode = card.value?.copy(title = titleValue) ?: return

                viewModelScope.launch {
                    saveResult.postValue(
                        SaveSuccess(
                            scannedCodeService.updateScannedCode(scannedCode).id
                        )
                    )
                }
            }
        }
    }
}